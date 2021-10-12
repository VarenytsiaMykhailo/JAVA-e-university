package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Student implements Comparable {

    private int studentId;

    private int studentNumber;

    private String firstName;

    private String lastName;

    private String middleName;

    private char sex;

    private Date dateOfBirth;

    private int groupId;


    // Конструктор по умолчанию
    public Student() {
        this.studentNumber = 10000;
        this.firstName = "NO_NAME";
        this.lastName = "NO_LAST_NAME";
        this.middleName = "NO_MIDDLE_NAME";
        this.sex = 'Н';
        this.dateOfBirth = new Date();
        this.groupId = 1;
    }

    // Конструктор принимающий ResultSet из бд
    public Student(ResultSet rs) throws SQLException {
        this.studentId = rs.getInt(1);
        this.studentNumber = rs.getInt(2);
        this.firstName = rs.getString(3);
        this.lastName = rs.getString(4);
        this.middleName = rs.getString(5);
        this.sex = rs.getString(6).charAt(0);
        this.dateOfBirth = rs.getDate(7);
        this.groupId = rs.getInt(8);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + ", "
                + "Пол = " + sex
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)
                + ", ИД группы = " + groupId;
    }

    @Override
    public int compareTo(Object obj) {
        //Collator collator = Collator.getInstance(new Locale("ru"));
        Collator collator = Collator.getInstance(new Locale("ru", "RU"));
        collator.setStrength(Collator.PRIMARY);
        //return this.toString().compareToIgnoreCase(obj.toString());
        return collator.compare(this.toString(), obj.toString());
    }
}
