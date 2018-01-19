DROP SCHEMA IF EXISTS `security`;

CREATE DATABASE  IF NOT EXISTS `security`;

USE `security`;
--
-- Table structure for table `book_detail`
--
DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255),
  `enabled` boolean NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE IF NOT EXISTS `user_role`(
`user_id` bigint NOT NULL,
`role_id` bigint NOT NULL,
PRIMARY KEY (`user_id`, `role_id`),
INDEX `fk_userId_user_role_idx` (`user_id` ASC),
INDEX `fk_roleId_user_role_idx` (`role_id` ASC),
CONSTRAINT `fk_userId_user_role`
	FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_roleId_user_role`
	FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `privilege`;

CREATE TABLE IF NOT EXISTS `privilege` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role_privilege`;

CREATE TABLE IF NOT EXISTS `role_privilege`(
  `role_id` bigint NOT NULL,
  `privilege_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `privilege_id`),
  INDEX `fk_roleId_role_privilege_idx` (`role_id` ASC),
  INDEX `fk_privilegeId_role_privilege_idx` (`privilege_id` ASC),
  CONSTRAINT `fk_roleId_role_privilege`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_privilegeId_role_privilege`
  FOREIGN KEY (`privilege_id`)
  REFERENCES `privilege` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB DEFAULT CHARSET=utf8;



