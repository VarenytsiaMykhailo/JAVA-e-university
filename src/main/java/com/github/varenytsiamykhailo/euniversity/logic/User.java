package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int userId;

    private String login;

    private String password;

    private String email;

    private int roleId;

    private int personId;

    public User() {
    }

    public User(String login, String password, String email, int roleId, int personId) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.personId = personId;
    }

    // Конструктор принимающий ResultSet из бд
    public User(ResultSet rs) throws SQLException {
        this.userId = rs.getInt(1);
        this.login = rs.getString(2);
        this.password = rs.getString(3);
        this.email = rs.getString(4);
        this.roleId = rs.getInt(5);
        this.personId = rs.getInt(6);

        // role = getRoleByRoleIdFromDB((Long) rs.getObject(5));
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
