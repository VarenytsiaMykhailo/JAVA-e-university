package com.github.varenytsiamykhailo.euniversity.logic.DAO;

import com.github.varenytsiamykhailo.euniversity.logic.User;

import java.util.ArrayList;

public class UserDAO {

    public final ArrayList<User> usersStore = new ArrayList<>();

    /**
     * Если такой user не найден, то возвращает объект User с id = -1
     */
    public User getUserById(int id) {
        User resultUser = new User();
        resultUser.setId(-1);

        for (User user : usersStore) {
            if (user.getId() == id) {
                resultUser = user;
                break;
            }
        }
        return resultUser;
    }

    /**
     * Если такой user не найден, то возвращает объект User с id = -1
     */
    public User getUserByLoginPassword(final String login, final String password) {
        User resultUser =new User();
        resultUser.setId(-1);

        for (User user : usersStore) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                resultUser = user;
                break;
            }
        }
        return resultUser;
    }

    public boolean addUser(final User newUser) {
        for (User user : usersStore) {
            if (user.getLogin().equals(newUser.getLogin())) { // Если уже сущетсвует пользователь с таким же логином
                return false;
            }
        }
        return usersStore.add(newUser);
    }

    public User.Role getUserRoleByLoginPassword(final String login, final String password) {
        User.Role resultRole = User.Role.UNKNOWN;

        for (User user : usersStore) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                resultRole = user.getRole();
                break;
            }
        }
        return resultRole;
    }

    public boolean userIsExist(final String login, final String password) {
        boolean result = false;

        for (User user : usersStore) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }
}


