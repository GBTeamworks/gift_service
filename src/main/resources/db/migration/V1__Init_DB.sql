# # Создание основной БД
SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

# CREATE DATABASE IF NOT EXISTS `gift_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
#
# CREATE DATABASE IF NOT EXISTS `gift_service_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `gift_service`;

CREATE TABLE `authority`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `role` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `authority` (`id`, `role`)
VALUES (1, 'USER'),
       (2, 'ADMIN');

CREATE TABLE `cart`
(
    `id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `status` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `cart` (`id`, `status`)
VALUES (1, 'Активна'),
       (2, 'Активна');

CREATE TABLE `categories`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `gift`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `user_id`     bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKrv0c4mo0tryp2o8skng8k5qgt` (`user_id`),
    CONSTRAINT `FKrv0c4mo0tryp2o8skng8k5qgt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `users`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `username`  varchar(255) DEFAULT NULL,
    `birthdate` date       NOT NULL,
    `password`  varchar(255) DEFAULT NULL,
    `email`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `users` (`id`, `username`, `birthdate`, `password`, `email`)
VALUES (1, 'user', '2022-12-02', '$2a$10$kkxw.678oiJgg7CzYNIV4OuWzfon1Ikjvim1ksOG0prlYc4gZ1sA6', 'testUser@mail.ru'),
       (2, 'admin', '2022-12-02', '$2a$10$5NnckycFCuvSjthG01S2qO8KAHgS5JcTB5in6oLYOfTESB/wHVGfG', 'testAdmin@mail.ru');

CREATE TABLE `cart_gift`
(
    `cart_id` bigint(20) NOT NULL,
    `gift_id` bigint(20) NOT NULL,
    PRIMARY KEY (`gift_id`, `cart_id`),
    UNIQUE KEY `UK_6o5b0acs5iwnfurs059ip9v9y` (`gift_id`),
    KEY `FKlvm9v4cqooj16iiy0dx07xnd3` (`cart_id`),
    CONSTRAINT `FKlvm9v4cqooj16iiy0dx07xnd3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
    CONSTRAINT `FKm6e6yf0eaqnpa8uisvup71qt9` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


CREATE TABLE `cart_status`
(
    `cart_id` bigint(20) NOT NULL,
    `status`  varchar(255) DEFAULT NULL,
    KEY `FKj3u4n4cs12gou0jqy6nnv1c2h` (`cart_id`),
    CONSTRAINT `FKj3u4n4cs12gou0jqy6nnv1c2h` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `category_gift`
(
    `category_id` bigint(20) NOT NULL,
    `gift_id`     bigint(20) NOT NULL,
    PRIMARY KEY (`category_id`, `gift_id`),
    KEY `FK2f6u2owlfelhtgeb6geh10mdk` (`gift_id`),
    CONSTRAINT `FK2f6u2owlfelhtgeb6geh10mdk` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`),
    CONSTRAINT `FKqnauvgboos0uf9qk3w4jaxe8y` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `user_authority`
(
    `user_id`      bigint(20) NOT NULL,
    `authority_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `authority_id`),
    KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
    CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
    CONSTRAINT `FKhi46vu7680y1hwvmnnuh4cybx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `user_authority_ibfk_1` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `user_authority_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `user_authority` (`user_id`, `authority_id`)
VALUES (1, 1),
       (2, 2);

CREATE TABLE `user_cart`
(
    `cart_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`cart_id`),
    KEY `FKjnc3hqv2aeg4rb38ghsrp561` (`user_id`),
    CONSTRAINT `FKjnc3hqv2aeg4rb38ghsrp561` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKna1i1fqja6kfp6yj0b6q64ah9` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `user_cart` (`cart_id`, `user_id`)
VALUES (1, 1),
       (2, 2);

CREATE TABLE `user_friends`
(
    `friend_id`  bigint(20) NOT NULL,
    `channel_id` bigint(20) NOT NULL,
    PRIMARY KEY (`channel_id`, `friend_id`),
    KEY `FK11y5boh1e7gh60rdqixyetv3x` (`friend_id`),
    CONSTRAINT `FK11y5boh1e7gh60rdqixyetv3x` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK63kiy3nfsyndsgbuucg2hvney` FOREIGN KEY (`channel_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

# Создание тестовой БД

USE `gift_service_test`;

DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `role` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `cart_gift`;
CREATE TABLE `cart_gift`
(
    `cart_id` bigint(20) NOT NULL,
    `gift_id` bigint(20) NOT NULL,
    PRIMARY KEY (`cart_id`, `gift_id`),
    UNIQUE KEY `UK_6o5b0acs5iwnfurs059ip9v9y` (`gift_id`),
    CONSTRAINT `FKlvm9v4cqooj16iiy0dx07xnd3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
    CONSTRAINT `FKm6e6yf0eaqnpa8uisvup71qt9` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `cart_status`;
CREATE TABLE `cart_status`
(
    `cart_id` bigint(20) NOT NULL,
    `status`  varchar(255) DEFAULT NULL,
    KEY `FKj3u4n4cs12gou0jqy6nnv1c2h` (`cart_id`),
    CONSTRAINT `FKj3u4n4cs12gou0jqy6nnv1c2h` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `category_gift`;
CREATE TABLE `category_gift`
(
    `category_id` bigint(20) NOT NULL,
    `gift_id`     bigint(20) NOT NULL,
    PRIMARY KEY (`category_id`, `gift_id`),
    KEY `FK2f6u2owlfelhtgeb6geh10mdk` (`gift_id`),
    CONSTRAINT `FK2f6u2owlfelhtgeb6geh10mdk` FOREIGN KEY (`gift_id`) REFERENCES `gift` (`id`),
    CONSTRAINT `FKqnauvgboos0uf9qk3w4jaxe8y` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`
(
    `installed_rank` int(11)       NOT NULL,
    `version`        varchar(50)            DEFAULT NULL,
    `description`    varchar(200)  NOT NULL,
    `type`           varchar(20)   NOT NULL,
    `script`         varchar(1000) NOT NULL,
    `checksum`       int(11)                DEFAULT NULL,
    `installed_by`   varchar(100)  NOT NULL,
    `installed_on`   timestamp     NOT NULL DEFAULT current_timestamp(),
    `execution_time` int(11)       NOT NULL,
    `success`        tinyint(1)    NOT NULL,
    PRIMARY KEY (`installed_rank`),
    KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `gift`;
CREATE TABLE `gift`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `title`       varchar(255) DEFAULT NULL,
    `user_id`     bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKrv0c4mo0tryp2o8skng8k5qgt` (`user_id`),
    CONSTRAINT `FKrv0c4mo0tryp2o8skng8k5qgt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `birthdate` date         DEFAULT NULL,
    `email`     varchar(255) DEFAULT NULL,
    `password`  varchar(255) DEFAULT NULL,
    `username`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority`
(
    `user_id`      bigint(20) NOT NULL,
    `authority_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `authority_id`),
    KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
    CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
    CONSTRAINT `FKhi46vu7680y1hwvmnnuh4cybx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `user_cart`;
CREATE TABLE `user_cart`
(
    `cart_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`cart_id`),
    KEY `FKjnc3hqv2aeg4rb38ghsrp561` (`user_id`),
    CONSTRAINT `FKjnc3hqv2aeg4rb38ghsrp561` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKna1i1fqja6kfp6yj0b6q64ah9` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;


DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends`
(
    `friend_id`  bigint(20) NOT NULL,
    `channel_id` bigint(20) NOT NULL,
    PRIMARY KEY (`channel_id`, `friend_id`),
    KEY `FK11y5boh1e7gh60rdqixyetv3x` (`friend_id`),
    CONSTRAINT `FK11y5boh1e7gh60rdqixyetv3x` FOREIGN KEY (`friend_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK63kiy3nfsyndsgbuucg2hvney` FOREIGN KEY (`channel_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;