package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DepartmentPerson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;

public class DepartmentStaffEditServlet extends HttpServlet {

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

        System.out.println("Enter to DepartmentStaffEditServlet");

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        String personId = req.getParameter("person_id");

        // Если пользователь нажал кнопку apply (выполнить) - обновляем данные
        if (personId != null && req.getParameter("apply") != null) {
            try {
                DepartmentPerson preparedDepartmentPerson = prepareDepartmentPerson(req);

                // Если personId сотрудника == 0 т.е. новый сотрудник
                if (Integer.parseInt(personId) == 0) {
                    if (managementSystemWebDAO.get().isExistPersonContractInDepartmentStaff(preparedDepartmentPerson.getPersonContract())) {
                        // Посылаем в jsp инфу об неуспешном добавлении сотрудника по причине выбора существующего person_contract. Нужно для вывода всплывающего окна об неудаче.
                        req.setAttribute("unSuccessfulDepartmentPersonInsertionNotification", Boolean.TRUE);
                        req.setAttribute("departmentPerson", preparedDepartmentPerson); // Нужно для того, чтобы пользователь заново не вводил данные в JSP.
                    } else {
                        managementSystemWebDAO.get().insertDepartmentPerson(preparedDepartmentPerson);
                        // Посылаем в jsp инфу об успешном добавлении сотрудника. Нужно для вывода всплывающего окна об успешности.
                        req.setAttribute("successfulDepartmentPersonInsertionNotification", Boolean.TRUE);
                    }
                } else {
                    if (!managementSystemWebDAO.get().checkPersonContractInDepartmentStaffForUpdate(preparedDepartmentPerson.getPersonId(), preparedDepartmentPerson.getPersonContract())) {
                        // Посылаем в jsp инфу об неуспешном обновлении сотрудника по причине выбора существующего person_contract. Нужно для вывода всплывающего окна об неудаче.
                        req.setAttribute("unSuccessfulDepartmentPersonUpdatingNotification", Boolean.TRUE);
                        req.setAttribute("departmentPerson", preparedDepartmentPerson); // Нужно для того, чтобы пользователь заново не вводил данные в JSP.
                    } else {
                        managementSystemWebDAO.get().updateDepartmentPerson(preparedDepartmentPerson);
                        // Посылаем в jsp инфу об успешном редактировании сотрудника. Нужно для вывода всплывающего окна об успешности.
                        req.setAttribute("successfulDepartmentPersonUpdatingNotification", Boolean.TRUE);
                    }
                }
            } catch (SQLException | ParseException e) {
                throw new IOException(e.getMessage());
            }
        }

        System.out.println("Redirect from StudentEditPageServlet to /DepartmentPersonEditPage.jsp");
        getServletContext().getRequestDispatcher("/DepartmentPersonEditPage.jsp").forward(req, resp);
    }

    private DepartmentPerson prepareDepartmentPerson(HttpServletRequest req) throws ParseException {
        DepartmentPerson departmentPerson = new DepartmentPerson();
        departmentPerson.setPersonId(Integer.parseInt(req.getParameter("person_id")));
        departmentPerson.setFirstName(req.getParameter("first_name").trim()); // trim - удаляет пробелы вначале и в конце строки
        departmentPerson.setLastName(req.getParameter("last_name").trim());
        departmentPerson.setMiddleName(req.getParameter("middle_name").trim());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        departmentPerson.setDateOfBirth(simpleDateFormat.parse(req.getParameter("date_of_birth").trim()));
        departmentPerson.setSex(req.getParameter("sex").charAt(0));
        departmentPerson.setPersonContract(Integer.parseInt(req.getParameter("person_contract")));
        return departmentPerson;
    }
}

