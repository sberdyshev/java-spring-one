DROP TABLE users_roles;
DROP TABLE users;
DROP TABLE roles;

CREATE TABLE IF NOT EXISTS users (
id INTEGER PRIMARY KEY,
name VARCHAR NOT NULL UNIQUE,
password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
id INTEGER PRIMARY KEY,
name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users_roles (
user_id INTEGER NOT NULL REFERENCES users(id),
role_id INTEGER NOT NULL REFERENCES roles(id)
);