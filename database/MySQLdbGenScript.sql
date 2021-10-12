DROP DATABASE IF EXISTS e_university;

CREATE DATABASE e_university;

USE e_university;

-- Типы учетных записей
CREATE TABLE roles
(
    role_id INT AUTO_INCREMENT,
    role    VARCHAR(50) NOT NULL,

    PRIMARY KEY (role_id)
) engine = InnoDB;


-- Штат сотрудников
CREATE TABLE department_staff
(
    person_id       INT AUTO_INCREMENT,
    person_contract INT UNIQUE  NOT NULL CHECK (person_contract >= 10000),
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    middle_name     VARCHAR(50) NOT NULL,
    sex             CHAR(1)     NOT NULL CHECK (sex IN ('М', 'Ж', 'Н')),
    date_of_birth   DATE        NOT NULL,

    PRIMARY KEY (person_id)
) engine = InnoDB;

-- CREATE INDEX IX_department_staff_person_contract ON department_staff(person_contract); -- Не требуется. Такой индекс для UNIQUE полей MySQL создает автоматически


-- Заметки для сотрудников
CREATE TABLE department_staff_notes
(
    note_id         INT AUTO_INCREMENT,
    person_id       INT           NOT NULL,
    date_time_added DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

-- CREATE INDEX IX_department_staff_notes_person_id ON department_staff_notes(person_id); -- Не требуется. MySQL создает индексы для внешних ключей автоматически


-- Аккаунты пользователей
CREATE TABLE users
(
    user_id   INT AUTO_INCREMENT,
    login     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    email     VARCHAR(255)        NOT NULL,
    role_id   INT                 NOT NULL,
    person_id INT                 NOT NULL,

    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

CREATE INDEX IX_users_password ON users (password);
-- Для login не требуется. Такой индекс для UNIQUE полей MySQL создает автоматически


-- Ученые (подтип department_staff)
CREATE TABLE scientists
(
    person_id           INT AUTO_INCREMENT,
    research_directions VARCHAR(1000) NOT NULL,

    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;


-- Представление для ученых Department_staff_scientists_view
CREATE VIEW department_staff_scientists_view
            (person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, research_directions) AS
SELECT DS.person_id,
       DS.person_contract,
       DS.first_name,
       DS.last_name,
       DS.middle_name,
       DS.sex,
       DS.date_of_birth,
       S.research_directions
FROM department_staff AS DS
         JOIN scientists AS S ON DS.person_id = S.person_id;


-- Кураторы (подтип depatment_staff)
CREATE TABLE curators
(
    person_id INT AUTO_INCREMENT,
    is_active BOOLEAN NOT NULL,                                                                           -- false - не работает куратором в настоящее время, давать ему новые группы нельзя, true - куратору можно выдавать новые группы для курирования

    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE -- На ON DELETE NO ACTION чтобы не терять информацию о кураторах для групп
) engine = InnoDB;


-- Представление для кураторов department_staff_curators_view
CREATE VIEW department_staff_curators_view
            (person_id, person_contract, first_name, last_name, middle_name, sex, date_of_birth, is_active) AS
SELECT DS.person_id,
       DS.person_contract,
       DS.first_name,
       DS.last_name,
       DS.middle_name,
       DS.sex,
       DS.date_of_birth,
       C.is_active
FROM department_staff AS DS
         JOIN curators AS C ON DS.person_id = C.person_id;


-- Группы
CREATE TABLE all_groups
(
    group_id           INT AUTO_INCREMENT,
    group_name         VARCHAR(50)  NOT NULL,
    speciality         VARCHAR(255) NOT NULL,
    education_year     INT          NOT NULL CHECK (education_year >= 2000), -- Пример: 2020
    number_of_semester INT          NOT NULL CHECK (number_of_semester IN (1, 2)),
    curator_id         INT          NOT NULL,

    PRIMARY KEY (group_id),
    FOREIGN KEY (curator_id) REFERENCES curators (person_id) ON DELETE NO ACTION ON UPDATE CASCADE,

    CONSTRAINT UQ_all_groups_group_name_education_year UNIQUE (group_name, education_year)
) engine = InnoDB;

CREATE INDEX IX_all_groups_group_name ON all_groups (group_name);


-- Заметки для групп
CREATE TABLE all_groups_notes
(
    note_id         INT AUTO_INCREMENT,
    group_id        INT           NOT NULL,
    date_time_added DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

-- CREATE INDEX IX_all_groups_notes_group_id ON all_groups_notes(group_id); -- Не требуется. MySQL создает индексы для внешних ключей автоматически


-- Студенты
CREATE TABLE all_students
(
    student_id     INT AUTO_INCREMENT,
    student_number INT         NOT NULL UNIQUE CHECK (student_number >= 10000), -- Номер зачетной книжки или другого документа, позволяющего уникально идентифицировать студента. Пример: 10001
    first_name     VARCHAR(50) NOT NULL,
    last_name      VARCHAR(50) NOT NULL,
    middle_name    VARCHAR(50) NOT NULL,
    sex            CHAR(1)     NOT NULL CHECK (sex IN ('М', 'Ж', 'Н')),
    date_of_birth  DATE        NOT NULL,
    group_id       INT         NOT NULL,

    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE NO ACTION ON UPDATE CASCADE,
    PRIMARY KEY (student_id)
) engine = InnoDB;

CREATE INDEX IX_all_students_last_name_first_name ON all_students (last_name, first_name);
-- CREATE INDEX IX_all_students_student_number ON all_students(student_number); -- Не требуется. Такой индекс для UNIQUE полей MySQL создает автоматически


-- Заметки для студентов
CREATE TABLE all_students_notes
(
    note_id         INT AUTO_INCREMENT,
    student_id      INT           NOT NULL,
    date_time_added DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (student_id) REFERENCES all_students (student_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

-- CREATE INDEX IX_all_students_notes_student_id ON all_students_notes(student_id); -- Не требуется. MySQL создает индексы для внешних ключей автоматически


-- -------------------- Объявление пользовательских функций, процедур (вставка встроенных строк в таблицы)


-- Пользовательская функция для получения встроенного в систему person_contract
DELIMITER $$
CREATE FUNCTION USF_get_default_person_contract() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE default_person_contract INT;
    SET default_person_contract = 10000;
    RETURN default_person_contract;
END $$
DELIMITER ;


-- Пользовательская функция для получения встроенного в систему group_name
DELIMITER $$
CREATE FUNCTION USF_get_default_group_name() RETURNS VARCHAR(50) DETERMINISTIC
BEGIN
    DECLARE default_group_name VARCHAR(50);
    SET default_group_name = 'Неопределенные студенты';
    RETURN default_group_name;
END $$
DELIMITER ;


-- Пользовательская функция для получения встроенного в систему education_year
DELIMITER $$
CREATE FUNCTION USF_get_default_education_year() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE default_education_year INT;
    SET default_education_year = 2000;
    RETURN default_education_year;
END $$
DELIMITER ;


-- Пользовательская функция для получения person_id по встроенному в систему person_contract
DELIMITER $$
CREATE FUNCTION USF_get_person_id_by_person_contract(input_person_contract INT) RETURNS INT
    READS SQL DATA
BEGIN
    DECLARE output_person_id INT;
    SELECT person_id INTO output_person_id FROM department_staff WHERE person_contract = input_person_contract;
    RETURN output_person_id;
END $$
DELIMITER ;


-- Пользовательская процедура для вставки встроенных строк в таблицы
DELIMITER $$
CREATE PROCEDURE USP_insert_default_values()
BEGIN
    INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
    VALUES (USF_get_default_person_contract(), 'Университет', 'Университет', 'Университет', 'Н', CURRENT_DATE());

    INSERT INTO roles (role)
    VALUES ('ADMIN'),
           ('USER'),
           ('UNKNOWN');

    INSERT INTO users (login, password, email, role_id, person_id)
    VALUES ('AdminAdmin', 'AdminAdmin', 'example@example.ru', 1,
            USF_get_person_id_by_person_contract(USF_get_default_person_contract()));

    INSERT INTO curators (person_id, is_active)
    VALUES ((SELECT person_id FROM department_staff WHERE person_contract = USF_get_default_person_contract()), true);

    INSERT INTO all_groups (group_name, speciality, education_year, number_of_semester, curator_id)
    VALUES (USF_get_default_group_name(), USF_get_default_group_name(), USF_get_default_education_year(), 1,
            USF_get_person_id_by_person_contract(USF_get_default_person_contract()));
END $$
DELIMITER ;

CALL USP_insert_default_values();


-- Пользовательская функция для получения встроенного в систему curator_id
DELIMITER $$
CREATE FUNCTION USF_get_default_curator_id() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE curator_id INT;
    SET curator_id = USF_get_person_id_by_person_contract(USF_get_default_person_contract());
    RETURN curator_id;
END $$
DELIMITER ;


-- Пользовательская функция для получения встроенного в систему group_id
DELIMITER $$
CREATE FUNCTION USF_get_default_group_id() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE output_group_id INT;
    SELECT group_id
    INTO output_group_id
    FROM all_groups
    WHERE group_name LIKE USF_get_default_group_name()
      AND education_year = USF_get_default_education_year();
    RETURN output_group_id;
END $$
DELIMITER ;

-- -------------------- Окончание вставки стандартных значений и объявлений пользовательских функций и процедур


-- История о состоянии студента в конкретной группе
CREATE TABLE student_group_history
(
    history_id         INT AUTO_INCREMENT,
    student_id         INT  NOT NULL,
    group_id           INT  NOT NULL DEFAULT 1, -- костыль. Здесь нужно вызвать функцию USF_get_default_group_id(), но MySQL не поддерживает вызов функций для ограничений DEFAULT. Возможно вставлять только статические (константные) значения
    date_of_enrollment DATE NOT NULL DEFAULT (DATE(CURRENT_TIMESTAMP)),
    date_of_expulsion  DATE,

    PRIMARY KEY (history_id),
    FOREIGN KEY (student_id) REFERENCES all_students (student_id) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE NO ACTION ON UPDATE CASCADE,

    CONSTRAINT UNIQUE (student_id, group_id, date_of_enrollment, date_of_expulsion),
    CONSTRAINT CHECK (date_of_enrollment <= date_of_expulsion)
) engine = InnoDB;


-- -------------------- Объявление триггеров


-- Триггер запрещающий удаление записей из student_group_history
DELIMITER $$
CREATE TRIGGER student_group_history_DELETE
    BEFORE DELETE
    ON student_group_history
    FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(128);
    SET error_message = 'You can not delete rows in student_group_history table.';
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
END $$
DELIMITER ;


-- Триггер запрещающий обновление полей student_id или group_id в student_group_history
DELIMITER $$
CREATE TRIGGER student_group_history_UPDATE
    BEFORE UPDATE
    ON student_group_history
    FOR EACH ROW
BEGIN
    IF (NEW.student_id != OLD.student_id OR NEW.group_id != OLD.group_id) THEN
        BEGIN
            DECLARE error_message VARCHAR(128);
            SET error_message = 'You can not update student_id or group_id columns in student_group_history table.';
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
        END;
    END IF;
END $$
DELIMITER ;


-- Триггер создающий запись в student_group_history, при добавлении студента в систему и его зачислении в группу
DELIMITER $$
CREATE TRIGGER all_students_INSERT
    AFTER INSERT
    ON all_students
    FOR EACH ROW
BEGIN
    INSERT INTO student_group_history (student_id, group_id, date_of_enrollment, date_of_expulsion)
    VALUES (NEW.student_id, NEW.group_id, DATE(CURRENT_TIMESTAMP), NULL);
END $$
DELIMITER ;


-- Триггер создающий запись в student_group_history, при переводе студента в другую группу
-- Триггер отчисляющий студента из предыдущей группы (если существует запись в student_group_history), заполняя поле date_of_expulsion, при его добавлении в другую группу
DELIMITER $$
CREATE TRIGGER all_students_UPDATE
    AFTER UPDATE
    ON all_students
    FOR EACH ROW
BEGIN
    IF (NEW.group_id != OLD.group_id) THEN
        BEGIN
            UPDATE student_group_history
            SET date_of_expulsion = DATE(CURRENT_TIMESTAMP)
            WHERE (student_id = NEW.student_id)
              AND (group_id = OLD.group_id)
              AND date_of_expulsion IS NULL;

            INSERT INTO student_group_history (student_id, group_id, date_of_enrollment, date_of_expulsion)
            VALUES (NEW.student_id, NEW.group_id, DATE(CURRENT_TIMESTAMP), NULL);
        END;
    END IF;
END $$
DELIMITER ;


-- Триггер запрещающий добавить новую группу, если для нее указан куратор с is_active = 0
DELIMITER $$
CREATE TRIGGER all_groups_INSERT
    BEFORE INSERT
    ON all_groups
    FOR EACH ROW
BEGIN
    IF EXISTS(SELECT is_active FROM curators WHERE (person_id = NEW.curator_id) AND (is_active = 0)) THEN
        BEGIN
            DECLARE error_message VARCHAR(128);
            SET error_message =
                    'You can not add the group because you selected a curator which is not active. Try to use other curator.';
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
        END;
    END IF;
END $$
DELIMITER ;


-- Триггер запрещающий обновлять группу, если для нее указан куратор с is_active = 0
DELIMITER $$
CREATE TRIGGER all_groups_UPDATE
    BEFORE UPDATE
    ON all_groups
    FOR EACH ROW
BEGIN
    IF EXISTS(SELECT is_active FROM curators WHERE (person_id = NEW.curator_id) AND (is_active = false)) THEN
        BEGIN
            DECLARE error_message VARCHAR(128);
            SET error_message =
                    'You can not update the group because you selected a curator which is not active. Try to use other curator.';
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
        END;
    END IF;
END $$
DELIMITER ;

-- -------------------- Окончание создания триггеров


INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
VALUES (10001, 'Светлана', 'Светкина', 'Светлановна', 'М', '1977-04-20'),
       (10002, 'Наталья', 'Натальева', 'Натальевна', 'М', '1977-04-20'),
       (10003, 'Ирина', 'Иринина', 'Ириновна', 'Ж', '1977-04-20'),
       (10004, 'Мишка', 'Мишкин', 'Мишкинин', 'Ж', '1977-04-20');

INSERT INTO curators (person_id, is_active)
VALUES (2, true),
       (3, false);

INSERT INTO scientists (person_id, research_directions)
VALUES (3, 'физика'),
       (4, 'паттерны проектирования');



INSERT INTO all_groups (group_name, speciality, education_year, number_of_semester, curator_id)
VALUES ('ИУ9-11Б', 'Программная инженерия', 2019, 1, 2),
       ('ИУ9-12Б', 'Информационная безопасность', 2019, 2, 2),
       ('ИУ9-21Б', 'Программная инженерия', 2019, 1, 2),
       ('ИУ9-22Б', 'Информационная безопасность', 2019, 2, 2),
       ('ИУ9-31Б', 'Программная инженерия', 2019, 1, 2),
       ('ИУ9-32Б', 'Информационная безопасность', 2019, 1, 2),
       ('ИУ9-41Б', 'Программная инженерия', 2019, 2, 2),
       ('ИУ9-42Б', 'Информационная безопасность', 2019, 2, 2),

       ('ИУ9-11Б', 'Программная инженерия', 2020, 1, 2),
       ('ИУ9-12Б', 'Информационная безопасность', 2020, 2, 2),
       ('ИУ9-21Б', 'Программная инженерия', 2020, 1, 2),
       ('ИУ9-22Б', 'Информационная безопасность', 2020, 2, 2),
       ('ИУ9-31Б', 'Программная инженерия', 2020, 1, 2),
       ('ИУ9-32Б', 'Информационная безопасность', 2020, 1, 2),
       ('ИУ9-41Б', 'Программная инженерия', 2020, 2, 2),
       ('ИУ9-42Б', 'Информационная безопасность', 2020, 2, 2);

INSERT INTO all_students (student_number, first_name, middle_name, last_name, sex, date_of_birth, group_id)
VALUES (10000, 'Иван', 'Сергеевич', 'Степанов', 'М', '1999-03-20', 1),
       (10001, 'Наталья', 'Андреевна', 'Чичикова', 'Ж', '2000-06-10', 1),
       (10002, 'Виктор', 'Сидорович', 'Белов', 'М', '1999-01-10', 1),
       (10003, 'Петр', 'Викторович', 'Сушкин', 'М', '2000-03-12', 1),
       (10004, 'Вероника', 'Сергеевна', 'Ковалева', 'Ж', '1999-07-19', 2),
       (10005, 'Ирина', 'Федоровна', 'Истомина', 'Ж', '2002-04-29', 2),
       (10006, 'Вероника', 'Афсона', 'Нотовна', 'Ж', '1999-07-19', 2),
       (10007, 'Ирина', 'Маня', 'Филотовна', 'Ж', '2002-04-29', 2),

       (10008, 'Адексей', 'Сергеевич', 'Степанов', 'М', '1999-03-20', 3),
       (10009, 'Владимир', 'Андреевна', 'Чичикова', 'Ж', '2000-06-10', 3),
       (10010, 'Михаил', 'Сидорович', 'Белов', 'М', '1999-01-10', 3),
       (10011, 'Анатолий', 'Викторович', 'Сушкин', 'М', '2000-03-12', 3),
       (10012, 'Юлия', 'Сергеевна', 'Ковалева', 'Ж', '1999-07-19', 4),
       (10013, 'Светлана', 'Федоровна', 'Истомина', 'Ж', '2002-04-29', 4),
       (10014, 'Ксения', 'Афсона', 'Нотовна', 'Ж', '1999-07-19', 4),
       (10015, 'Татьяна', 'Маня', 'Филотовна', 'Ж', '2002-04-29', 4);






