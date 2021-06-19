CREATE TABLE IF NOT EXISTS employee (
    id serial PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(50),
    date_of_birth TIMESTAMP
);
