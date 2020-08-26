package com.github.varenytsiamykhailo.euniversity.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int userId;

    private String login;

    private String password;

    private String email;

    private Role role;

    public enum Role {
        ADMIN,
        USER,
        UNKNOWN
    }

    public User() {
    }

    public User(int id, String login, String password, String email, Role role) {
        this.userId = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Конструктор принимающий ResultSet из бд
    public User(ResultSet rs) throws SQLException {
        this.userId = rs.getInt(1);
        this.login = rs.getString(2);
        this.password = rs.getString(3);
        this.email = rs.getString(4);
        role = getRoleByRoleIdFromDB((Long) rs.getObject(5));
    }

    /**
     * Возвращает Role.ADMIN если id == 1, Role.USER если id == 2, в противном случае - Role.UNKNOWN.
     * Если получаемый параметр null, то возвращается null.
     */
    User.Role getRoleByRoleIdFromDB(Long id) {
        if (id == null) {
            return null;
        }
        if (id == 1) {
            return Role.ADMIN;
        } else if (id == 2) {
            return Role.USER;
        } else {
            return Role.UNKNOWN;
        }
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
