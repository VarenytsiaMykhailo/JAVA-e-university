package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.*;
import com.github.varenytsiamykhailo.euniversity.web.forms.MainDataFormForDisplay;
import com.github.varenytsiamykhailo.euniversity.web.forms.StudentDataFormForDisplay;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class DepartmentStaffServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Установка кодировки для принятия параметров (запроса)

        System.out.println("Enter to DepartmentStaffServlet");

        String selectedTable = req.getParameter("selected_table");
        if (selectedTable == null) {
            selectedTable = "departmentStaff";
        }


        @SuppressWarnings("unchecked") final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

        if (selectedTable.equals("departmentStaff")) {
            List<DepartmentPerson> departmentStaff;
            try {
                departmentStaff = managementSystemWebDAO.get().getDepartmentStaff();
                req.setAttribute("departmentStaff", departmentStaff);
            } catch (SQLException e) {
                throw new IOException(e);
            }
        } else if (selectedTable.equals("curators")) {
            List<Curator> curators;
            try {
                curators = managementSystemWebDAO.get().getCurators();
                req.setAttribute("curators", curators);
            } catch (SQLException e) {
                throw new IOException(e);
            }
        } else if (selectedTable.equals("scientists")) {
            List<Scientist> scientists;
            try {
                scientists = managementSystemWebDAO.get().getScientists();
                req.setAttribute("scientists", scientists);
            } catch (SQLException e) {
                throw new IOException(e);
            }
        }

        System.out.println("Redirect from DepartmentStaffServlet to /DepartmentStaffPage.jsp");
        getServletContext().getRequestDispatcher("/DepartmentStaffPage.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("get_selected_table") != null) {
            doGet(req, resp);
        } else if (req.getParameter("insert_department_person") != null) {
            DepartmentPerson departmentPerson = new DepartmentPerson();
            req.setAttribute("departmentPerson", departmentPerson);
            System.out.println("Redirect from DepartmentStaffServlet to /DepartmentPersonEditPage.jsp");
            getServletContext().getRequestDispatcher("/DepartmentPersonEditPage.jsp").forward(req, resp);
        } else if (req.getParameter("update_department_person") != null) {
            @SuppressWarnings("unchecked") final AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = (AtomicReference<ManagementSystemWebDAO>) req.getServletContext().getAttribute("managementSystemWebDAO");

            DepartmentPerson departmentPerson;
            int personId = Integer.parseInt(req.getParameter("person_id"));
            try {
                departmentPerson = managementSystemWebDAO.get().getDepartmentPersonById(personId);
            } catch (SQLException e) {
                throw new IOException(e);
            }
            req.setAttribute("departmentPerson", departmentPerson);
            System.out.println("Redirect from DepartmentStaffServlet to /DepartmentPersonEditPage.jsp");
            getServletContext().getRequestDispatcher("/DepartmentPersonEditPage.jsp").forward(req, resp);
        }


    }

}

