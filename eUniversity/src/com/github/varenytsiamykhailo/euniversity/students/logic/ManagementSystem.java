package com.github.varenytsiamykhailo.euniversity.students.logic;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

public class ManagementSystem {

    private static ManagementSystem instance; // статическая переменная для шаблона Singleton

    private ArrayList<Group> allGroups;

    private Collection<Student> allStudents;

    /*
    * закрытый конструктор.
    * Реализация шаблона Singleton.
    * Экземпляры класса нужно создавать с помощью метода статического getInstance
    * */
    private ManagementSystem() {
        loadGroups();
        //loadStudents();
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystem getInstance() {
        if (instance == null) // если экземпляр класса не создан, то создать его (выделить память)
            instance = new ManagementSystem();
        return instance; // вернуть ссылку на экземпляр класса
    }

    public static void main(String[] args) {
        // перенаправляем стандартный поток вывода в текстовый файл. Нужно для корректной работы с различными языками и избежания крокозябр
        try {
            System.setOut(new PrintStream("out.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        ManagementSystem ms = ManagementSystem.getInstance();

        // Просмотр полного списка групп
        printString("Полный список групп");
        printString("*******************");
        ArrayList<Group> allGroups = ms.getAllGroups();
        for (Group g : allGroups) {
            printString(g);
        }
        printString();
    }

    /**
     * создает две группы и помещает их в коллекцию для групп
     */
    public void loadGroups() {
        if (allGroups == null)
            allGroups = new ArrayList<Group>();
        else
            allGroups.clear();
        Group g = null;


        g = new Group();
        g.setGroupId(1);
        g.setGroupName("Первая группа");
        g.setCurator(new Curator());
        g.setSpeciality("Программная инженерия");
        allGroups.add(g);

        g = new Group();
        g.setGroupId(2);
        g.setGroupName("Вторая группа");
        g.setCurator(new Curator());
        g.setSpeciality("Информационная безопасность");
        allGroups.add(g);
    }

    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public Collection<Student> getAllStudents() {
        return allStudents;
    }

    // меняем кодировку на UTF-8
    public static void printString(Object str) {
        try {
            System.out.println(new String(str.toString().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void printString() {
        System.out.println();
    }

}
