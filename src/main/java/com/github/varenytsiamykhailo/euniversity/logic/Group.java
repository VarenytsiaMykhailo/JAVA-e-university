package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Group {

    private int groupId;

    private String groupName;

    private String speciality;

    private int educationYear;

    private int numberOfSemester;

    private int curatorId;

    public Group() {
        this.groupId = 0;
        this.groupName = "NO_GROUP_NAME";
        this.speciality = "NO_SPECIALITY";
        this.educationYear = 2000;
        this.numberOfSemester = 1;
        this.curatorId = 1;
    }

    public Group(ResultSet rs) throws SQLException {
        this.groupId = rs.getInt(1);
        this.groupName = rs.getString(2);
        this.speciality = rs.getString(3);
        this.educationYear = rs.getInt(4);
        this.numberOfSemester = rs.getInt(5);
        this.curatorId = rs.getInt(6);
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }

    public void setNumberOfSemester(int numberOfSemester) {
        this.numberOfSemester = numberOfSemester;
    }

    public int getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(int curatorId) {
        this.curatorId = curatorId;
    }

    @Override
    public String toString() {
        return groupName + ", год обучения: " + educationYear + ", номер семестра: " + numberOfSemester + ", Специальность: " + speciality;
    }
}
