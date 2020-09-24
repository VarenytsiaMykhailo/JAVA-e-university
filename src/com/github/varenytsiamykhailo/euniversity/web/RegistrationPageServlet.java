package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.DAO;
import com.github.varenytsiamykhailo.euniversity.logic.entities.Role;
import com.github.varenytsiamykhailo.euniversity.logic.entities.User;

import com.github.varenytsiamykhailo.euniversity.web.Exceptions.IllegalRequestException;
import com.github.varenytsiamykhailo.euniversity.web.Exceptions.validatorExceptions.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class RegistrationPageServlet extends HttpServlet {

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

        final String login = req.getParameter("login");
        final String loginRepeat = req.getParameter("login_repeat");
        final String email = req.getParameter("email");
        final String emailRepeat = req.getParameter("email_repeat");
        final String password = req.getParameter("password");
        final String passwordRepeat = req.getParameter("password_repeat");
        final String role = req.getParameter("role");

        /* Отладка
        System.out.println(login);
        System.out.println(loginRepeat);
        System.out.println(email);
        System.out.println(emailRepeat);
        System.out.println(password);
        System.out.println(passwordRepeat);
        System.out.println(role);
        */

        // Валидатор введенных данных (на случай, если что-то случилось с js скриптом)
        validator(login, loginRepeat, email, emailRepeat, password, passwordRepeat);

        DAO dao = new DAO();

        if (dao.userIsExistByLogin(login)) {

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

            newUser.setRole(dao.getRole(role));
            dao.addUser(newUser);

            // Посылаем в jsp инфу об успешной регистрации. Нужно для вывода всплывающего окна об успешности.
            req.setAttribute("successfulRegistrationNotification", Boolean.TRUE);
        }

        System.out.println("Redirect from RegistrationPageServlet to RegistrationPage.jsp");

        /* На следующей jsp странице будет вызываться js скрипт-оповещение об успешной или неудачной регистраци
           с предложением перейти на главную страницу или зарегистрировать нового пользователя */
        getServletContext().getRequestDispatcher("/RegistrationPage.jsp").forward(req, resp);

    }

    void validator(String login, String loginRepeat, String email, String emailRepeat, String password, String passwordRepeat) throws IOException {
        // Регулярные выражения для валидации
        String regExpLogin = "^[a-zA-Z][a-zA-Z0-9-_]{3,21}$"; // минимум 4 символа, максимум 23; допускаются строчные и заглавные латинские буквы и цифры; первый символ обязательно буква
        String regExpEmail = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"; // Регулярка соответствует емаилу из стандарта RFC 5322. https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression/201378#201378 взята с http://emailregex.com/  для javascript
        String regExpPassword = "^[0-9a-zA-Z]{6,}$"; // минимум 6 символов; допускаются строчные и заглавные латинские буквы и цифры;

        // Выбрасываем исключения, если введены неверные данные
        if (!login.matches(regExpLogin)) {
            throw new IOException(new IncorrectLoginInputException());
        }
        if (!email.matches(regExpEmail)) {
            throw new IOException(new IncorrectEmailInputException());
        }
        if (!password.matches(regExpPassword)) {
            throw new IOException(new IncorrectPasswordInputException());
        }

        if (!login.equals(loginRepeat)) {
            throw new IOException(new LoginsDontEqualsException());
        }
        if (!email.equals(emailRepeat)) {
            throw new IOException(new EmailsDontEqualsException());
        }
        if (!password.equals(passwordRepeat)) {
            throw new IOException(new PasswordsDontEqualsException());
        }
    }
}
