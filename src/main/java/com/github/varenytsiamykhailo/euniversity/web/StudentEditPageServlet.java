package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;
import com.github.varenytsiamykhailo.euniversity.web.forms.MainDataFormForDisplay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class StudentEditPageServlet extends HttpServlet {

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

        System.out.println("Enter to StudentEditPageServlet");

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        String studentId = req.getParameter("student_id");

        // Если пользователь нажал кнопку apply (выполнить) - обновляем данные
        if (studentId != null && req.getParameter("apply") != null) {
            try {
                // Если id студента == 0 т.е. новый студент
                if (Integer.parseInt(studentId) == 0) {
                    insertStudent(req);
                    // Посылаем в jsp инфу об успешном добавлении студента. Нужно для вывода всплывающего окна об успешности.
                    req.setAttribute("successfulStudentInsertionNotification", Boolean.TRUE);
                } else {
                    updateStudent(req);
                    // Посылаем в jsp инфу об успешном редактировании студента. Нужно для вывода всплывающего окна об успешности.
                    req.setAttribute("successfulStudentUpdatingNotification", Boolean.TRUE);
                }
            } catch (SQLException | ParseException e) {
                throw new IOException(e.getMessage());
            }
        }

        // Получаем данные для отображения на главной форме
        String groupIdString = req.getParameter("group_id");
        String yearString = req.getParameter("education_year");

        int groupId = -1;
        if (groupIdString != null) {
            groupId = Integer.parseInt(groupIdString);
        }

        int year = Calendar.getInstance().get(Calendar.YEAR); // Дефолтное значение - текущий год
        if (yearString != null) {
            year = Integer.parseInt(yearString);
        }

        MainDataFormForDisplay mainDataFormForDisplay = new MainDataFormForDisplay();
        try {
            ArrayList<Group> allGroups = managementSystemWebDAO.get().getAllGroups();
            Group group = new Group();
            group.setGroupId(groupId);
            if (groupId == -1) { // Если группа не выбрала в списке
                Iterator it = allGroups.iterator();
                group = (Group) it.next();
            }

            ArrayList<Student> studentsForSelectedGroup = managementSystemWebDAO.get().getStudentsFromGroup(group, year);
            mainDataFormForDisplay.setSelectedGroupId(group.getGroupId());
            mainDataFormForDisplay.setSelectedYear(year);
            mainDataFormForDisplay.setAllGroups(allGroups);
            mainDataFormForDisplay.setStudentsForSelectedGroup(studentsForSelectedGroup);
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        }
        System.out.println("Redirect from StudentEditPageServelet to /MainPage.jsp");
        req.setAttribute("mainDataForm", mainDataFormForDisplay);
        getServletContext().getRequestDispatcher("/MainPage.jsp").forward(req, resp);
        //getServletContext().getRequestDispatcher("/MainPageServlet").forward(req, resp);
    }

    private void insertStudent(HttpServletRequest req) throws SQLException, ParseException {
        Student preparedStudent = prepareStudent(req);

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");
        managementSystemWebDAO.get().insertStudent(preparedStudent);
    }

    private void updateStudent(HttpServletRequest req) throws SQLException, ParseException {
        Student preparedStudent = prepareStudent(req);

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");
        managementSystemWebDAO.get().updateStudent(preparedStudent);
    }

    private Student prepareStudent(HttpServletRequest req) throws ParseException {
        Student student = new Student();
        student.setStudentId(Integer.parseInt(req.getParameter("student_id")));
        student.setFirstName(req.getParameter("first_name").trim()); // trim - удаляет пробелы вначале и в конце строки
        student.setLastName(req.getParameter("last_name").trim());
        student.setMiddleName(req.getParameter("middle_name").trim());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setDateOfBirth(simpleDateFormat.parse(req.getParameter("date_of_birth").trim()));
        if (req.getParameter("sex").equals("0")) {
            student.setSex('М');
        } else if (req.getParameter("sex").equals("1")) {
            student.setSex('Ж');
        } else {
            student.setSex('Н');
        }
        student.setGroupId(Integer.parseInt(req.getParameter("group_id").trim()));
        student.setEducationYear(Integer.parseInt(req.getParameter("education_year")));
        return student;
    }
}
