package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.UserDAO;
import com.github.varenytsiamykhailo.euniversity.logic.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ContextListener put user DAO ti servlet context.
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private AtomicReference<UserDAO> userDAO; // UserDAO нужно обернуть в объект-обертку atomic, который может безопасно работать с многопоточностью.

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        userDAO = new AtomicReference<>(new UserDAO());

        userDAO.get().addUser(new User(1, "Admin", "123", User.Role.ADMIN));
        userDAO.get().addUser(new User(2, "User", "123", User.Role.USER));

        final ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("userDAO", userDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        userDAO = null; // Здесь нужно закрыть подключение к базе данных
    }
}
