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

CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `restaurant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  KEY `restaurant_id_idx` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Restaurant` (
  `restaurant_id` varchar(255) NOT NULL,
  `restaurant_name` varchar(255) DEFAULT NULL,
  `details_id` int DEFAULT NULL,
  `rating_id` int DEFAULT NULL,
  PRIMARY KEY (`restaurant_id`),
  UNIQUE KEY `restaurant_id_UNIQUE` (`restaurant_id`),
  UNIQUE KEY `restaurant_name_UNIQUE` (`restaurant_name`),
  KEY `details_id_idx` (`details_id`),
  KEY `rating_id_idx` (`rating_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Rating_details` (
  `rating_id` int NOT NULL,
  `review_count` int DEFAULT NULL,
  `rating` double DEFAULT NULL,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `rating_id_UNIQUE` (`rating_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Restaurant_details` (
  `details_id` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `estimated_price` int DEFAULT NULL,
  `yelp_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`details_id`),
  UNIQUE KEY `details_id_UNIQUE` (`details_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
