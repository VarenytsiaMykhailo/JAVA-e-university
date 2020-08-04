package com.github.varenytsiamykhailo.euniversity.desktop.frame;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.ManagementSystem;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

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

        /*
        Main testing = new Main();
        testing.testManagementSystemMethods();
        */
    }

    private void testManagementSystemMethods() {
        ManagementSystem ms = ManagementSystem.getInstance();
        try {
            // Проверка метода getAllGroups
            System.out.println("        Проверка метода getAllGroups");
            ArrayList<Group> allGroups = ms.getAllGroups();
            for (Group g : allGroups) {
                System.out.println(g);
            }

            Group gr1 = allGroups.get(0);
            Group gr2 = allGroups.get(1);
            System.out.println();
            System.out.println("gr1 = " + gr1.toString());
            System.out.println("gr2 = " + gr2.toString());


            // Проверка метода getAllStudents
            {
                System.out.println();
                System.out.println("        Проверка метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода getStudentById
            {
                System.out.println();
                System.out.println("        Проверка метода getStudentById");
                Student st1 = ms.getStudentById(1);
                System.out.println("st1 = " + st1);
                Student st2 = ms.getStudentById(2);
                System.out.println("st2 = " + st2);
                Student st3 = ms.getStudentById(3);
                System.out.println("st3 = " + st3);
                Student st4 = ms.getStudentById(4);
                System.out.println("st4 = " + st4);
                Student st5 = ms.getStudentById(5);
                System.out.println("st5 = " + st5);
                Student st6 = ms.getStudentById(6);
                System.out.println("st6 = " + st6);
                Student st7Nullable = ms.getStudentById(7);
                System.out.println("st7Nullable = " + st7Nullable);
            }

            // Проверка метода getStudentsFromGroup
            {
                System.out.println();
                System.out.println("        Проверка метода getStudentsFromGroup");

                System.out.println("studentsInGr1 2020:");
                ArrayList<Student> studentsInGr1 = ms.getStudentsFromGroup(gr1, 2020);
                for (Student s : studentsInGr1) {
                    System.out.println(s);
                }

                System.out.println("studentsInGr1 2019:");
                studentsInGr1 = ms.getStudentsFromGroup(gr1, 2019);
                for (Student s : studentsInGr1) {
                    System.out.println(s);
                }

                System.out.println("studentsInGr2 1999:");
                ArrayList<Student> studentsInGr2 = ms.getStudentsFromGroup(gr2, 1999);
                for (Student s : studentsInGr2) {
                    System.out.println(s);
                }
            }

            // Проверка метода moveStudentsToGroup - перевод из второй группы в нулевую
            {
                System.out.println();
                System.out.println("        Проверка метода moveStudentsToGroup - перевод из второй группы в нулевую");

                Group nullGroup = new Group();
                nullGroup.setGroupId(0);
                System.out.println("nullGroup:");
                System.out.println(nullGroup);
                System.out.println("null group id is: " + nullGroup.getGroupId());
                System.out.println();
                ms.moveStudentsToGroup(gr2, 2020, nullGroup, 2019);
            }

            // Вызов метода getAllStudents
            {
                System.out.println();
                System.out.println("        Вызов метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода moveStudentsToGroup - перевод из нулевой группы во вторую
            {
                System.out.println();
                System.out.println("        Проверка метода moveStudentsToGroup - перевод из нулевой группы во вторую");

                Group nullGroup = new Group();
                nullGroup.setGroupId(0);
                System.out.println("nullGroup:");
                System.out.println(nullGroup);
                System.out.println("null group id is: " + nullGroup.getGroupId());
                System.out.println();
                ms.moveStudentsToGroup(nullGroup, 2019, gr2, 2021);
            }

            // Проверка метода getAllStudents после перевода null группы во вторую
            {
                System.out.println();
                System.out.println("        Проверка метода getAllStudents после перевода null группы во вторую");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода removeStudentsFromGroup
            {
                System.out.println();
                System.out.println("        Проверка метода removeStudentsFromGroup");
                ms.removeStudentsFromGroup(gr2, 2021);
            }

            // Вызов метода getAllStudents
            {
                System.out.println();
                System.out.println("        Вызов метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода insertStudent
            {
                System.out.println();
                System.out.println("        Проверка метода insertStudent");
                Student student = new Student();
                ms.insertStudent(student);
            }

            // Вызов метода getAllStudents
            {
                System.out.println();
                System.out.println("        Вызов метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода updateStudent
            {
                System.out.println();
                System.out.println("        Проверка метода updateStudent");
                Student student = ms.getStudentById(7);
                student.setFirstName("Андрей");
                student.setLastName("Вано");
                student.setPatronymic("Андреевич");
                student.setSex('М');
                student.setGroupId(2);
                student.setEducationYear(2025);
                ms.updateStudent(student);
            }

            // Вызов метода getAllStudents
            {
                System.out.println();
                System.out.println("        Вызов метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

            // Проверка метода deleteStudent
            {
                System.out.println();
                System.out.println("        Проверка метода deleteStudent");
                Student student = ms.getStudentById(7);
                ms.deleteStudent(student);
            }

            // Вызов метода getAllStudents
            {
                System.out.println();
                System.out.println("        Вызов метода getAllStudents");
                ArrayList<Student> allStudents = ms.getAllStudents();
                System.out.println("all students:");
                for (Student s : allStudents) {
                    System.out.println(s);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
