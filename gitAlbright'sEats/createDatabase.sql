DROP DATABASE IF EXISTS `restaurantdata`;
CREATE DATABASE `restaurantdata` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE restaurantdata;
CREATE TABLE `accounts` (
  `account-id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`account-id`),
  UNIQUE KEY `account-id_UNIQUE` (`account-id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

