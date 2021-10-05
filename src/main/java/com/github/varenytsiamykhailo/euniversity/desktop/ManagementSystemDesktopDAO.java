package com.github.varenytsiamykhailo.euniversity.desktop;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.ManagementSystemDAO;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagementSystemDesktopDAO extends ManagementSystemDAO {

    private static ManagementSystemDesktopDAO instance; // статическая переменная для шаблона Singleton

    /**
     * закрытый конструктор.
     * Реализация шаблона Singleton.
     * Экземпляры класса нужно создавать с помощью статического метода getInstance
     */
    private ManagementSystemDesktopDAO() {
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystemDesktopDAO getInstance() {
        if (instance == null) { // если экземпляр класса не создан, то создать его (выделить память)
            try {
                instance = new ManagementSystemDesktopDAO();
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/e_university?serverTimezone=Europe/Moscow";
                instance.connection = DriverManager.getConnection(url, "root", "admin");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance; // вернуть ссылку на экземпляр класса
    }
}
