package com.github.varenytsiamykhailo.euniversity.logic.DAO;

import com.github.varenytsiamykhailo.euniversity.logic.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Класс предоставляет методы с запросами к базе данных
 */
public abstract class ManagementSystemDAO {

    public Connection connection;

    /**
     * Получить список студентов для опеределенной группы, определенного года обучения.
     */
    public ArrayList<Student> getStudentsFromGroup(Group group, int year) throws SQLException {
        ArrayList<Student> students = new ArrayList<Student>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT student_id, first_name, last_name, middle_name, date_of_birth, sex, all_students.group_id, education_year " +
                    "FROM all_students " +
                    "JOIN all_groups " +
                    "ON all_students.group_id = all_groups.group_id "+
                    "WHERE all_students.group_id = ? AND all_groups.education_year = ? " +
                    "ORDER BY last_name, first_name, middle_name"
            );
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs);

                students.add(student);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return students;
    }

    /**
     * Перевести студентов из одной группы с одним годом обучения в другую группу с другим годом обучения
     */
    public void moveStudentsFromGroupToNewGroup(Group oldGroup, int oldYear, Group newGroup, int newYear) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE all_students " +
                    "SET group_id = ?, education_year = ? " +
                    "WHERE group_id = ? AND education_year = ?"
            );
            stmt.setInt(1, newGroup.getGroupId());
            stmt.setInt(2, newYear);
            stmt.setInt(3, oldGroup.getGroupId());
            stmt.setInt(4, oldYear);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Удаляет всех студентов из определенной группы, определенного года обучения
     */
    public void removeStudentsFromGroup(Group group, int year) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE all_students " +
                    "SET group_id = 1 " +
                    "WHERE group_id = ? AND education_year = ?"
            );
            stmt.setInt(1, group.getGroupId());
            stmt.setInt(2, year);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Добавить студента
     */
    public void insertStudent(Student student) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO all_students " +
                    "(first_name, last_name, middle_name, date_of_birth, sex, group_id, education_year) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getMiddleName());
            stmt.setDate(4, new Date(student.getDateOfBirth().getTime()));
            stmt.setString(5, Character.toString(student.getSex()));
            stmt.setInt(6, student.getGroupId());
            stmt.setInt(7, student.getEducationYear());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Обновить данные о студенте
     */
    public void updateStudent(Student newStudentData) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE all_students " +
                    "SET first_name = ?, last_name = ?, middle_name = ?, date_of_birth = ?, sex = ?, group_id = ?, education_year= ? " +
                    "WHERE student_id = ?"
            );
            stmt.setString(1, newStudentData.getFirstName());
            stmt.setString(2, newStudentData.getLastName());
            stmt.setString(3, newStudentData.getMiddleName());
            stmt.setDate(4, new Date(newStudentData.getDateOfBirth().getTime()));
            stmt.setString(5, Character.toString(newStudentData.getSex()));
            stmt.setInt(6, newStudentData.getGroupId());
            stmt.setInt(7, newStudentData.getEducationYear());
            stmt.setInt(8, newStudentData.getStudentId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Удаляет студента. Ищет нужного студента по его ИД и удаляет его
     */
    public void deleteStudent(Student delStudentData) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM all_students " +
                    "WHERE student_id = ?"
            );
            stmt.setInt(1, delStudentData.getStudentId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Поиск студента по переданному id. Если студента с таким id нет - возвращается null
     */
    public Student getStudentById(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT student_id, first_name, last_name, middle_name, date_of_birth, sex, group_id, education_year " +
                    "FROM all_students " +
                    "WHERE student_id = ? " +
                    "ORDER BY last_name, first_name, middle_name"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Поиск куратора по переданному id из department_staff_curators_view. Если куратора с таким id нет - возвращается null
     */

    public Curator getCuratorById(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, is_active " +
                    "FROM department_staff_curators_view " +
                    "WHERE person_id = ? " +
                    "ORDER BY last_name, first_name, middle_name"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Curator(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает коллекцию всех групп из базы данных в виде ArrayList<Group>
     */
    public ArrayList<Group> getAllGroups() throws SQLException {
        ArrayList<Group> allGroups = new ArrayList<Group>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT group_id, group_name, curator_id, speciality FROM all_groups");
            while (rs.next()) {
                Group group = new Group();
                group.setGroupId(rs.getInt(1));
                group.setGroupName(rs.getString(2));
                group.setCuratorId(rs.getInt(3));
                group.setSpeciality(rs.getString(4));

                allGroups.add(group);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }

        return allGroups;
    }

    /**
     * Возвращает коллекцию всех студентов из базы данных в виде ArrayList<Student>
     */
    public ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> allStudents = new ArrayList<Student>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT student_id, first_name, last_name, middle_name, date_of_birth, sex, group_id, education_year " +
                    "FROM all_students " +
                    "ORDER BY last_name, first_name, middle_name"
            );
            while (rs.next()) {
                Student student = new Student(rs); // Класс Student сам обрабатывает переданный ему ResultSet

                allStudents.add(student);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }

        return allStudents;
    }

    /**
     * Поиск user по переданному id. Если user с таким id нет - возвращается null
     */
    public User getUserById(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT user_id, login, password, email, role_id, person_id " +
                    "FROM users " +
                    "WHERE user_id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает представление роли, выраженное типом Role, в зависимости от его соответствующего числового представления id.
     * Если id == 1, то возвращается Role.ADMIN.
     * Если id == 2, то возвращается Role.USER.
     * Во всех остальных случаях возвращается Role.UNKNOWN, даже если получаемый параметр равен null
     * Role.UNKNOWN - соответствует id = 3 (числу 3).
     * Противоположный ему метод - getRoleIdByUserRole.
     * !!! Обязательно отредактировать эти методы, при внесении изменений в таблицу roles в базе данных
     * (в случае изменения порядка расположения кортежей или в случае добавления новых ролей).
     */
    public Role getUserRoleByRoleIdFromDB(Long id) {
        if (id == null) {
            return Role.UNKNOWN;
        }
        if (id == 1) {
            return Role.ADMIN;
        } else if (id == 2) {
            return Role.USER;
        } else {
            return Role.UNKNOWN;
        }
    }

    /**
     * Возвращает числовое представление передаваемому типу Role.
     * Если role == Role.ADMIN, то возвращается число 1.
     * Если role == Role.USER, то возвращается число 2.
     * Во всех остальных случаях возвращается число 3, даже если получаемый параметр равен null.
     * Число 3 - соответствует типу Role.UNKNOWN
     * Противоположный ему метод - getUserRoleByRoleIdFromDB.
     * !!! Обязательно отредактировать эти методы, при внесении изменений в таблицу roles в базе данных
     * (в случае изменения порядка расположения кортежей или в случае добавления новых ролей).
     */
    public int getRoleIdByUserRole(Role role) {
        if (role == null) {
            return 3;
        }
        if (role == Role.ADMIN) {
            return 1;
        } else if (role == Role.USER) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Поиск user по переданному логину и паролю. Если user с таким login и password не найден - возвращается null.
     */
    public User getUserByLoginPassword(final String login, final String password) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT user_id, login, password, email, role_id, person_id " +
                    "FROM users " +
                    "WHERE login = ? AND password = ?"
            );
            stmt.setString(1, login);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Добавляет (регистрирует) user в базу данных. Если пользователь с таким же login уже существует, то бросается исключение SQLException
     */
    public void addUser(User user) throws SQLException {
        if (!userIsExistByLogin(user.getLogin())) {
            PreparedStatement stmt = null;
            try {
                stmt = connection.prepareStatement("INSERT INTO users " +
                        "(login, password, email, role_id, person_id) " +
                        "VALUES (?, ?, ?, ?, ?)"
                );
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setInt(4, user.getRoleId());
                if (user.getPersonId() != 0) {
                    stmt.setInt(5, user.getPersonId());
                } else {
                    stmt.setNull(5, 0);
                }
                stmt.executeUpdate();
            } finally {
                if (stmt != null)
                    stmt.close();
            }
        } else {
            throw new SQLException("User with this login is exist!");
        }
    }

    /**
     * Получает роль пользователя по его логину и паролю. Если роли нет или введен неверный логин или пароль - возвращается null.
     */
    public Role getUserRoleByLoginPassword(final String login, final String password) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT role FROM users " +
                    "JOIN roles ON users.role_id = roles.role_id " +
                    "WHERE users.login = ? AND users.password = ?"
            );
            stmt.setString(1, login);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals("ADMIN")) {
                    return Role.ADMIN;
                } else if (rs.getString(1).equals("USER")) {
                    return Role.USER;
                } else {
                    return null; // Переписать, если добавится новая роль в БД.
                }
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает true, если пользователь с переданными логином и паролем существует, иначе - false.
     */
    public boolean userIsExistByLoginPassword(final String login, final String password) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT EXISTS(" +
                    "SELECT user_id FROM users " +
                    "WHERE login = ? && password = ?)"
            );
            stmt.setString(1, login);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int result = rs.getInt(1);
                if (result == 1)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает true, если пользователь с переданным логином существует, иначе - false.
     */
    public boolean userIsExistByLogin(final String login) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT EXISTS(" +
                    "SELECT user_id FROM users " +
                    "WHERE login = ?)"
            );
            stmt.setString(1, login);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int result = rs.getInt(1);
                if (result == 1)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Устанавливает новое значение email у user с переданным login
     */
    public void updateUserEmail(final String login, final String newEmail) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE users " +
                    "SET email = ? " +
                    "WHERE login = ?"
            );
            stmt.setString(1, newEmail);
            stmt.setString(2, login);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Устанавливает новое значение password у user с переданным login и currentPassword
     */
    public void updateUserPassword(final String login, final String currentPassword, final String newPassword) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE users " +
                    "SET password = ? " +
                    "WHERE login = ? AND password = ?"
            );
            stmt.setString(1, newPassword);
            stmt.setString(2, login);
            stmt.setString(3, currentPassword);
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Получить список людей из department_staff
     */
    public List<DepartmentPerson> getDepartmentStaff() throws SQLException {
        List<DepartmentPerson> departmentStaff = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth " +
                    "FROM department_staff " +
                    "ORDER BY last_name, first_name, middle_name"
            );

            while (rs.next()) {
                DepartmentPerson departmentPerson = new DepartmentPerson(rs);

                departmentStaff.add(departmentPerson);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return departmentStaff;
    }

    /**
     * Получить список людей из department_staff_curators_view
     */
    public List<Curator> getCurators() throws SQLException {
        List<Curator> curators = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, is_active " +
                    "FROM department_staff_curators_view " +
                    "ORDER BY last_name, first_name, middle_name"
            );

            while (rs.next()) {
                Curator curator = new Curator(rs);

                curators.add(curator);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return curators;
    }

    /**
     * Получить список людей из Department_staff_scientists_view
     */
    public List<Scientist> getScientists() throws SQLException {
        List<Scientist> scientists = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, research_directions " +
                    "FROM Department_staff_scientists_view " +
                    "ORDER BY last_name, first_name, middle_name"
            );

            while (rs.next()) {
                Scientist scientist = new Scientist(rs);

                scientists.add(scientist);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
        return scientists;
    }

    /**
     * Поиск DepartmentPerson из department_staff по переданному personId. Если DepartmentPerson с таким personId нет - возвращается null
     */
    public DepartmentPerson getDepartmentPersonById(int personId) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth " +
                    "FROM department_staff " +
                    "WHERE person_id = ?"
            );
            stmt.setInt(1, personId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new DepartmentPerson(rs);
            } else {
                return null;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Добавить сотрудника
     */
    public void insertDepartmentPerson(DepartmentPerson departmentPerson) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("INSERT INTO department_staff " +
                    "(person_contract, first_name, last_name, middle_name, sex, date_of_birth) " +
                    "VALUES (?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, departmentPerson.getPersonContract());
            stmt.setString(2, departmentPerson.getFirstName());
            stmt.setString(3, departmentPerson.getLastName());
            stmt.setString(4, departmentPerson.getMiddleName());
            stmt.setNString(5, Character.toString(departmentPerson.getSex()));
            stmt.setDate(6, new Date(departmentPerson.getDateOfBirth().getTime()));

            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Обновить данные о сотруднике
     */
    public void updateDepartmentPerson(DepartmentPerson newDepartmentPersonData) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("UPDATE department_staff " +
                    "SET person_contract = ?, first_name = ?, last_name = ?, middle_name = ?, sex = ?, date_of_birth = ? " +
                    "WHERE person_id = ?"
            );
            stmt.setInt(1, newDepartmentPersonData.getPersonContract());
            stmt.setString(2, newDepartmentPersonData.getFirstName());
            stmt.setString(3, newDepartmentPersonData.getLastName());
            stmt.setString(4, newDepartmentPersonData.getMiddleName());
            stmt.setString(5, Character.toString(newDepartmentPersonData.getSex()));
            stmt.setDate(6, new Date(newDepartmentPersonData.getDateOfBirth().getTime()));
            stmt.setInt(7, newDepartmentPersonData.getPersonId());
            stmt.executeUpdate();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает true, если переданный personContract существует в таблице department_staff, иначе - false.
     */
    public boolean isExistPersonContractInDepartmentStaff(final int personContract) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT EXISTS(" +
                    "SELECT person_contract FROM department_staff " +
                    "WHERE person_contract = ?)"
            );
            stmt.setInt(1, personContract);

            rs = stmt.executeQuery();
            if (rs.next()) {
                int result = rs.getInt(1);
                if (result == 1)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Возвращает true, если переданный personContract может использоваться для обновления у сотрудника в таблице department_staff, иначе - false.
     */
    public boolean checkPersonContractInDepartmentStaffForUpdate(final int personId, final int personContract) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT person_id FROM department_staff " +
                    "WHERE person_contract = ?"
            );
            stmt.setInt(1, personContract);

            rs = stmt.executeQuery();
            if (rs.next()) {
                int resultId = rs.getInt(1);
                if (resultId == personId)
                    return true;
                else
                    return false;
            } else {
                return true;
            }
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }
}
