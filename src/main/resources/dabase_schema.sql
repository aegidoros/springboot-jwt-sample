CREATE DATABASE  IF NOT EXISTS `test`;

USE `test`;
--
-- Table structure for table `book_detail`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255),
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255),
  `enabled` boolean NOT NULL,
  `last_password_reset_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `book`
--
CREATE TABLE IF NOT EXISTS `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_authority`(
`user_id` bigint NOT NULL,
`authority_id` bigint NOT NULL,
PRIMARY KEY (`user_id`, `authority_id`),
INDEX `fk_userId_user_authority_idx` (`user_id` ASC),
INDEX `fk_authorityId_user_authority_idx` (`authority_id` ASC),
CONSTRAINT `fk_userId_user_authority`
	FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_authorityId_user_authority`
	FOREIGN KEY (`authority_id`)
    REFERENCES `authority` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;
