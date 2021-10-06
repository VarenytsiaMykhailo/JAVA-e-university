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