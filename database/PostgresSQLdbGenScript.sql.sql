-- Типы учетных записей
CREATE TABLE roles
(
    role_id SERIAL,
    role    VARCHAR(50) NOT NULL,

    PRIMARY KEY (role_id)
);


-- Штат сотрудников
CREATE TABLE department_staff
(
    person_id       SERIAL,
    person_contract INT UNIQUE  NOT NULL CHECK (person_contract >= 10000),
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    middle_name     VARCHAR(50) NOT NULL,
    sex             CHAR(1)     NOT NULL CHECK (sex IN ('М', 'Ж', 'Н')),
    date_of_birth   DATE        NOT NULL,

    PRIMARY KEY (person_id)
);

-- CREATE INDEX IX_department_staff_person_contract ON department_staff(person_contract); -- Не требуется. Такой индекс для UNIQUE полей PosgresSQL создает автоматически.


-- Заметки для сотрудников
CREATE TABLE department_staff_notes
(
    note_id         SERIAL,
    person_id       INT           NOT NULL,
    date_time_added TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- CREATE INDEX IX_department_staff_notes_person_id ON department_staff_notes(person_id);  -- Не требуется. PostgresSQL создает индексы для внешних ключей автоматически


-- Аккаунты пользователей
CREATE TABLE users
(
    user_id   SERIAL,
    login     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    email     VARCHAR(255)        NOT NULL,
    role_id   INT                 NOT NULL,
    person_id INT                 NOT NULL,

    PRIMARY KEY (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Ученые (подтип department_staff)
CREATE TABLE scientists
(
    person_id           SERIAL,
    research_directions VARCHAR(1000) NOT NULL,

    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Представление для ученых Department_staff_scientists_view
CREATE VIEW Department_staff_scientists_view
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
    person_id SERIAL,
    is_active BOOLEAN NOT NULL,                                                                           -- false - не работает куратором в настоящее время, давать ему новые группы нельзя, true - куратору можно выдавать новые группы для курирования.

    PRIMARY KEY (person_id),
    FOREIGN KEY (person_id) REFERENCES department_staff (person_id) ON DELETE NO ACTION ON UPDATE CASCADE -- На ON DELETE NO ACTION чтобы не терять информацию о кураторах для групп
);


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
    group_id           SERIAL,
    group_name         VARCHAR(50)  NOT NULL,
    speciality         VARCHAR(255) NOT NULL,
    education_year     INT          NOT NULL CHECK (education_year >= 2000), -- Пример: 2020
    number_of_semester INT          NOT NULL CHECK (number_of_semester IN (1, 2)),
    curator_id         INT          NOT NULL,

    PRIMARY KEY (group_id),
    FOREIGN KEY (curator_id) REFERENCES curators (person_id) ON DELETE NO ACTION ON UPDATE CASCADE,

    CONSTRAINT UQ_all_groups_group_name_education_year UNIQUE (group_name, education_year)
);

CREATE INDEX IX_all_groups_group_name ON all_groups (group_name);


-- Заметки для групп
CREATE TABLE all_groups_notes
(
    note_id         SERIAL,
    group_id        INT           NOT NULL,
    date_time_added TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- CREATE INDEX IX_all_groups_notes_group_id ON all_groups_notes(group_id); -- Не требуется. PostgresSQL создает индексы для внешних ключей автоматически


-- Студенты
CREATE TABLE all_students
(
    student_id         SERIAL,
    student_number     INT         NOT NULL UNIQUE CHECK (student_number >= 10000), -- Номер зачетной книжки или другого документа, позволяющего уникально идентифицировать студента. Пример: 10001
    first_name         VARCHAR(50) NOT NULL,
    last_name          VARCHAR(50) NOT NULL,
    middle_name        VARCHAR(50) NOT NULL,
    sex                CHAR(1)     NOT NULL CHECK (sex IN ('М', 'Ж', 'Н')),
    date_of_birth      DATE        NOT NULL,
    group_id           INT         NOT NULL,
    education_year     INT         NOT NULL CHECK (education_year >= 2000),         -- Пример: 2020
    number_of_semester INT         NOT NULL CHECK (number_of_semester IN (1, 2)),

    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE NO ACTION ON UPDATE CASCADE,
    PRIMARY KEY (student_id)
);

CREATE INDEX IX_all_students_last_name_first_name ON all_students (last_name, first_name);
-- CREATE INDEX IX_all_students_student_number ON all_students(student_number); -- Не требуется. Такой индекс для UNIQUE полей MySQL создает автоматически


-- Заметки для студентов
CREATE TABLE all_students_notes
(
    note_id         SERIAL,
    student_id      INT           NOT NULL,
    date_time_added TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    note_text       VARCHAR(1000) NOT NULL DEFAULT '',

    PRIMARY KEY (note_id),
    FOREIGN KEY (student_id) REFERENCES all_students (student_id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- CREATE INDEX IX_all_students_notes_student_id ON all_students_notes(student_id); -- Не требуется. MySQL создает индексы для внешних ключей автоматически


-- -------------------- Объявление пользовательских функций, процедур (вставка встроенных строк в таблицы)


-- Пользовательская функция для получения встроенного в систему person_contract
CREATE FUNCTION USF_get_default_person_contract() RETURNS INT AS
$$
DECLARE
    default_person_contract INT;
BEGIN
    default_person_contract = 10000;
    RETURN default_person_contract;
END;
$$ LANGUAGE plpgSQL;


-- Пользовательская функция для получения встроенного в систему group_name
CREATE FUNCTION USF_get_default_group_name() RETURNS VARCHAR(50) AS
$$
DECLARE
    default_group_name VARCHAR(50);
BEGIN
    default_group_name = 'Неопределенные студенты';
    RETURN default_group_name;
END
$$ LANGUAGE plpgSQL;


-- Пользовательская функция для получения встроенного в систему education_year
CREATE FUNCTION USF_get_default_education_year() RETURNS INT AS
$$
DECLARE
    default_education_year INT;
BEGIN
    default_education_year = 2000;
    RETURN default_education_year;
END
$$ LANGUAGE plpgSQL;


-- Пользовательская функция для получения person_id по встроенному в систему person_contract
CREATE FUNCTION USF_get_person_id_by_person_contract(input_person_contract INT) RETURNS INT AS
$$
DECLARE
    output_person_id INT;
BEGIN
    SELECT person_id INTO output_person_id FROM department_staff WHERE person_contract = input_person_contract;
    RETURN output_person_id;
END
$$ LANGUAGE plpgSQL;


-- Пользовательская функция для вставки встроенных строк в таблицы
CREATE FUNCTION USP_insert_default_values() RETURNS VOID AS
$$
INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
VALUES (USF_get_default_person_contract(), 'Университет', 'Университет', 'Университет', 'Н', CURRENT_DATE);

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
$$ LANGUAGE SQL;

SELECT USP_insert_default_values();


-- Пользовательская функция для получения встроенного в систему curator_id
CREATE FUNCTION USF_get_default_curator_id() RETURNS INT AS
$$
DECLARE
    curator_id INT;
BEGIN
    curator_id = USF_get_person_id_by_person_contract(USF_get_default_person_contract());
    RETURN curator_id;
END
$$ LANGUAGE plpgSQL;


-- Пользовательская функция для получения встроенного в систему group_id
CREATE FUNCTION USF_get_default_group_id() RETURNS INT AS
$$
DECLARE
    output_group_id INT;
BEGIN
    SELECT group_id
    INTO output_group_id
    FROM all_groups
    WHERE group_name LIKE USF_get_default_group_name()
      AND education_year = USF_get_default_education_year();
    RETURN output_group_id;
END
$$ LANGUAGE plpgSQL;

-- -------------------- Окончание вставки стандартных значений и объявлений пользовательских функций и процедур


-- История о состоянии студента в конкретной группе
CREATE TABLE student_group_history
(
    history_id         SERIAL,
    student_id         INT  NOT NULL,
    group_id           INT  NOT NULL DEFAULT USF_get_default_group_id(), -- костыль. Здесь нужно вызвать функцию USF_get_default_group_id(), но MySQL не поддерживает вызов функций для ограничений DEFAULT. Возможно вставлять только статические (константные) значения
    date_of_enrollment DATE NOT NULL DEFAULT CURRENT_DATE,
    date_of_expulsion  DATE,

    PRIMARY KEY (history_id),
    FOREIGN KEY (student_id) REFERENCES all_students (student_id) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES all_groups (group_id) ON DELETE NO ACTION ON UPDATE CASCADE,

    UNIQUE (student_id, group_id, date_of_enrollment, date_of_expulsion),
    CHECK (date_of_enrollment <= date_of_expulsion)
);


-- -------------------- Объявление триггеров


-- Триггер запрещающий удаление записей из student_group_history
CREATE FUNCTION USF_TRIGGER_student_group_history_DELETE() RETURNS TRIGGER AS
$$
BEGIN
    RAISE EXCEPTION 'You can not delete rows in student_group_history table.';
    RETURN NULL;
END
$$ LANGUAGE plpgSQL;

CREATE TRIGGER student_group_history_DELETE
    BEFORE DELETE
    ON student_group_history
    FOR EACH ROW
EXECUTE PROCEDURE USF_TRIGGER_student_group_history_DELETE();


-- Триггер запрещающий обновление полей student_id или group_id в student_group_history
CREATE FUNCTION USF_TRIGGER_student_group_history_UPDATE() RETURNS TRIGGER AS
$$
BEGIN
    IF (NEW.student_id != OLD.student_id OR NEW.group_id != OLD.group_id) THEN
        RAISE EXCEPTION 'You can not update student_id or group_id columns in student_group_history table.';
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgSQL;

CREATE TRIGGER student_group_history_UPDATE
    BEFORE UPDATE
    ON student_group_history
    FOR EACH ROW
EXECUTE PROCEDURE USF_TRIGGER_student_group_history_UPDATE();


-- Триггер создающий запись в student_group_history, при добавлении студента в систему и его зачислении в группу
CREATE FUNCTION USF_TRIGGER_all_students_INSERT() RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO student_group_history (student_id, group_id, date_of_enrollment, date_of_expulsion)
    VALUES (NEW.student_id, NEW.group_id, CURRENT_DATE, NULL);
    RETURN NULL;
END
$$ LANGUAGE plpgSQL;

CREATE TRIGGER all_students_INSERT
    AFTER INSERT
    ON all_students
    FOR EACH ROW
EXECUTE PROCEDURE USF_TRIGGER_all_students_INSERT();


-- Триггер создающий запись в student_group_history, при переводе студента в другую группу
-- Триггер отчисляющий студента из предыдущей группы (если существует запись в student_group_history), заполняя поле date_of_expulsion, при его добавлении в другую группуCREATE FUNCTION USF_TRIGGER_all_students_INSERT() RETURNS TRIGGER AS $$
CREATE FUNCTION USF_TRIGGER_all_students_UPDATE() RETURNS TRIGGER AS
$$
BEGIN
    IF (NEW.group_id != OLD.group_id) THEN
        BEGIN
            UPDATE student_group_history
            SET date_of_expulsion = CURRENT_DATE
            WHERE (student_id = NEW.student_id)
              AND (group_id = OLD.group_id)
              AND date_of_expulsion IS NULL;

            INSERT INTO student_group_history (student_id, group_id, date_of_enrollment, date_of_expulsion)
            VALUES (NEW.student_id, NEW.group_id, CURRENT_DATE, NULL);
        END;
    END IF;
    RETURN NULL;
END
$$ LANGUAGE plpgSQL;

CREATE TRIGGER all_students_UPDATE
    AFTER UPDATE
    ON all_students
    FOR EACH ROW
EXECUTE PROCEDURE USF_TRIGGER_all_students_UPDATE();


-- Триггер запрещающий добавить новую группу или обновить существующую, если для нее указан куратор с is_active = 0
CREATE FUNCTION USF_TRIGGER_all_students_INSERT_UPDATE() RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(SELECT is_active FROM curators WHERE (person_id = NEW.curator_id) AND (is_active = false)) THEN
        BEGIN
            RAISE EXCEPTION 'You can not add or update the group because you selected a curator which is not active. Try to use other curator.';
        END;
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgSQL;

CREATE TRIGGER all_students_INSERT_UPDATE
    BEFORE INSERT OR UPDATE
    ON all_groups
    FOR EACH ROW
EXECUTE PROCEDURE USF_TRIGGER_all_students_INSERT_UPDATE();

-- -------------------- Окончание создания триггеров


DROP FUNCTION USF_TRIGGER_all_students_INSERT_UPDATE CASCADE;
INSERT INTO department_staff (person_contract, first_name, last_name, middle_name, sex, date_of_birth)
VALUES (10001, 'Светлана', 'Светкина', 'Светлановна', 'М', '1900-04-20'),
       (10002, 'Наталья', 'Натальева', 'Натальевна', 'М', '1900-04-20'),
       (10003, 'Ирина', 'Иринина', 'Ириновна', 'Ж', '1900-04-20'),
       (10004, 'Мишка', 'Мишкин', 'Мишкинин', 'Ж', '1900-04-20');

INSERT INTO curators
VALUES (2, true),
       (3, false);

INSERT INTO scientists (person_id, research_directions)
VALUES (3, 'физика'),
       (4, 'паттерны проектирования');



INSERT INTO all_groups (group_name, speciality, education_year, number_of_semester, curator_id)
VALUES ('Первая группа', 'Программная инженерия', 2019, 1, 2),
       ('Вторая группа', 'Информационная безопасность', 2020, 2, 2);



INSERT INTO all_students (student_number, first_name, middle_name, last_name, sex, date_of_birth, group_id,
                          education_year, number_of_semester)
VALUES (10000, 'Иван', 'Сергеевич', 'Степанов', 'М', '1999-03-20', 2, 2019, 1),
       (10001, 'Наталья', 'Андреевна', 'Чичикова', 'Ж', '2000-06-10', 1, 2019, 1),
       (10002, 'Виктор', 'Сидорович', 'Белов', 'М', '1999-01-10', 2, 2020, 2),
       (10003, 'Петр', 'Викторович', 'Сушкин', 'М', '2000-03-12', 3, 2020, 1),
       (10004, 'Вероника', 'Сергеевна', 'Ковалева', 'Ж', '1999-07-19', 3, 2020, 2),
       (10005, 'Ирина', 'Федоровна', 'Истомина', 'Ж', '2002-04-29', 3, 2020, 1);

INSERT INTO all_students (student_number, first_name, middle_name, last_name, sex, date_of_birth, group_id,
                          education_year, number_of_semester)
VALUES (10006, 'Макарон', 'Макарон', 'Макарон', 'М', '1999-03-20', 2, 2019, 1);

INSERT INTO all_students (student_number, first_name, middle_name, last_name, sex, date_of_birth, group_id,
                          education_year, number_of_semester)
VALUES (10007, 'SADASS', 'ASDASD', 'Макарон', 'М', '1999-03-20', 2, 2019, 1);

INSERT INTO student_group_history (student_id, group_id, date_of_enrollment, date_of_expulsion)
VALUES (1, 2, DATE(CURRENT_TIMESTAMP), NULL);

SELECT *
FROM student_group_history;
SELECT *
FROM all_students;

DELETE
FROM student_group_history
WHERE history_id = 2;
UPDATE student_group_history
SET student_id = 2
WHERE history_id = 2;
UPDATE student_group_history
SET group_id = 3
WHERE history_id = 2;

UPDATE all_students
SET student_id = 33
WHERE student_id = 7;
UPDATE all_students
SET group_id = 3
WHERE student_id = 1;
UPDATE student_group_history
SET date_of_expulsion = CURRENT_DATE
WHERE (student_id = 1)
  AND (group_id = 2)
  AND date_of_expulsion IS NULL;
SELECT *
FROM student_group_history
WHERE (student_id = 1)
  AND (group_id = 2)
  AND date_of_expulsion IS NULL;

UPDATE student_group_history
SET date_of_expulsion = '2021-10-12'
WHERE date_of_expulsion IS NULL;
UPDATE student_group_history
SET date_of_expulsion = CURRENT_DATE
WHERE student_id = 8;


INSERT INTO student_group_history (student_id, date_of_expulsion)
VALUES (1, NULL);
SELECT CURRENT_DATE;
SELECT DATE(CURRENT_TIMESTAMP);

SELECT *
FROM all_groups;
SELECT *
FROM curators;
UPDATE all_groups
SET curator_id = 2
WHERE group_id = 1;





