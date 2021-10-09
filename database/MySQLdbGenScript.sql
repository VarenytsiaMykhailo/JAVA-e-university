DROP DATABASE IF EXISTS e_university;

CREATE DATABASE e_university;

USE e_university;

CREATE TABLE roles
(
    role_id INT AUTO_INCREMENT,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
) engine = InnoDB;


CREATE TABLE department_staff
(
    person_id INT AUTO_INCREMENT,
    person_contract INT UNIQUE NOT NULL CHECK(person_contract >= 10000),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    sex CHAR(1) NOT NULL CHECK(sex IN ('М', 'Ж', 'Н')),
    date_of_birth DATE NOT NULL,
    PRIMARY KEY (person_id)
) engine = InnoDB;


CREATE TABLE users
(
    user_id INT AUTO_INCREMENT,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role_id INT,
    person_id INT,
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;


CREATE TABLE scientists
(
    person_id INT AUTO_INCREMENT,
    research_directions VARCHAR(1000) NOT NULL,
    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE
) engine = InnoDB;


-- Представление для ученых Department_staff_scientists_view
CREATE VIEW Department_staff_scientists_view (person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, research_directions) AS
SELECT DS.person_id,
       DS.person_contract,
       DS.first_name,
       DS.last_name,
       DS.middle_name,
       DS.sex,
       DS.date_of_birth,
       S.research_directions
FROM department_staff AS DS JOIN scientists AS S ON DS.person_id = S.person_id;


CREATE TABLE curators
(
    person_id INT AUTO_INCREMENT,
    is_active BOOLEAN NOT NULL, -- false - не работает куратором в настоящее время, давать ему новые группы нельзя, true - куратору можно выдавать новые группы для курирования.
    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE
) engine = InnoDB;


-- Представление для кураторов department_staff_curators_view
CREATE VIEW department_staff_curators_view (person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, is_active) AS
SELECT DS.person_id,
       DS.person_contract,
       DS.first_name,
       DS.last_name,
       DS.middle_name,
       DS.sex,
       DS.date_of_birth,
       C.is_active
FROM department_staff AS DS JOIN curators AS C ON DS.person_id = C.person_id;


CREATE TABLE all_groups
(
    group_id INT NOT NULL AUTO_INCREMENT,
    group_name VARCHAR(50) NOT NULL,
    speciality VARCHAR(255) NOT NULL,
    education_year INT NOT NULL CHECK(education_year >= 2000),	-- Пример: 2020
    number_of_semester INT NOT NULL CHECK(number_of_semester IN (1, 2)),
    curator_id INT NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (curator_id) REFERENCES curators (person_id) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT UQ_all_groups_group_name_education_year UNIQUE (group_name, education_year)
) engine = InnoDB;


CREATE TABLE all_students
(
    student_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    sex CHAR(1) NOT NULL CHECK(sex IN ('М', 'Ж', 'Н')),
    date_of_birth DATE NOT NULL,
    group_id INT NOT NULL,
    education_year INT NOT NULL,
    PRIMARY KEY (student_id)
) engine = InnoDB;



INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('USER'),
       ('UNKNOWN');

INSERT INTO users (login, password, email, role_id)
VALUES ('AdminAdmin', 'AdminAdmin', 'example@example.ru', 1);

INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
VALUES (10000, 'Светлана', 'Светкина', 'Светлановна', 'М', '1900-04-20'),
       (10001, 'Наталья', 'Натальева', 'Натальевна', 'М', '1900-04-20'),
       (10002, 'Ирина', 'Иринина', 'Ириновна', 'Ж', '1900-04-20'),
       (10003, 'Мишка', 'Мишкин', 'Мишкинин', 'Ж', '1900-04-20');

INSERT INTO curators
VALUES (1, true),
       (2, false);

INSERT INTO scientists
VALUES (3, "физика"),
       (4, "паттерны проектирования");




INSERT INTO all_groups (group_name, speciality, education_year, number_of_semester, curator_id)
VALUES ('Нулевая группа', 'Неопределенные студенты', 2019, 1, 1),
       ('Первая группа', 'Программная инженерия', 2019, 1, 2),
       ('Вторая группа', 'Информационная безопасность', 2020, 2, 2);


INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Иван', 'Сергеевич', 'Степанов', 'М', '1999-03-20', 2, 2019);

INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Наталья', 'Андреевна', 'Чичикова', 'Ж', '2000-06-10', 1, 2019);

INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Виктор', 'Сидорович', 'Белов', 'М', '1999-01-10', 2, 2020);

INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Петр', 'Викторович', 'Сушкин', 'М', '2000-03-12', 3, 2020);

INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Вероника', 'Сергеевна', 'Ковалева', 'Ж', '1999-07-19', 3, 2020);

INSERT INTO all_students (first_name, middle_name, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Ирина', 'Федоровна', 'Истомина', 'Ж', '2002-04-29', 3, 2020);


