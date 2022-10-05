

DROP TABLE IF EXISTS ReimbursementTable;
DROP TABLE IF EXISTS UserTable;
DROP TABLE IF EXISTS StatusTable;
DROP TABLE IF EXISTS RoleTable;

CREATE TABLE RoleTable (

	role_id SERIAL PRIMARY KEY,
	role_name VARCHAR(10)

);


CREATE TABLE UserTable (

	user_id SERIAL PRIMARY KEY,
	username VARCHAR(30) NOT NULL UNIQUE,
	pwd VARCHAR(30) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	role_id INTEGER REFERENCES RoleTable(role_id) NOT NULL

);

CREATE TABLE ReimbursementTable(

	request_id SERIAL PRIMARY KEY,
	reimbursement_amount INTEGER NOT NULL CHECK (reimbursement_amount > 0),
	description VARCHAR(50) NOT NULL,
	ticket_status VARCHAR(10),
	submitter_id INTEGER REFERENCES UserTable(user_id) NOT NULL,
	manager_id INTEGER REFERENCES UserTable(user_id)

);

INSERT INTO RoleTable(role_name)
VALUES
('Employee'),
('Manager');


INSERT INTO UserTable (username, pwd, first_name, last_name, role_id)
VALUES
('Admin','pass123','John','Jones',2);


--truncate ReimbursementTable



