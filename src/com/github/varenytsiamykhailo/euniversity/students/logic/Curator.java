package com.github.varenytsiamykhailo.euniversity.students.logic;

import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Curator implements Comparable {

    private int curatorId;

    private String firstName;

    private String lastName;

    private String patronymic;

    private Date dateOfBirth;

    //private Sex sex;

    private char sex;

    // private int groupId;

    /**
     * год преподавания
     */
    private int yearOfTeaching;


    public int getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(int curatorId) {
        this.curatorId = curatorId;
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

/*  Старая версия
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
*/

    public int getYearOfTeaching() {
        return yearOfTeaching;
    }

    public void setYearOfTeaching(int yearOfTeaching) {
        this.yearOfTeaching = yearOfTeaching;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + patronymic + ", "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)
                + " Год преподавания: " + yearOfTeaching;
    }

    @Override
    public int compareTo(Object obj) {
        Collator collator = Collator.getInstance(new Locale("ru"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.toString(), obj.toString());
    }
}
