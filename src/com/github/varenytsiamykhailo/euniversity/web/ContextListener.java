package com.github.varenytsiamykhailo.euniversity.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ContextListener put user ManagementSystemWebDAO to servlet context.
 */
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // AtomicReference<ManagementSystemWebDAO> managementSystemWebDAO = new AtomicReference<ManagementSystemWebDAO>(ManagementSystemWebDAO.getInstance()); // ManagementSystemWebDAO нужно обернуть в объект-обертку atomic, который может безопасно работать с многопоточностью.
        final ServletContext servletContext = sce.getServletContext();
        // servletContext.setAttribute("managementSystemWebDAO", managementSystemWebDAO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Здесь нужно закрыть подключение к базе данных?
    }
}
