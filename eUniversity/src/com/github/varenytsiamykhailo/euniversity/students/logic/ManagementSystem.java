package com.github.varenytsiamykhailo.euniversity.students.logic;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeSet;

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
        loadStudents();
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
        {
            printString("Полный список групп");
            printString("***************************");
            ArrayList<Group> allGroups = ms.getAllGroups();
            for (Group g : allGroups) {
                printString(g);
            }
            printString();
        }

        // Просмотр полного списка студентов
        {
            printString("Полный список студентов");
            printString("***************************");
            Collection<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

        // Вывод списков студентов по группам
        {
            printString("Список студентов по группам");
            printString("***************************");
            ArrayList<Group> groups = ms.getAllGroups();
            for (Group g : groups) {
                printString("---> Группа: " + g.getGroupName());
                Collection<Student> studentsInGroup = ms.getStudentsFromGroup(g, 2020);
                for (Student s : studentsInGroup) {
                    printString("\t" + s);
                }
            }
            printString();
        }

        // Создаем нового студента и добавляем его в список
        {
            Student newStudent = new Student();
            newStudent.setStudentId(5);
            newStudent.setFirstName("Иван");
            newStudent.setLastName("Дубин");
            newStudent.setPatronymic("Алексеевич");
            Calendar c = Calendar.getInstance();
            c.set(1998, Calendar.NOVEMBER,29);
            newStudent.setDateOfBirth(c.getTime());
            newStudent.setSex(Sex.MALE);
            newStudent.setGroupId(1);
            newStudent.setEducationYear(2020);

            printString("Добавление студента: " + newStudent);
            printString("***************************");
            ms.insertStudent(newStudent);
            printString("--->> Полный список студентов после добавления:");
            Collection<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

        // Изменяем данные о студенте
        {
            Student newStudent = new Student();
            newStudent.setStudentId(5);
            newStudent.setFirstName("новыйИван");
            newStudent.setLastName("новыйДубин");
            newStudent.setPatronymic("Алексеевич");
            Calendar c = Calendar.getInstance();
            c.set(1998, Calendar.NOVEMBER,29);
            newStudent.setDateOfBirth(c.getTime());
            newStudent.setSex(Sex.MALE);
            newStudent.setGroupId(1);
            newStudent.setEducationYear(2020);

            printString("Редактирование данных студента: " + newStudent);
            printString("***************************");
            ms.updateStudent(newStudent);
            printString("--->> Полный список студентов после редактирования:");
            Collection<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

    }

    /**
     * Создает две группы и помещает их в коллекцию для групп
     */
    public void loadGroups() {
        if (allGroups == null)
            allGroups = new ArrayList<Group>();
        else
            allGroups.clear();

        Group g = null;

        // Создаем первую группу
        {
            g = new Group();
            g.setGroupId(1);
            g.setGroupName("Первая группа");
            g.setCurator(new Curator());
            g.setSpeciality("Программная инженерия");
            allGroups.add(g);
        }

        // Создаем вторую группу
        {
            g = new Group();
            g.setGroupId(2);
            g.setGroupName("Вторая группа");
            g.setCurator(new Curator());
            g.setSpeciality("Информационная безопасность");
            allGroups.add(g);
        }
    }

    /**
     * Создает несколько студентов и помещает их в коллекцию
     */
    public void loadStudents() {
        if (allStudents == null) {
            allStudents = new TreeSet<Student>();
        } else {
            allStudents.clear();
        }

        Student s = null;
        Calendar c = Calendar.getInstance();

        // Создаем студентов второй группы
        {
            s = new Student();
            s.setStudentId(1);
            s.setFirstName("Марк");
            s.setLastName("Конюхов");
            s.setPatronymic("Сергеевич");
            c.set(1999, Calendar.SEPTEMBER, 04);
            s.setDateOfBirth(c.getTime());
            s.setSex(Sex.MALE);
            s.setGroupId(2);
            s.setEducationYear(2020);
            allStudents.add(s);

            s = new Student();
            s.setStudentId(2);
            s.setFirstName("Игорь");
            s.setLastName("Игорев");
            s.setPatronymic("Игоревич");
            c.set(2000, Calendar.AUGUST, 03);
            s.setDateOfBirth(c.getTime());
            s.setSex(Sex.MALE);
            s.setGroupId(2);
            s.setEducationYear(2020);
            allStudents.add(s);
        }

        // Создаем студентов первой группы
        {
            s = new Student();
            s.setStudentId(3);
            s.setFirstName("Татьяна");
            s.setLastName("Пушкина");
            s.setPatronymic("Михайловна");
            c.set(1999, Calendar.APRIL, 02);
            s.setDateOfBirth(c.getTime());
            s.setSex(Sex.FAMALE);
            s.setGroupId(1);
            s.setEducationYear(2020);
            allStudents.add(s);

            s = new Student();
            s.setStudentId(4);
            s.setFirstName("Андрей");
            s.setLastName("Володин");
            s.setPatronymic("Птушкинович");
            c.set(2000, Calendar.MARCH, 11);
            s.setDateOfBirth(c.getTime());
            s.setSex(Sex.MALE);
            s.setGroupId(1);
            s.setEducationYear(2020);
            allStudents.add(s);
        }
    }

    /**
     * Получить список студентов для опеределенной группы, определенного года обучения.
     */
    public Collection<Student> getStudentsFromGroup(Group group, int year) {
        Collection<Student> studentsInGroup = new TreeSet<Student>();
        for (Student s: allStudents) {
            if (s.getGroupId() == group.getGroupId() && s.getEducationYear() == year) {
                studentsInGroup.add(s);
            }
        }
        return studentsInGroup;
    }

    /**
     * Перевести студентов из одной группы с одним годом обучения в другую группу с другим годом обучения
     */
    public void moveStudentsToGroup(Group oldGroup, int oldYear, Group newGroup, int newYear) {
        for (Student s: allStudents) {
            if (s.getGroupId() == oldGroup.getGroupId() && s.getEducationYear() == oldYear) {
                s.setGroupId(newGroup.getGroupId());
                s.setEducationYear(newYear);
            }
        }
    }

    /**
     * Добавить студента
     */
    public void insertStudent(Student student) {
        allStudents.add(student);
    }

    /**
     * Обновить данные о студенте
     */
    public void updateStudent(Student newStudentData) {
        // Ищем нужного студента по его ИД и заменяем поля
        Student updStudent = null;
        for (Student s : allStudents) {
            if (s.getStudentId() == newStudentData.getStudentId()) {
                updStudent = s;
                break;
            }
        }
        updStudent.setFirstName(newStudentData.getFirstName());
        updStudent.setLastName(newStudentData.getLastName());
        updStudent.setPatronymic(newStudentData.getPatronymic());
        updStudent.setDateOfBirth(newStudentData.getDateOfBirth());
        updStudent.setSex(newStudentData.getSex());
        updStudent.setGroupId(newStudentData.getGroupId());
        updStudent.setEducationYear(newStudentData.getEducationYear());

    }

    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public Collection<Student> getAllStudents() {
        return allStudents;
    }

    // Меняем кодировку на UTF-8
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
