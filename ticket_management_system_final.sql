-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ticket_management_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ticket_management_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ticket_management_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ticket_management_system` ;

-- -----------------------------------------------------
-- Table `ticket_management_system`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT NOW(),
  `updated_at` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`customer_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`customer_role` (
  `customer_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`customer_id`, `role_id`),
  INDEX `fk_customer_has_role_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_customer_has_role_customer1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_customer_has_role_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ticket_management_system`.`customer` (`id`),
  CONSTRAINT `fk_customer_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ticket_management_system`.`role` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`department` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT NOW(),
  `updated_at` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT NOW(),
  `updated_at` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  `dept_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKaqchbcb8i6nvtl9g6c72yba0p` (`dept_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `FKaqchbcb8i6nvtl9g6c72yba0p`
    FOREIGN KEY (`dept_id`)
    REFERENCES `ticket_management_system`.`department` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`employee_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`employee_role` (
  `employee_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`employee_id`, `role_id`),
  INDEX `fk_employee_has_role_role1_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_employee_has_role_employee1_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_employee_has_role_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `ticket_management_system`.`employee` (`id`),
  CONSTRAINT `fk_employee_has_role_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `ticket_management_system`.`role` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`ticket` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `detail` TEXT NOT NULL,
  `priority` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT NOW(),
  `updated_at` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  `customer_id` INT NULL DEFAULT NULL,
  `department_id` INT NULL DEFAULT NULL,
  `employee_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKmli0eqrecnnqvdgv3kcx7d9m8` (`customer_id` ASC) VISIBLE,
  INDEX `FKh33hi5t6a64q5nq9n0kwoa8to` (`department_id` ASC) VISIBLE,
  INDEX `FKa78dsu86la2dni050sh6gap19` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `FKa78dsu86la2dni050sh6gap19`
    FOREIGN KEY (`employee_id`)
    REFERENCES `ticket_management_system`.`employee` (`id`),
  CONSTRAINT `FKh33hi5t6a64q5nq9n0kwoa8to`
    FOREIGN KEY (`department_id`)
    REFERENCES `ticket_management_system`.`department` (`id`),
  CONSTRAINT `FKmli0eqrecnnqvdgv3kcx7d9m8`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ticket_management_system`.`customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ticket_management_system`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticket_management_system`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT NOW(),
  `updated_at` DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  `customer_id` INT NULL DEFAULT NULL,
  `employee_id` INT NULL DEFAULT NULL,
  `ticket_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKg2pmh6bgwe0dqobetgwdrv47d` (`customer_id` ASC) VISIBLE,
  INDEX `FKnt0c0ugqu26cuj5vyqhmrgrvi` (`employee_id` ASC) VISIBLE,
  INDEX `FKahc0e8ev24pd1cfg3twg2qyb` (`ticket_id` ASC) VISIBLE,
  CONSTRAINT `FKahc0e8ev24pd1cfg3twg2qyb`
    FOREIGN KEY (`ticket_id`)
    REFERENCES `ticket_management_system`.`ticket` (`id`),
  CONSTRAINT `FKg2pmh6bgwe0dqobetgwdrv47d`
    FOREIGN KEY (`customer_id`)
    REFERENCES `ticket_management_system`.`customer` (`id`),
  CONSTRAINT `FKnt0c0ugqu26cuj5vyqhmrgrvi`
    FOREIGN KEY (`employee_id`)
    REFERENCES `ticket_management_system`.`employee` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
