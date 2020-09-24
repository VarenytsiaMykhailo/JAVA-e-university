DROP DATABASE IF EXISTS e_university;

CREATE DATABASE e_university DEFAULT CHARACTER SET 'utf8';

USE e_university;

CREATE TABLE roles
(
  role_id int unsigned not null auto_increment,
  role varchar(50) not null unique,
  primary key (role_id)
) engine = InnoDB;

CREATE TABLE users
(
  user_id int unsigned not null auto_increment,
  role_id int unsigned,
  login varchar(255) not null unique,
  password varchar(255) not null,
  email varchar(255) not null,
  primary key (user_id),
  foreign key (role_id) references roles (role_id) ON DELETE SET NULL ON UPDATE SET NULL
) engine = InnoDB;

CREATE TABLE department_staff
(
  person_id int unsigned not null auto_increment,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  middle_name varchar(255) not null,
  date_of_birth date not null,
  sex char(1) not null,
  year_of_teaching int not null,
  primary key (person_id)
) engine = InnoDB;

CREATE TABLE curators
(
  curator_id int unsigned not null auto_increment,
  person_id int unsigned not null,
  primary key (curator_id),
  foreign key (person_id) references department_staff (person_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;


CREATE TABLE all_groups
(
  group_id int unsigned not null auto_increment,
  curator_id int unsigned,
  group_name varchar(255) not null,
  speciality varchar(255) not null,
  education_year varchar(255) not null,
  primary key (group_id),
  foreign key (curator_id) references curators (curator_id) ON DELETE SET NULL ON UPDATE CASCADE
) engine = InnoDB;

CREATE TABLE students
(
  student_id int unsigned not null auto_increment,
  group_id int unsigned,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  middle_name varchar(255) not null,
  date_of_birth date not null,
  sex char(1) not null,
  primary key (student_id),
  foreign key (group_id) references all_groups (group_id) ON DELETE SET NULL ON UPDATE CASCADE
) engine = InnoDB;

CREATE TABLE student_group_history
(
  history_id int unsigned not null auto_increment,
  student_id int unsigned not null,
  group_id int unsigned not null,
  primary key (history_id),
  foreign key (student_id) references students (student_id) ON DELETE CASCADE ON UPDATE CASCADE,
  foreign key (group_id) references all_groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB;

SET NAMES 'utf8';

INSERT INTO roles (role)
VALUES ('ADMIN');
INSERT INTO roles (role)
VALUES ('USER');
INSERT INTO roles (role)
VALUES ('UNKNOWN');


INSERT INTO users (role_id, login, password, email)
VALUES (1, 'AdminAdmin', 'AdminAdmin', 'example@example.ru');
INSERT INTO users (role_id, login, password, email)
VALUES (2, 'UserUser', 'UserUser', 'example@example.ru');
