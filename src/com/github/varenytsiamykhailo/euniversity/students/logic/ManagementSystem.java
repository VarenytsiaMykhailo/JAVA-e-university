package com.github.varenytsiamykhailo.euniversity.students.logic;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

public class ManagementSystem {

    private static ManagementSystem instance; // статическая переменная для шаблона Singleton

    private static Connection connection;
    /*
    private ArrayList<Group> allGroups;

    private HashSet<Student> allStudents;

    private HashSet<Curator> allCurators;
    */

    /**
     * закрытый конструктор.
     * Реализация шаблона Singleton.
     * Экземпляры класса нужно создавать с помощью статического метода getInstance
     */
    private ManagementSystem() {
        /*
        loadGroups();
        loadStudents();
        loadCurators();
        */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/e_university";
            connection = DriverManager.getConnection(url, "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * статический метод для получения экземпляра класса (реализация шаблона Singleton)
     */
    public static synchronized ManagementSystem getInstance() {
        if (instance == null) // если экземпляр класса не создан, то создать его (выделить память)
            instance = new ManagementSystem();
        return instance; // вернуть ссылку на экземпляр класса
    }

/* Старая версия для тестов, проверок.
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
            HashSet<Student> allStudents = ms.getAllStudents();
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
                HashSet<Student> studentsInGroup = ms.getStudentsFromGroup(g, 2020);
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
            c.set(1998, Calendar.NOVEMBER, 29);
            newStudent.setDateOfBirth(c.getTime());
            newStudent.setSex(Sex.MALE);
            newStudent.setGroupId(1);
            newStudent.setEducationYear(2020);

            printString("Добавление студента: " + newStudent);
            printString("***************************");
            ms.insertStudent(newStudent);
            printString("--->> Полный список студентов после добавления:");
            HashSet<Student> allStudents = ms.getAllStudents();
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
                HashSet<Student> studentsInGroup = ms.getStudentsFromGroup(g, 2020);
                for (Student s : studentsInGroup) {
                    printString("\t" + s);
                }
            }
            printString();
        }

        // Изменяем данные о студенте. Затем удаляем его
        {
            Student newStudent = new Student();
            newStudent.setStudentId(5);
            newStudent.setFirstName("новыйИван");
            newStudent.setLastName("новыйДубин");
            newStudent.setPatronymic("Алексеевич");
            Calendar c = Calendar.getInstance();
            c.set(1998, Calendar.NOVEMBER, 29);
            newStudent.setDateOfBirth(c.getTime());
            newStudent.setSex(Sex.MALE);
            newStudent.setGroupId(1);
            newStudent.setEducationYear(2020);

            printString("Редактирование данных студента по id: " + ms.getStudentById(newStudent.getStudentId()) + " . \nНовые данные студента: " + newStudent);
            printString("***************************");
            ms.updateStudent(newStudent);
            printString("--->> Полный список студентов после редактирования:");
            HashSet<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();

            // Удалим студента
            printString("Удаление студента: " + newStudent);
            printString("***************************");
            ms.deleteStudent(newStudent);
            printString("--->> Полный список студентов после удаления:");
            allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

        // Переводим всех студентов одной группы в другую
        {
            Group g1 = ms.allGroups.get(0);
            Group g2 = ms.allGroups.get(1);
            printString("Перевод студентов из 1-ой во 2-ю группу.");
            printString("***************************");
            ms.moveStudentsToGroup(g1, 2020, g2, 2021);
            printString("--->> Полный список студентов после перевода:");
            HashSet<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

        // Удаляем всех студентов из группы
        {
            Group g2 = ms.getAllGroups().get(1);
            int delYear = 2020;
            printString("Удаление студентов из группы: " + g2 + " в " + delYear + " году.");
            printString("***************************");
            ms.removeStudentsFromGroup(g2, delYear);
            printString("--->> Полный список студентов после удаления");
            HashSet<Student> allStudents = ms.getAllStudents();
            for (Student s : allStudents) {
                printString(s);
            }
            printString();
        }

    }
*/

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
            g.setCuratorId(1);
            g.setSpeciality("Программная инженерия");
            allGroups.add(g);
        }

        // Создаем вторую группу
        {
            g = new Group();
            g.setGroupId(2);
            g.setGroupName("Вторая группа");
            g.setCuratorId(2);
            g.setSpeciality("Информационная безопасность");
            allGroups.add(g);
        }
    }

