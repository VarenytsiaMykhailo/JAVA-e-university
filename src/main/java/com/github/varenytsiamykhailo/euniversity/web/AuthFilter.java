package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DepartmentPerson;
import com.github.varenytsiamykhailo.euniversity.logic.Role;
import com.github.varenytsiamykhailo.euniversity.web.Exceptions.IllegalRequestException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

/**
 * Authentication filter.
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        System.out.println("_________________________");
        System.out.println("Enter to Filter");
        System.out.println("URI = " + req.getRequestURI());
        System.out.println("URL = " + req.getRequestURL());

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        final HttpSession session = req.getSession();


        if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) { // Если пользователь уже вводил логин и пароль ранее (т.е. через куки пришла инфа с его активной сессией и введенными ранее данными), то мы его пропускаем
            System.out.println("Enter to the first validator block");

            final Role role = (Role) session.getAttribute("role");

            giveAccessToContent(req, res, filterChain, role);
        } else {

            final String login = req.getParameter("login");
            final String password = req.getParameter("password");
            System.out.println("LOGIN = " + login);
            System.out.println("PASSWORD = " + password);

            try {
                if (nonNull(login) && nonNull(password) && managementSystemWebDAO.get().userIsExistByLoginPassword(login, password)) { // Если пользователь проходит проверку впервые (и он ввел корректные данные)
                    System.out.println("Enter to the second validator block");

                    final Role role = managementSystemWebDAO.get().getUserRoleByLoginPassword(login, password);

                    req.getSession().setAttribute("password", password);
                    req.getSession().setAttribute("login", login);
                    req.getSession().setAttribute("role", role);

                    giveAccessToContent(req, res, filterChain, role);
                } else { // Если пользователь ввел некорректные данные
                    System.out.println("Enter to the third validator block");

                    if (nonNull(login) || nonNull(password)) {  // Логика вывода сообщения об неправильно введенных данных
                        req.setAttribute("incorrectLoginPassword", Boolean.TRUE);
                    }

                    giveAccessToContent(req, res, filterChain, Role.UNKNOWN);
                }
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    private void giveAccessToContent(final HttpServletRequest req, final HttpServletResponse res, FilterChain filterChain, final Role role) throws ServletException, IOException {
        if (role.equals(Role.USER)) {
            System.out.println("giveAccessToContent USER");

            // В эту коллекцию добавляем страницы, которые недоступны User.
            ArrayList<String> blockedContentForUsers = new ArrayList<>();
            blockedContentForUsers.add("/RegistrationPage.jsp");
            blockedContentForUsers.add("/registration");

            if (nonNull(req.getParameter("is_login_action"))) { // Если пользователь попал фильтр со страницы входа, перенаправляем его на главную страницу
                System.out.println("isLoginAction = " + req.getParameter("is_login_action"));
                req.getRequestDispatcher("/main").forward(req, res);
                return;
            }

            // Блокируем запрещенные контенты
            for (String pageName : blockedContentForUsers) { // Попытка перейти на запрещенный контент
                if (req.getRequestURI().equals(req.getServletContext().getContextPath() + pageName)) {
                    throw new IOException(new IllegalRequestException());
                }
            }

            filterChain.doFilter(req, res); // Передаем управление следующим фильтрам/сервлетам в цепочке

        } else if (role.equals(Role.ADMIN)) {
            System.out.println("giveAccessToContent ADMIN");

            if (nonNull(req.getParameter("is_login_action"))) { // Если пользователь попал фильтр со страницы входа, перенаправляем его на главную страницу
                System.out.println("isLoginAction = " + req.getParameter("is_login_action"));
                req.getRequestDispatcher("/main").forward(req, res);
                return;
            }

            filterChain.doFilter(req, res); // Передаем управление следующим фильтрам/сервлетам в цепочке

        } else {
            System.out.println("giveAccessToContent UNKNOWN");

            req.getRequestDispatcher("/LoginPage.jsp").forward(req, res); // Если роль UNKNOWN т.е. человек не прошел аутентификацию - отправляем его на страницу входа
        }
    }

    @Override
    public void destroy() {

    }
}
