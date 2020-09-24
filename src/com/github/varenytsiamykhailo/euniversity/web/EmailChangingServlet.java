package com.github.varenytsiamykhailo.euniversity.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class EmailChangingServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8"); // Установка кодировки для принятия параметров (запроса)

        System.out.println("Enter to EmailChangingServlet");

        final String newEmail = req.getParameter("new_email");
        final String newEmailRepeat = req.getParameter("new_email_repeat");

        int result = emailValidator(newEmail, newEmailRepeat);
        if (result == 1) {
            // Посылаем в jsp инфу об неправильно введенном email
            req.setAttribute("invalidEmailInput", Boolean.TRUE);
        } else if (result == 2) {
            // Посылаем в jsp инфу об не одинаково введенных emailах
            req.setAttribute("emailsDontEqualsInput", Boolean.TRUE);
        } else if (result == 0) {
            final String userLogin = (String) req.getSession().getAttribute("login");

            @SuppressWarnings("unchecked") final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

            try {
                managementSystemWebDAO.get().updateUserEmail(userLogin, newEmail);

                // Посылаем в jsp инфу об успешном изменении email
                req.setAttribute("successfulEmailChangingNotification", Boolean.TRUE);
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        getServletContext().getRequestDispatcher("/AccountSettingsPage.jsp").forward(req, resp);

    }

    /**
     * Вовзращает 0, если валидация пройдена, 1 - если емаил не соответствует регулярному выражению, 2 - если емаилы не одинаковы.
     */
    int emailValidator(String email, String emailRepeat) {
        // Регулярные выражения для валидации
        /*
         * Регулярка соответствует емаилу из стандарта RFC 5322
         * https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression/201378#201378
         * */
        String regExpEmail = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"; // Регулярка соответствует емаилу из стандарта RFC 5322. https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression/201378#201378 взята с http://emailregex.com/  для javascript

        if (!email.matches(regExpEmail)) {
            return 1;
        } else if (!email.equals(emailRepeat)) {
            return 2;
        } else {
            return 0; // Валидация пройдена
        }
    }
}
