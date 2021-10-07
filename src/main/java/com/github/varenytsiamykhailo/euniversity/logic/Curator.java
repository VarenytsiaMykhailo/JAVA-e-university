package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Curator implements Comparable {

    private int personId;

    private int personContract;

    private String firstName;

    private String lastName;

    private String middleName;

    private char sex;

    private Date dateOfBirth;

    private boolean isActive;


    // Конструктор по умолчанию
    public Curator() {
        this.personContract = 0;
        this.firstName = "NO_NAME";
        this.lastName = "NO_LAST_NAME";
        this.middleName = "NO_MIDDLENAME";
        this.sex = 'Н';
        this.dateOfBirth = new Date();
        this.isActive = false;
    }

    // Конструктор принимающий ResultSet из бд
    public Curator(ResultSet rs) throws SQLException {
        this.personId = rs.getInt(1);
        this.personContract = rs.getInt(2);
        this.firstName = rs.getString(3);
        this.lastName = rs.getString(4);
        this.middleName = rs.getString(5);
        this.sex = rs.getString(6).charAt(0);
        this.dateOfBirth = rs.getDate(7);
        this.isActive = rs.getBoolean(8);
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPersonContract() {
        return personContract;
    }

    public void setPersonContract(int personContract) {
        this.personContract = personContract;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + ", " +
                "Номер договора: " + personContract + ", " +
                "Пол: " + sex + ", " +
                "Дата рождения: " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth) + ", " +
                "Готов курировать новую группу: " + (isActive ? "да" : "нет");
    }

    @Override
    public int compareTo(Object obj) {
        Collator collator = Collator.getInstance(new Locale("ru"));
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.toString(), obj.toString());
    }
}
