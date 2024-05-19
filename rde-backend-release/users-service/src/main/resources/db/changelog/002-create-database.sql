CREATE TABLE users (
                       id CHAR(36) PRIMARY KEY NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       birth DATE NOT NULL,
                       
                       student_id CHAR(6) NOT NULL,
                       email_address VARCHAR(255) NOT NULL,
                       phone VARCHAR(8) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       scholarship INT DEFAULT 0,
                       password VARCHAR(255) NOT NULL,
                       CONSTRAINT chk_scholarship CHECK (scholarship BETWEEN 0 AND 5)
);

CREATE TABLE admins (
                        id CHAR(36) PRIMARY KEY NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        phone VARCHAR(8) NOT NULL,
                        email_address VARCHAR(255) NOT NULL,
                        birth DATE NOT NULL,
                        password VARCHAR(255) NOT NULL
);