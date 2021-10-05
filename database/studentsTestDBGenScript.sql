DROP DATABASE IF EXISTS e_university;

CREATE DATABASE e_university DEFAULT CHARACTER SET 'utf8';

USE e_university;

CREATE TABLE roles
(
  role_id int unsigned not null auto_increment,
  role varchar(50) not null,
  primary key (role_id)
) engine = InnoDB;

CREATE TABLE all_users
(
  user_id int unsigned not null auto_increment,
  login varchar(255) not null,
  password varchar(255) not null,
  email varchar(255) not null,
  role_id int unsigned,
  primary key (user_id),
  foreign key (role_id) references roles (role_id) ON DELETE SET NULL ON UPDATE SET NULL
) engine = InnoDB;

CREATE TABLE all_curators
(
  curator_id int unsigned not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  patronymic varchar(255) not null,
  date_of_birth date not null,
  sex char(1),
  year_of_teaching int not null,
  primary key (curator_id)
) engine = InnoDB;

CREATE TABLE all_groups
(
  group_id int unsigned not null auto_increment,
  group_name varchar(255) not null,
  curator_id int unsigned,
  speciality varchar(255) not null,
  primary key (group_id),
   foreign key (curator_id) references all_curators (curator_id) ON DELETE SET NULL ON UPDATE SET NULL
) engine = InnoDB;

CREATE TABLE all_students
(
  student_id int unsigned not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  patronymic varchar(255) not null,
  date_of_birth date not null,
  sex char(1),
  group_id int not null,
  education_year int not null,
  primary key (student_id)
) engine = InnoDB;

SET NAMES 'utf8';

INSERT INTO roles (role)
VALUES ('ADMIN');
INSERT INTO roles (role)
VALUES ('USER');
INSERT INTO roles (role)
VALUES ('UNKNOWN');


INSERT INTO all_users (login, password, email, role_id)
VALUES ('AdminAdmin', 'AdminAdmin', 'example@example.ru', 1);



INSERT INTO all_curators (first_name, patronymic, last_name, sex, date_of_birth, year_of_teaching)
VALUES ('Отсутствует', 'Отсутствует', 'Отсутствует', 'М', '1900-04-20', 1900);

INSERT INTO all_curators (first_name, patronymic, last_name, sex, date_of_birth, year_of_teaching)
VALUES ('Петр', 'Владимирович', 'Шапкин', 'М', '1980-04-29', 2020);

INSERT INTO all_curators (first_name, patronymic, last_name, sex, date_of_birth, year_of_teaching)
VALUES ('Николай', 'Владимирович', 'Безверхний', 'М', '1970-04-29', 2020);



INSERT INTO all_groups (group_name, curator_id, speciality)
VALUES ('Нулевая группа', 1, 'Неопределенные студенты');

INSERT INTO all_groups (group_name, curator_id, speciality)
VALUES ('Первая группа', 2, 'Программная инженерия');

INSERT INTO all_groups (group_name, curator_id, speciality)
VALUES ('Вторая группа', 3, 'Информационная безопасность');


INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Иван', 'Сергеевич', 'Степанов', 'М', '1999-03-20', 2, 2019);

INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Наталья', 'Андреевна', 'Чичикова', 'Ж', '2000-06-10', 1, 2019);

INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Виктор', 'Сидорович', 'Белов', 'М', '1999-01-10', 2, 2020);

INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Петр', 'Викторович', 'Сушкин', 'М', '2000-03-12', 3, 2020);

INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Вероника', 'Сергеевна', 'Ковалева', 'Ж', '1999-07-19', 3, 2020);

INSERT INTO all_students (first_name, patronymic, last_name, sex, date_of_birth, group_id, education_year)
VALUES ('Ирина', 'Федоровна', 'Истомина', 'Ж', '2002-04-29', 3, 2020);


