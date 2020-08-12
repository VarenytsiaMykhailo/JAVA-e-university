package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import java.util.ArrayList;

public class DataStructureForDisplay {

    private int year;

    private int groupId;

    private ArrayList<Group> groups;

    private ArrayList<Student> students;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}

