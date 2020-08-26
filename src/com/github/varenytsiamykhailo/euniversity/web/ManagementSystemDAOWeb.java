package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.ManagementSystemDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class ManagementSystemDAOWeb extends ManagementSystemDAO {

    private static ManagementSystemDAOWeb instance; // статическая переменная для шаблона Singleton

    /**
     * закрытый конструктор.
     * Реализация шаблона Singleton.
     * Экземпляры класса нужно создавать с помощью статического метода getInstance
     */
    private ManagementSystemDAOWeb() {
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystemDAOWeb getInstance() {
        if (instance == null) { // если экземпляр класса не создан, то создать его (выделить память)
            try {
                instance = new ManagementSystemDAOWeb();
                Context context = new InitialContext();
                DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysqlconnector");
                instance.connection = dataSource.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance; // вернуть ссылку на экземпляр класса
    }
}
