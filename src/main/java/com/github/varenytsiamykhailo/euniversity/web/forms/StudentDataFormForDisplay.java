package com.github.varenytsiamykhailo.euniversity.web.forms;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentDataFormForDisplay {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private int studentId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String dateOfBirth;

    private int sex;

    private int groupId;

    private int educationYear;

    private ArrayList<Group> allGroups;

    public void initFromStudent(Student student) {
        this.studentId = student.getStudentId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.middleName = student.getMiddleName();
        this.dateOfBirth = simpleDateFormat.format(student.getDateOfBirth());
        if (student.getSex() == 'М') {
            this.sex = 0; // Мужчина
        } else if (student.getSex() == 'Ж') {
            this.sex = 1; // Женщина
        } else {
            this.sex = 3; // Неопределенный пол
        }
        this.groupId = student.getGroupId();
        this.educationYear = student.getEducationYear();
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(ArrayList<Group> allGroups) {
        this.allGroups = allGroups;
    }
}
