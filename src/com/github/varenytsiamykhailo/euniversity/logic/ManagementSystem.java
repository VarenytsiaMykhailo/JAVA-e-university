package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public abstract class ManagementSystem {

    public Connection connection;

    /**
     * Получить список студентов для опеределенной группы, определенного года обучения.
     */
    public ArrayList<Student> getStudentsFromGroup(Group group, int year) throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT student_id, first_name, last_name, patronymic, date_of_birth, sex, group_id, education_year " +
                    "FROM all_students " +
                    "WHERE group_id = ? AND education_year = ? " +
                    "ORDER BY last_name, first_name, patronymic");
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs);

                students.add(student);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return students;
    }

    /**
     * Перевести студентов из одной группы с одним годом обучения в другую группу с другим годом обучения
     */
    public void moveStudentsFromGroupToNewGroup (Group oldGroup, int oldYear, Group newGroup, int newYear) throws SQLException {
        PreparedStatement stmt = null;
        try {
           stmt = connection.prepareStatement("UPDATE all_students " +
                   "SET group_id = ?, education_year = ? " +
                   "WHERE group_id = ? AND education_year = ?");
           stmt.setInt(1, newGroup.getGroupId());
           stmt.setInt(2, newYear);
           stmt.setInt(3, oldGroup.getGroupId());
           stmt.setInt(4, oldYear);
           stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Удаляет всех студентов из определенной группы, определенного года обучения
     */
    public void removeStudentsFromGroup(Group group, int year) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE all_students " +
                    "SET group_id = 0 " +
                    "WHERE group_id = ? AND education_year = ?"
            );
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Добавить студента
     */
    public void insertStudent(Student student) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO all_students " +
                    "(first_name, last_name, patronymic, date_of_birth, sex, group_id, education_year) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getPatronymic());
            stmt.setDate(4, new Date(student.getDateOfBirth().getTime()));
            stmt.setString(5, Character.toString(student.getSex()));
            stmt.setInt(6, student.getGroupId());
            stmt.setInt(7, student.getEducationYear());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Обновить данные о студенте
     */
    public void updateStudent(Student newStudentData) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE all_students " +
                    "SET first_name = ?, last_name = ?, patronymic = ?, date_of_birth = ?, sex = ?, group_id = ?, education_year= ? " +
                    "WHERE student_id = ?"
            );
            stmt.setString(1, newStudentData.getFirstName());
            stmt.setString(2, newStudentData.getLastName());
            stmt.setString(3, newStudentData.getPatronymic());
            stmt.setDate(4, new Date(newStudentData.getDateOfBirth().getTime()));
            stmt.setString(5, Character.toString(newStudentData.getSex()));
            stmt.setInt(6, newStudentData.getGroupId());
            stmt.setInt(7, newStudentData.getEducationYear());
            stmt.setInt(8, newStudentData.getStudentId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Удаляет студента. Ищет нужного студента по его ИД и удаляет его
     */
    public void deleteStudent(Student delStudentData) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM all_students " +
                    "WHERE student_id = ?"
            );
            stmt.setInt(1, delStudentData.getStudentId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Поиск студента по переданному id. Если студента с таким id нет - возвращается null
     */
    public Student getStudentById(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT student_id, first_name, last_name, patronymic, date_of_birth, sex, group_id, education_year " +
                    "FROM all_students " +
                    "WHERE student_id = ? " +
                    "ORDER BY last_name, first_name, patronymic");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Поиск куратора по переданному id. Если куратора с таким id нет - возвращается null
     */
    public Curator getCuratorById(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT curator_id, first_name, last_name, patronymic, date_of_birth, sex, year_of_teaching " +
                    "FROM all_curators " +
                    "WHERE curator_id = ? " +
                    "ORDER BY last_name, first_name, patronymic");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Curator(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает коллекцию всех групп из базы данных в виде ArrayList<Group>
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

    /**
     * Возвращает коллекцию всех студентов из базы данных в виде ArrayList<Student>
     */
    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> allStudents = new ArrayList<Student>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                    "SELECT student_id, first_name, last_name, patronymic, date_of_birth, sex, group_id, education_year " +
                            "FROM all_students " +
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
}
