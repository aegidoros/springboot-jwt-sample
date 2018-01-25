DROP SCHEMA IF EXISTS `security`;

CREATE DATABASE  IF NOT EXISTS `security`;

USE `security`;
--
-- Table structure for table `book_detail`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` boolean NOT NULL,
  `role_id` bigint NOT NULL ,
  PRIMARY KEY (`id`),
  INDEX `fk_roleId_user_idx`(`role_id` ASC),
  CONSTRAINT `fk_roleId_user`
  FOREIGN KEY (`role_id`)
    REFERENCES  `role`(`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `permission`;

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE IF NOT EXISTS `role_permission`(
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`),
  INDEX `fk_roleId_role_permission_idx` (`role_id` ASC),
  INDEX `fk_permissionId_role_permission_idx` (`permission_id` ASC),
  CONSTRAINT `fk_roleId_role_permission`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_permissionId_role_permission`
  FOREIGN KEY (`permission_id`)
  REFERENCES `permission` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB DEFAULT CHARSET=utf8;