    /**
     * Создает несколько студентов и помещает их в коллекцию
     */
    public void loadStudents() {
        if (allStudents == null) {
            allStudents = new HashSet<Student>();
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
            s.setSex('М');
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
            s.setSex('М');
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
            s.setSex('Ж');
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
            s.setSex('М');
            s.setGroupId(1);
            s.setEducationYear(2020);
            allStudents.add(s);
        }
    }

    /**
     * Создает несколько кураторов и помещает их в коллекцию
     */
    public void loadCurators() {
        if (allCurators == null) {
            allCurators = new HashSet<Curator>();
        } else {
            allCurators.clear();
        }

        Curator curator = null;
        Calendar c = Calendar.getInstance();

        // Создаем первого куратора
        {
            curator = new Curator();
            curator.setCuratorId(1);
            curator.setFirstName("Петр");
            curator.setLastName("Шапкин");
            curator.setPatronymic("Владимирович");
            c.set(1980, Calendar.APRIL, 02);
            curator.setDateOfBirth(c.getTime());
            curator.setSex('М');
            allCurators.add(curator);
        }

        // Создаем второго куратора
        {
            curator = new Curator();
            curator.setCuratorId(1);
            curator.setFirstName("Николай");
            curator.setLastName("Безверхний");
            curator.setPatronymic("Владимирович");
            c.set(1970, Calendar.APRIL, 02);
            curator.setDateOfBirth(c.getTime());
            curator.setSex('М');
            allCurators.add(curator);
        }
    }

    /**
     * Получить список студентов для опеределенной группы, определенного года обучения.
     */
    public HashSet<Student> getStudentsFromGroup(Group group, int year) {
        HashSet<Student> studentsInGroup = new HashSet<Student>();
        for (Student s : allStudents) {
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
        for (Student s : allStudents) {
            if (s.getGroupId() == oldGroup.getGroupId() && s.getEducationYear() == oldYear) {
                s.setGroupId(newGroup.getGroupId());
                s.setEducationYear(newYear);
            }
        }
    }

    /**
     * Удаляет всех студентов из определенной группы, определенного года обучения
     */
    public void removeStudentsFromGroup(Group group, int year) {
        /* // вылетает java.util.ConcurrentModificationException
        for (Student s : allStudents) {
            if (s.getGroupId() == group.getGroupId() && s.getEducationYear() == year) {
                allStudents.remove(s);
            }
        }*/

        allStudents.removeIf(s -> s.getGroupId() == group.getGroupId() && s.getEducationYear() == year);
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

    /**
     * Удаляет студента. Ищет нужного студента по его ИД и удаляет его
     */
    public void deleteStudent(Student delStudentData) {
        // Ищем нужного студента по его ИД и удаляем
        Student delStudent = null;
        for (Student s : allStudents) {
            if (s.getStudentId() == delStudentData.getStudentId()) {
                delStudent = s;
                break;
            }
        }
        allStudents.remove(delStudent);
    }

    /**
     * Поиск студента по переданному id. Если студента с таким id нет - возвращается null
     */
    public Student getStudentById(int id) {
        for (Student s : allStudents) {
            if (s.getStudentId() == id) {
                return s;
            }
        }
        return null;
    }

/* Старая версия
    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }
*/

    public ArrayList<Group> getAllGroups() throws SQLException {
        ArrayList<Group> allGroups = new ArrayList<Group>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM all_groups");
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getInt(1));
                group.setGroupName(rs.getString(2));
                group.setCuratorId(rs.getInt(3));
                group.setSpeciality(rs.getString(4));

                allGroups.add(group);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }

        return allGroups;
    }

/* Старая версия
    public HashSet<Student> getAllStudents() {
        return allStudents;
    }
*/

    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> allStudents = new ArrayList<Student>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                        "SELECT student_id, first_name, last_name, patronymic, date_of_birth, sex, group_id, education_year" +
                            "FROM all_students" +
                            "ORDER BY last_name, first_name, patronymic");
            while (rs.next()) {
                Student student = new Student(rs); // Класс Student сам обрабатывает переданный ему ResultSet
                allStudents.add(student);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }

        return allStudents;
    }



/* Старая версия для тестов, проверок
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
*/

}
