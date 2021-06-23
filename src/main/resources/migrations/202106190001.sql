CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY,
    uid VARCHAR(20) UNIQUE NOT NULL,
    role VARCHAR(12) NOT NULL, -- admin, doctor, nurse, engineer
    avatar VARCHAR(255),
    name VARCHAR(20) NOT NULL,
    email VARCHAR(50),
    telephone VARCHAR(20),
    password VARCHAR(45),
    salt VARCHAR(10),
    status VARCHAR(10),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
