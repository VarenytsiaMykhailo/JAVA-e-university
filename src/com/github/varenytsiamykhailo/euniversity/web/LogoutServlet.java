package com.github.varenytsiamykhailo.euniversity.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Enter to LogoutServlet");

        final HttpSession session = req.getSession();

        session.removeAttribute("user");

        resp.sendRedirect(req.getServletContext().getContextPath()); // getContextPath - Корень приложения
    }
}
