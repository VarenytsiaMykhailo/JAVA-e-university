package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.DAO;
import com.github.varenytsiamykhailo.euniversity.logic.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordChangingServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8"); // Установка кодировки для принятия параметров (запроса)

        System.out.println("Enter to PasswordChangingServlet");

        final String newPassword = req.getParameter("new_password");
        final String newPasswordRepeat = req.getParameter("new_password_repeat");
        final String currentPassword = req.getParameter("current_password"); // Валидировать нет смысла

        int result = passwordValidator(newPassword, newPasswordRepeat);

        if (result == 1) {
            // Посылаем в jsp инфу об неправильно введенном password
            req.setAttribute("invalidPasswordInput", Boolean.TRUE);
        } else if (result == 2) {
            // Посылаем в jsp инфу об не одинаково введенных passwords
            req.setAttribute("passwordsDontEqualsInput", Boolean.TRUE);
        } else if (result == 0) {

            final User user = (User) req.getSession().getAttribute("user");

            DAO dao = new DAO();

            if (!dao.userIsExistByLoginPassword(user.getLogin(), currentPassword)) { // Если пользователь ввел неправильный currentPassword
                // Посылаем в jsp инфу об неправильно введенном currentPassword.
                req.setAttribute("incorrectCurrentPasswordInput", Boolean.TRUE);
            } else {
                dao.updateUserPassword(user, currentPassword, newPassword);
                // Посылаем в jsp инфу об успешном изменении email
                req.setAttribute("successfulPasswordChangingNotification", Boolean.TRUE);
            }

        }

        getServletContext().getRequestDispatcher("/AccountSettingsPage.jsp").forward(req, resp);
    }

    /**
     * Вовзращает 0, если валидация пройдена, 1 - если пароль не соответствует регулярному выражению, 2 - если пароли не одинаковы.
     */
    int passwordValidator(final String newPassword, final String newPasswordRepeat) {
        // Регулярные выражения для валидации
        String regExpPassword = "^[0-9a-zA-Z]{6,}$"; // минимум 6 символов; допускаются строчные и заглавные латинские буквы и цифры;

        if (!newPassword.matches(regExpPassword)) {
            return 1;
        } else if (!newPassword.equals(newPasswordRepeat)) {
            return 2;
        } else {
            return 0; // Валидация пройдена
        }
    }
}
