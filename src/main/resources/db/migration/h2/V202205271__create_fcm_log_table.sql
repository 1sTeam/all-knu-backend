DROP TABLE IF EXISTS `fcm_log`;

CREATE TABLE `fcm_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `admin_email` VARCHAR(100),
    `title` VARCHAR(100),
    `body` VARCHAR(511),
    `link` VARCHAR(255),
    `timestamp` DATETIME
);