package com.github.varenytsiamykhailo.euniversity.web;

import com.github.varenytsiamykhailo.euniversity.logic.DAO.DAOtest;
import com.github.varenytsiamykhailo.euniversity.logic.entities.Role;
import com.github.varenytsiamykhailo.euniversity.logic.entities.User;

public class Main {
    public static void main(String[] args) {
        DAOtest dao = new DAOtest();

        User user = dao.getUserById(11L);
        System.out.println("user = " + user);
        Role role = dao.getUserRoleByLoginPassword("Asddd", "zxc");
        System.out.println("role = " + role);


        //System.out.println("id = " + dao.userIsExistByLoginPassword("asddd", "zxcz"));
    }
}
