package com.github.varenytsiamykhailo.euniversity.students.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Student implements Comparable {

    private int studentId;

    private String firstName;

    private String lastName;

    private String patronymic;

    private Date dateOfBirth;

    //private Sex sex;

    private char sex;

    private int groupId;

    /**
     * год преподавания
     */
    private int educationYear;

    public Student(ResultSet rs) throws SQLException {
        this.studentId = rs.getInt(1);
        this.firstName = rs.getString(2);
        this.lastName = rs.getString(3);
        this.patronymic = rs.getString(4);
        this.dateOfBirth = rs.getDate(5);
        this.sex = rs.getString(6).charAt(0);
        this.groupId = rs.getInt(7);
        this.educationYear = rs.getInt(8);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + patronymic + ", "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)
                + ", ИД группы = " + groupId + ", Год обучения: " + educationYear;
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
