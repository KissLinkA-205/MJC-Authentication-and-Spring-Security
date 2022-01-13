DROP DATABASE module3;

CREATE DATABASE IF NOT EXISTS module3;

USE module3;

-- -----------------------------------------------------
-- Table module3.tags
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tags
(
    id   BIGINT UNSIGNED AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.gift_certificates
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificates
(
    id               BIGINT UNSIGNED AUTO_INCREMENT,
    name             VARCHAR(45) UNIQUE     NOT NULL,
    description      TEXT(300),
    price            DECIMAL(8, 2) UNSIGNED NOT NULL,
    duration         SMALLINT UNSIGNED      NOT NULL,
    create_date      DATETIME               NOT NULL,
    last_update_date DATETIME               NOT NULL,
    deleted          BOOLEAN DEFAULT false,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.gift_certificates_tags
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificates_has_tags
(
    id                  BIGINT UNSIGNED AUTO_INCREMENT,
    gift_certificate_id BIGINT UNSIGNED,
    tag_id              BIGINT UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);

-- -----------------------------------------------------
-- Table module3.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT UNSIGNED AUTO_INCREMENT,
    email    VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    role     VARCHAR(15)  NOT NULL,
    name     VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.orders
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS orders
(
    id                  BIGINT UNSIGNED AUTO_INCREMENT,
    price               DECIMAL(8, 2) UNSIGNED NOT NULL,
    purchase_time       DATETIME               NOT NULL,
    user_id             BIGINT UNSIGNED,
    gift_certificate_id BIGINT UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- -----------------------------------------------------
-- Table module3.tags_aud
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tags_aud
(
    id        BIGINT UNSIGNED AUTO_INCREMENT,
    tag_id    BIGINT UNSIGNED NOT NULL,
    tag_name  VARCHAR(30)     NOT NULL,
    operation VARCHAR(15)     NOT NULL,
    aud_time  DATETIME        NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.gift_certificates_aud
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificates_aud
(
    id                                BIGINT UNSIGNED AUTO_INCREMENT,
    gift_certificate_id               BIGINT UNSIGNED        NOT NULL,
    gift_certificate_name             VARCHAR(45)            NOT NULL,
    gift_certificate_description      TEXT(300),
    gift_certificate_price            DECIMAL(8, 2) UNSIGNED NOT NULL,
    gift_certificate_duration         SMALLINT UNSIGNED      NOT NULL,
    gift_certificate_create_date      DATETIME               NOT NULL,
    gift_certificate_last_update_date DATETIME               NOT NULL,
    deleted                           BOOLEAN,
    operation                         VARCHAR(15)            NOT NULL,
    aud_time                          DATETIME               NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.users_aud
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users_aud
(
    id            BIGINT UNSIGNED AUTO_INCREMENT,
    user_id       BIGINT UNSIGNED NOT NULL,
    user_email    VARCHAR(50)     NOT NULL UNIQUE,
    user_password VARCHAR(200)    NOT NULL,
    user_role     VARCHAR(15)     NOT NULL,
    user_name     VARCHAR(100)    NOT NULL,
    operation     VARCHAR(15)     NOT NULL,
    aud_time      DATETIME        NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table module3.orders_aud
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS orders_aud
(
    id                        BIGINT UNSIGNED AUTO_INCREMENT,
    order_id                  BIGINT UNSIGNED        NOT NULL,
    order_price               DECIMAL(8, 2) UNSIGNED NOT NULL,
    order_purchase_time       DATETIME               NOT NULL,
    order_user_id             BIGINT UNSIGNED,
    order_gift_certificate_id BIGINT UNSIGNED,
    operation                 VARCHAR(15)            NOT NULL,
    aud_time                  DATETIME               NOT NULL,
    PRIMARY KEY (id)
);
