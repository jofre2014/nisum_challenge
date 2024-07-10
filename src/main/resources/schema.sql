-- Creación de la tabla user_app
CREATE TABLE IF NOT EXISTS user_app (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    is_active BOOLEAN NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP,
    last_login TIMESTAMP
);

CREATE SEQUENCE IF NOT EXISTS PHONE_SEQ START WITH 1 INCREMENT BY 1;

-- Creación de la tabla phone
CREATE TABLE IF NOT EXISTS phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES user_app(id)
);
