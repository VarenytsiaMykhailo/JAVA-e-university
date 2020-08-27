package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;
import com.github.varenytsiamykhailo.euniversity.web.forms.MainDataFormForDisplay;
import com.github.varenytsiamykhailo.euniversity.web.forms.StudentDataFormForDisplay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class MainPageServlet extends HttpServlet {

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

        System.out.println("Enter to MainPageServlet");

        @SuppressWarnings("unchecked")
        final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        if (req.getParameter("insert_student") != null) {
            // Вызов другой формы, которая перенеаправляет сервлет на другую JSP для ввода данных о новом студенте
            try {
                Student student = new Student();
                student.setStudentId(0);
                student.setDateOfBirth(new Date());
                student.setEducationYear(Calendar.getInstance().get(Calendar.YEAR)); // Дефолтное значение - текущий год
                ArrayList<Group> allGroups = managementSystemWebDAO.get().getAllGroups();
                StudentDataFormForDisplay studentDataFormForDisplay = new StudentDataFormForDisplay();
                studentDataFormForDisplay.initFromStudent(student);
                studentDataFormForDisplay.setAllGroups(allGroups);
                req.setAttribute("studentDataForm", studentDataFormForDisplay);
                System.out.println("Redirect from MainPageServlet to /StudentEditPage.jsp");
                getServletContext().getRequestDispatcher("/StudentEditPage.jsp").forward(req, resp);

                return;

            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        if (req.getParameter("update_student") != null) {
            // Вызов другой формы, которая перенеаправляет сервлет на другую JSP для ввода данных о студенте
            try {
                if (req.getParameter("student_id") != null) {
                    int studentId = Integer.parseInt(req.getParameter("student_id"));
                    Student student = managementSystemWebDAO.get().getStudentById(studentId);
                    ArrayList<Group> allGroups = managementSystemWebDAO.get().getAllGroups();
                    StudentDataFormForDisplay studentDataFormForDisplay = new StudentDataFormForDisplay();
                    studentDataFormForDisplay.initFromStudent(student);
                    studentDataFormForDisplay.setAllGroups(allGroups);
                    req.setAttribute("studentDataForm", studentDataFormForDisplay);
                    System.out.println("Redirect from MainPageServlet to /StudentEditPage.jsp");
                    getServletContext().getRequestDispatcher("/StudentEditPage.jsp").forward(req, resp);

                    return;

                }
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        if (req.getParameter("delete_student") != null) {
            try {
                if (req.getParameter("student_id") != null) {
                    Student student = new Student();
                    student.setStudentId(Integer.parseInt(req.getParameter("student_id")));
                    managementSystemWebDAO.get().deleteStudent(student);

                    // Посылаем в jsp инфу об успешном удалении студента. Нужно для вывода всплывающего окна об успешности.
                    req.setAttribute("successfulStudentDeletionNotification", Boolean.TRUE);
                }
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        String groupIdString = req.getParameter("selected_group_id");
        String yearString = req.getParameter("selected_year");

        if (req.getParameter("move_group") != null) {
            // Перемещаем всех студентов в другую группу
            String newGroupIdString = req.getParameter("new_group_id");
            String newYearString = req.getParameter("new_year");
            try {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(groupIdString));

                Group newGroup = new Group();
                newGroup.setGroupId(Integer.parseInt(newGroupIdString));

                managementSystemWebDAO.get().moveStudentsFromGroupToNewGroup(group, Integer.parseInt(yearString), newGroup, Integer.parseInt(newYearString));

                // Устанавливаем новые значения. Нужно для показа группы, в которую мы переместили студентов
                groupIdString = newGroupIdString;
                yearString = newYearString;

                // Посылаем в jsp инфу об успешном добавлении группы. Нужно для вывода всплывающего окна об успешности.
                req.setAttribute("successfulMoveGroupNotification", Boolean.TRUE);

            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        int selectedGroupId = -1;
        if (groupIdString != null) {
            selectedGroupId = Integer.parseInt(groupIdString);
        }

        int selectedYear = Calendar.getInstance().get(Calendar.YEAR); // Дефолтное значение - текущий год
        if (yearString != null) {
            selectedYear = Integer.parseInt(yearString);
        }

        MainDataFormForDisplay mainDataFormForDisplay = new MainDataFormForDisplay();
        try {
            ArrayList<Group> allGroups = managementSystemWebDAO.get().getAllGroups();
            Group group = new Group();
            group.setGroupId(selectedGroupId);
            if (selectedGroupId == -1) { // Если группа не выбрала в списке
                Iterator it = allGroups.iterator();
                group = (Group) it.next();
            }

            ArrayList<Student> studentsForSelectedGroup = managementSystemWebDAO.get().getStudentsFromGroup(group, selectedYear);
            mainDataFormForDisplay.setSelectedGroupId(group.getGroupId());
            mainDataFormForDisplay.setSelectedYear(selectedYear);
            mainDataFormForDisplay.setAllGroups(allGroups);
            mainDataFormForDisplay.setStudentsForSelectedGroup(studentsForSelectedGroup);
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        }

        // Отладка:
        System.out.println();
        System.out.println(">>>>>>>>>>> Prepared mainDataFormForDisplay:");
        System.out.println(mainDataFormForDisplay);
        System.out.println("selected year = " + mainDataFormForDisplay.getSelectedYear());
        System.out.println("selected groupId = " + mainDataFormForDisplay.getSelectedGroupId());
        System.out.println("allGroups = " + mainDataFormForDisplay.getAllGroups());
        System.out.println("StudentsForSelectedGroup = " + mainDataFormForDisplay.getStudentsForSelectedGroup());
        System.out.println();

        req.setAttribute("mainDataForm", mainDataFormForDisplay);
        System.out.println("Redirect from MainPageServlet to /MainPage.jsp");
        getServletContext().getRequestDispatcher("/MainPage.jsp").forward(req, resp);
    }
}
