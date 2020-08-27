package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.Role;
import com.github.varenytsiamykhailo.euniversity.logic.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class RegistrationPageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8"); // Установка кодировки для принятия параметров (запроса)

        System.out.println("Enter to RegistrationPageServlet");

        // Мини защита, если пытаются попасть в сервелет RegistrationPageServelet не из RegistrationPage.jsp, обойдя валидацию js скрипта.
        if (req.getHeader("Referer") == null || !req.getHeader("Referer").endsWith("RegistrationPage.jsp")
                || req.getParameter("validatedByJSValidator") == null || !req.getParameter("validatedByJSValidator").equals("TRUE")) {
            throw new IOException(new IllegalRequestException());
        }

        @SuppressWarnings("unchecked") final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        final String login = req.getParameter("login");
        final String loginRepeat = req.getParameter("login_repeat");
        final String email = req.getParameter("email");
        final String emailRepeat = req.getParameter("email_repeat");
        final String password = req.getParameter("password");
        final String passwordRepeat = req.getParameter("password_repeat");
        final String role = req.getParameter("role");

        System.out.println(login);
        System.out.println(loginRepeat);
        System.out.println(email);
        System.out.println(emailRepeat);
        System.out.println(password);
        System.out.println(passwordRepeat);
        System.out.println(role);

        // Проверки на равенство данных (на случай, если что-то случилось с js скриптом)
        if (!login.equals(loginRepeat)) {
            throw new IOException(new LoginsDontEqualsException());
        }
        if (!email.equals(emailRepeat)) {
            throw new IOException(new EmailsDontEqualsException());
        }
        if (!password.equals(passwordRepeat)) {
            throw new IOException(new PasswordsDontEqualsException());
        }

        try {
            if (managementSystemWebDAO.get().userIsExistByLogin(login)) {

                /* Посылаем в jsp инфу об неудачной регистрации.
                Нужно для вывода всплывающего окна об неудачной регистрации, с сообщением,
                что пользователь с таким логином уже зарегистрирован*/
                req.setAttribute("unsuccessfulRegistrationNotification", Boolean.TRUE);
                req.setAttribute("unsuccessfulRegistrationLogin", login);

            } else {
                User newUser = new User();
                newUser.setLogin(login);
                newUser.setEmail(email);
                newUser.setPassword(password);
                Role roleTmp;
                if (role.equals("ADMIN"))
                    roleTmp = Role.ADMIN;
                else if (role.equals("USER"))
                    roleTmp = Role.USER;
                else {
                    roleTmp = Role.UNKNOWN;
                }
                newUser.setRoleId(managementSystemWebDAO.get().getRoleIdByUserRole(roleTmp));
                managementSystemWebDAO.get().addUser(newUser);

                // Посылаем в jsp инфу об успешной регистрации. Нужно для вывода всплывающего окна об успешности.
                req.setAttribute("successfulRegistrationNotification", Boolean.TRUE);
            }
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        }

        System.out.println("Redirect from RegistrationPageServlet to RegistrationPage.jsp");

        /* На следующей jsp странице будет вызываться js скрипт-оповещение об успешной или неудачной регистраци
           с предложением перейти на главную страницу или зарегистрировать нового пользователя */
        getServletContext().getRequestDispatcher("/RegistrationPage.jsp").forward(req, resp);

    }
}

class LoginsDontEqualsException extends Exception {
    public LoginsDontEqualsException() {
        super("Login is not equal login repeat.");
    }
}

class EmailsDontEqualsException extends Exception {
    public EmailsDontEqualsException() {
        super("Email is not equal email repeat.");
    }
}

class PasswordsDontEqualsException extends Exception {
    public PasswordsDontEqualsException() {
        super("Password is not equal password repeat.");
    }
}

class IllegalRequestException extends Exception {
    public IllegalRequestException() {
        super("Illegal request exception.");
    }
}