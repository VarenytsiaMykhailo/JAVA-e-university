package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.Group;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class SimpleListServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<b>Список групп:</b>");
        printWriter.println("<table border = 1>");
        ManagementSystemWeb ms = ManagementSystemWeb.getInstance();
        try {
            List<Group> allGroups = ms.getAllGroups();
            for (Group g : allGroups) {
                printWriter.println("<tr>");
                printWriter.println("<td>" + g.getGroupId() + "</td>");
                printWriter.println("<td>" + g.getGroupName() + "</td>");
                printWriter.println("<td>" + g.getCuratorId() + "</td>");
                printWriter.println("<td>" + g.getSpeciality() + "</td>");
                printWriter.println("</tr>");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        printWriter.println("</table>");
    }
}
