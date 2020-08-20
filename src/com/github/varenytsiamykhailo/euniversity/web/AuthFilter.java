package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.UserDAO;
import com.github.varenytsiamykhailo.euniversity.logic.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

/**
 * Acidification filter.
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        System.out.println("<_________________________");
        System.out.println("LOGIN = " + login);
        System.out.println("PASSWORD = " + password);

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> userDAO = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("userDAO");

        final HttpSession session = req.getSession();

        System.out.println("URI = " + req.getRequestURI());
        System.out.println("URL = " + req.getRequestURL());

        if (req.getRequestURI().equals(req.getServletContext().getContextPath() + "/logout")) { // Если нажата кнопка logout
            System.out.println("Redirecting to LogoutServlet");
            // res.sendRedirect(req.getServletContext().getContextPath() + "/logout");
            filterChain.doFilter(req, res);
            return;
        }


        if (nonNull(session) && nonNull(session.getAttribute("login")) && nonNull(session.getAttribute("password"))) { // Если пользователь уже вводил логин и пароль ранее (т.е. через куки пришла инфа с его активной сессией и введенными ранее данными), то мы его пропускаем
            System.out.println("Enter to the first validator block");

            final User.Role role = (User.Role) session.getAttribute("role");
            System.out.println("Role = " + role.toString());

            giveAccessToContent(req, res, filterChain, role);
        } else if (nonNull(login) && nonNull(password) && userDAO.get().userIsExist(login, password)) { // Если пользователь проходит проверку впервые (и он ввел корректные данные)
            System.out.println("Enter to the second validator block");

            final User.Role role = userDAO.get().getUserRoleByLoginPassword(login, password);
            System.out.println("Role = " + role.toString());

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            giveAccessToContent(req, res, filterChain, role);
        } else { // Если пользователь ввел некорректные данные
            System.out.println("Enter to the third validator block");

            if (nonNull(login) || nonNull(password)) {
                req.setAttribute("incorrectLoginPassword", true);
            }

            giveAccessToContent(req, res, filterChain, User.Role.UNKNOWN);
        }
    }

    private void giveAccessToContent(final HttpServletRequest req, final HttpServletResponse res, FilterChain filterChain, final User.Role role) throws ServletException, IOException {
        if (role.equals(User.Role.USER)) {
            filterChain.doFilter(req, res);
            req.setAttribute("role", "USER");
            req.getRequestDispatcher("/main").forward(req, res);
        } else if (role.equals(User.Role.ADMIN)) {
            filterChain.doFilter(req, res);
            req.setAttribute("role", "ADMIN");
            req.getRequestDispatcher("/main").forward(req, res);
        } else {
            req.getRequestDispatcher("/LoginPage.jsp").forward(req, res); // Если роль UNKNOWN т.е. человек не прошел аутентификацию
        }
    }

    @Override
    public void destroy() {

    }
}
