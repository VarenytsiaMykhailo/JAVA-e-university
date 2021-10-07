CREATE TABLE roles
(
    role_id SERIAL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_id)
);


CREATE TABLE department_staff
(
    person_id SERIAL,
    person_contract INT UNIQUE NOT NULL CHECK(person_contract >= 10000),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    sex CHAR(1) NOT NULL CHECK(sex IN ('М', 'Ж', 'Н')),
    date_of_birth DATE NOT NULL,
    PRIMARY KEY (person_id)
);


CREATE TABLE users
(
    user_id SERIAL,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role_id INT,
    person_id INT,
    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE scientists
(
    person_id SERIAL,
    research_directions VARCHAR(1000) NOT NULL,
    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE
)


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
    person_id SERIAL,
    is_active BOOLEAN NOT NULL, -- false - не работает куратором в настоящее время, давать ему новые группы нельзя, true - куратору можно выдавать новые группы для курирования.
    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE
)


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



INSERT INTO roles (role)
VALUES ('ADMIN'),
       ('USER'),
       ('UNKNOWN');

INSERT INTO users (login, password, email, role_id)
VALUES ('AdminAdmin', 'AdminAdmin', 'example@example.ru', 1);

INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
VALUES (10000, 'Светлана', 'Светкина', 'Светлановна', 'М', '1900-04-20'),
       (10001, 'Наталья', 'Натальева', 'Натальевна', 'М', '1900-04-20'),
       (10002, 'Ирина', 'Иринина', 'Ириновна', 'Ж', '1900-04-20');

INSERT INTO curators
VALUES (1, true),
       (2, false);