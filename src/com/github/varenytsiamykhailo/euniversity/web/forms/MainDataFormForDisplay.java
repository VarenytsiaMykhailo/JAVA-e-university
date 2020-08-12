package com.github.varenytsiamykhailo.euniversity.web.forms;

import com.github.varenytsiamykhailo.euniversity.logic.Group;
import com.github.varenytsiamykhailo.euniversity.logic.Student;

import java.util.ArrayList;

public class MainDataFormForDisplay {

    private int selectedYear;

    private int selectedGroupId;

    private ArrayList<Group> allGroups;

    private ArrayList<Student> studentsForSelectedGroup;

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getSelectedGroupId() {
        return selectedGroupId;
    }

    public void setSelectedGroupId(int selectedGroupId) {
        this.selectedGroupId = selectedGroupId;
    }

    public ArrayList<Group> getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(ArrayList<Group> allGroups) {
        this.allGroups = allGroups;
    }

    public ArrayList<Student> getStudentsForSelectedGroup() {
        return studentsForSelectedGroup;
    }

    public void setStudentsForSelectedGroup(ArrayList<Student> studentsForSelectedGroup) {
        this.studentsForSelectedGroup = studentsForSelectedGroup;
    }
}

