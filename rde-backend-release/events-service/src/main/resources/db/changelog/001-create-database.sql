CREATE TABLE headquarters (
    id CHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE campus (
    id CHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    headquarters_id CHAR(36) NOT NULL,
    CONSTRAINT fk_headquarters FOREIGN KEY (headquarters_id)
    REFERENCES headquarters (id)
    ON DELETE CASCADE -- O NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE places (
    id CHAR(36) PRIMARY KEY NOT NULL,
    site VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    campus_id CHAR(36) NOT NULL,
    CONSTRAINT fk_campus FOREIGN KEY (campus_id)
    REFERENCES campus (id)
    ON DELETE CASCADE -- O NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE events (
    id CHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    datetime DATETIME NOT NULL,
    img TEXT NOT NULL,
    capacity INT NOT NULL,
    availability INT NOT NULL,
    status ENUM('active', 'canceled', 'finished') NOT NULL,
    category ENUM('festival', 'library', 'sport', 'conference', 'workshop') NOT NULL,
    target ENUM('general', 'adult') NOT NULL,
    modality ENUM('virtual', 'onsite') NOT NULL,
    -- transport BOOLEAN NOT NULL,
    user_id CHAR(36) NOT NULL,
    place_id CHAR(36) NOT NULL,
    CONSTRAINT fk_place FOREIGN KEY (place_id)
    REFERENCES places (id)
    ON DELETE CASCADE -- O NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE bookings (
    id CHAR(36) PRIMARY KEY NOT NULL,
    datetime DATETIME NOT NULL,
    status VARCHAR(255) DEFAULT 'active', -- canceled
    user_id CHAR(36) NOT NULL,
    event_id CHAR(36) NOT NULL,
    CONSTRAINT fk_event FOREIGN KEY (event_id)
    REFERENCES events (id)
    ON DELETE CASCADE -- O NO ACTION
    ON UPDATE CASCADE
);
