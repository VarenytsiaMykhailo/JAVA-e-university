package com.github.varenytsiamykhailo.euniversity.desktop;

import com.github.varenytsiamykhailo.euniversity.desktop.frame.StudentsFrame;
import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class runEuniversityDesktop {
    public static void main(String[] args) {
        // Запуск формы лучше производить в специальном треде Event-dispatching thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentsFrame studentsFrame = new StudentsFrame();
                studentsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                studentsFrame.setVisible(true);
            }
        });
    }
}
