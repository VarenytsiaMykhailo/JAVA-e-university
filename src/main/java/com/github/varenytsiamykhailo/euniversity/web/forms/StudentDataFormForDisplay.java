package com.github.varenytsiamykhailo.euniversity.web.forms;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentDataFormForDisplay {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private int studentId;

    private int studentNumber;

    private String firstName;

    private String lastName;

    private String middleName;

    private int sex;

    private String dateOfBirth;

    private int groupId;

    private ArrayList<Group> allGroups;

    public void initFromStudent(Student student) {
        this.studentId = student.getStudentId();
        this.studentNumber = student.getStudentNumber();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.middleName = student.getMiddleName();
        if (student.getSex() == 'М') {
            this.sex = 0; // Мужчина
        } else if (student.getSex() == 'Ж') {
            this.sex = 1; // Женщина
        } else {
            this.sex = 3; // Неопределенный пол
        }
        this.dateOfBirth = simpleDateFormat.format(student.getDateOfBirth());
        this.groupId = student.getGroupId();
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(ArrayList<Group> allGroups) {
        this.allGroups = allGroups;
    }
}
