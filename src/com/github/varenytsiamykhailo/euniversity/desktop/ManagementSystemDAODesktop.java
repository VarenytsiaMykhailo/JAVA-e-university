package com.github.varenytsiamykhailo.euniversity.desktop;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.ManagementSystemDAO;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagementSystemDAODesktop extends ManagementSystemDAO {

    private static ManagementSystemDAODesktop instance; // статическая переменная для шаблона Singleton

    /**
     * закрытый конструктор.
     * Реализация шаблона Singleton.
     * Экземпляры класса нужно создавать с помощью статического метода getInstance
     */
    private ManagementSystemDAODesktop() {
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystemDAODesktop getInstance() {
        if (instance == null) { // если экземпляр класса не создан, то создать его (выделить память)
            try {
                instance = new ManagementSystemDAODesktop();
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3307/e_university?serverTimezone=Europe/Moscow";
                instance.connection = DriverManager.getConnection(url, "root", "root");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance; // вернуть ссылку на экземпляр класса
    }
}
