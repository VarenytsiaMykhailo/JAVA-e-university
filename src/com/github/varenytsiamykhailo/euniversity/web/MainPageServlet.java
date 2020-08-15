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

        if (req.getParameter("insert_student") != null) {
            // Вызов другой формы, которая перенеаправляет сервлет на другую JSP для ввода данных о новом студенте
            try {
                Student student = new Student();
                student.setStudentId(0);
                student.setDateOfBirth(new Date());
                student.setEducationYear(Calendar.getInstance().get(Calendar.YEAR)); // Дефолтное значение - текущий год
                ArrayList<Group> allGroups = ManagementSystemWeb.getInstance().getAllGroups();
                StudentDataFormForDisplay studentDataFormForDisplay = new StudentDataFormForDisplay();
                studentDataFormForDisplay.initFromStudent(student);
                studentDataFormForDisplay.setAllGroups(allGroups);
                req.setAttribute("studentDataForm", studentDataFormForDisplay);
                getServletContext().getRequestDispatcher("/StudentFrame.jsp").forward(req, resp);

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
                    Student student = ManagementSystemWeb.getInstance().getStudentById(studentId);
                    ArrayList<Group> allGroups = ManagementSystemWeb.getInstance().getAllGroups();
                    StudentDataFormForDisplay studentDataFormForDisplay = new StudentDataFormForDisplay();
                    studentDataFormForDisplay.initFromStudent(student);
                    studentDataFormForDisplay.setAllGroups(allGroups);
                    req.setAttribute("studentDataForm", studentDataFormForDisplay);
                    getServletContext().getRequestDispatcher("/StudentFrame.jsp").forward(req, resp);

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
                    student.setStudentId(Integer.parseInt(req.getParameter("studentId")));
                    ManagementSystemWeb.getInstance().deleteStudent(student);
                }
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        String groupIdString = req.getParameter("group_id");
        String yearString = req.getParameter("year");

        if (req.getParameter("move_group") != null) {
            // Перемещаем всех студентов в другую группу
            String newGroupIdString = req.getParameter("new_group_id");
            String newYearString = req.getParameter("new_year");
            try {
                Group group = new Group();
                group.setGroupId(Integer.parseInt(groupIdString));

                Group newGroup = new Group();
                newGroup.setGroupId(Integer.parseInt(newGroupIdString));

                ManagementSystemWeb.getInstance().moveStudentsFromGroupToNewGroup(group, Integer.parseInt(yearString), newGroup, Integer.parseInt(newYearString));

                // Устанавливаем новые значения. Нужно для показа группы, в которую мы переместили студентов
                groupIdString = newGroupIdString;
                yearString = newYearString;
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
            ArrayList<Group> allGroups = ManagementSystemWeb.getInstance().getAllGroups();
            Group group = new Group();
            group.setGroupId(selectedGroupId);
            if (selectedGroupId == -1) { // Если группа не выбрала в списке
                Iterator it = allGroups.iterator();
                group = (Group) it.next();
            }

            ArrayList<Student> studentsForSelectedGroup = ManagementSystemWeb.getInstance().getStudentsFromGroup(group, selectedYear);
            mainDataFormForDisplay.setSelectedGroupId(group.getGroupId());
            mainDataFormForDisplay.setSelectedYear(selectedYear);
            mainDataFormForDisplay.setAllGroups(allGroups);
            mainDataFormForDisplay.setStudentsForSelectedGroup(studentsForSelectedGroup);
        } catch (SQLException e) {
            throw new IOException(e.getMessage());
        }
/* Дебаг
        System.out.println();
        System.out.println("prepared mainDataFormForDisplay:");
        System.out.println(mainDataFormForDisplay);
        System.out.println("selected year = " + mainDataFormForDisplay.getSelectedYear());
        System.out.println("selected groupId = " + mainDataFormForDisplay.getSelectedGroupId());
        System.out.println("allGroups = " + mainDataFormForDisplay.getAllGroups());
        System.out.println("StudentsForSelectedGroup = " + mainDataFormForDisplay.getStudentsForSelectedGroup());
        System.out.println();
*/

        req.setAttribute("mainDataForm", mainDataFormForDisplay);
        getServletContext().getRequestDispatcher("/MainPageFrame.jsp").forward(req, resp);
    }
}
