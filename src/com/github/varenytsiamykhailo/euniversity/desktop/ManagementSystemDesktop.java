package com.github.varenytsiamykhailo.euniversity.desktop;

import com.github.varenytsiamykhailo.euniversity.logic.ManagementSystem;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagementSystemDesktop extends ManagementSystem {

    private static ManagementSystemDesktop instance; // статическая переменная для шаблона Singleton

    /**
     * закрытый конструктор.
     * Реализация шаблона Singleton.
     * Экземпляры класса нужно создавать с помощью статического метода getInstance
     */
    private ManagementSystemDesktop() {
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystemDesktop getInstance() {
        if (instance == null) { // если экземпляр класса не создан, то создать его (выделить память)
            try {
                instance = new ManagementSystemDesktop();
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
