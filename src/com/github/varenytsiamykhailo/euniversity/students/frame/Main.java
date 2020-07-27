package com.github.varenytsiamykhailo.euniversity.students.frame;

import javax.swing.*;

public class Main {
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
