package com.github.varenytsiamykhailo.euniversity.logic.DAO;

import com.github.varenytsiamykhailo.euniversity.logic.entities.*;
import com.github.varenytsiamykhailo.euniversity.logic.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class DAOtest {

    /**
     * Поиск user по переданному id. Если user с таким id нет - возвращается null
     */
    public User getUserById(Long userId) {
        User user;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            user = session.get(User.class, userId);
            if (user != null && user.getRole() != null)
                Hibernate.initialize(user.getRole());
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    /**
     * Поиск user по переданному логину и паролю. Если user с таким login и password не найден - возвращается null.
     */
    public User getUserByLoginPassword(final String login, final String password) {
        User user = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE login = :login AND password = :password");
            query.setParameter("login", login);
            query.setParameter("password", password);
            List result = query.list();
            if (result.iterator().hasNext()) {
                user = (User) result.iterator().next();
            }
            if (user != null && user.getRole() != null)
                Hibernate.initialize(user.getRole());
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    /**
     * Получает объект Role пользователя по его логину и паролю. Если роли нет или введен неверный логин или пароль - возвращается null.
     */
    public Role getUserRoleByLoginPassword(final String login, final String password) {
        Role role = null;
        User user = getUserByLoginPassword(login, password);
        if (user != null && user.getRole() != null) {
            role = user.getRole();
        }
        return role;
    }

    /**
     * Добавляет (регистрирует) user в базу данных. Если пользователь с таким же login уже существует, то возвращается null.
     * Если user добавился в базу данных - возвращается его сгенерированный id.
     */
    public Long addUser(User user) {
        Long id = null;
        if (!userIsExistByLogin(user.getLogin())) {
            Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();
                id = (Long) session.save(user);
                transaction.commit();
            } finally {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            }
        }
        return id;
    }

    /**
     * Возвращает true, если пользователь с переданным логином существует, иначе - false.
     */
    public boolean userIsExistByLogin(final String login) {
        boolean result = false;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT 1 FROM User WHERE login = :login");
            query.setParameter("login", login);
            result = query.uniqueResult() != null;
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Возвращает true, если пользователь с переданными логином и паролем существует, иначе - false.
     */
    public boolean userIsExistByLoginPassword(final String login, final String password) {
        boolean result = false;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT 1 FROM User WHERE login = :login AND password = :password");
            query.setParameter("login", login);
            query.setParameter("password", password);
            result = query.uniqueResult() != null;
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Возвращает объект Role из БД, соответствующий переданному методу roleString.
     * Возвращает null, если запись с переданным описанием роли не найдена.
     */
    public Role getRole(final String roleString) {
        Role role = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Role WHERE role = :role");
            query.setParameter("role", roleString);
            List result = query.list();
            if (result.iterator().hasNext()) {
                role = (Role) result.iterator().next();
            }
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return role;
    }

    public void updateUserEmail(final User user, final String newEmail) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            id = (Long) session.save(user);
            transaction.commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
