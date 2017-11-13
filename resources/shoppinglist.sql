-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bevasarlo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bevasarlo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bevasarlo` DEFAULT CHARACTER SET utf8mb4 ;
USE `bevasarlo` ;

-- -----------------------------------------------------
-- Table `bevasarlo`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`user` (
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `id` INT(11) NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bevasarlo`.`lista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`lista` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_lista_user_idx` (`user_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bevasarlo`.`kategoriak`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`kategoriak` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bevasarlo`.`unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`unit` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bevasarlo`.`cikkek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`cikkek` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(100) NULL,
  `kategoriak_id` INT(11) NOT NULL,
  `unit_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_cikkek_kategoriak1_idx` (`kategoriak_id` ASC),
  INDEX `fk_cikkek_unit1_idx` (`unit_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bevasarlo`.`lista_has_cikkek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bevasarlo`.`lista_has_cikkek` (
  `lista_id`  NOT NULL,
  `lista_user_id` INT(11) NOT NULL,
  `cikkek_id` INT(11) NOT NULL,
  `status` INT(11) NULL,
  `amount` INT(11) NULL,
  `lista_has_cikkekcol` VARCHAR(45) NULL,
  PRIMARY KEY (`lista_id`, `lista_user_id`, `cikkek_id`),
  INDEX `fk_lista_has_cikkek_cikkek1_idx` (`cikkek_id` ASC),
  INDEX `fk_lista_has_cikkek_lista1_idx` (`lista_id` ASC, `lista_user_id` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
