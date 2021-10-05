package com.github.varenytsiamykhailo.euniversity.desktop;

import com.github.varenytsiamykhailo.euniversity.desktop.frame.StudentsFrame;

import javax.swing.*;

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
