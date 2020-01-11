-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: safe_streets_db
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coordinate`
--

DROP TABLE IF EXISTS `coordinate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coordinate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` decimal(12,10) NOT NULL,
  `longitude` decimal(12,10) NOT NULL,
  `altitude` decimal(16,10) DEFAULT '0.0000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinate`
--

LOCK TABLES `coordinate` WRITE;
/*!40000 ALTER TABLE `coordinate` DISABLE KEYS */;
INSERT INTO `coordinate` VALUES (1,45.4769300000,9.2322900000,122.0000000000),(2,45.4667800000,9.1904000000,122.0000000000),(3,45.4900000000,12.2500000000,2.5600000000),(4,10.0000000000,10.0000000000,900.0000000000),(5,3.0000000000,3.0000000000,3.0000000000),(6,4.0000000000,5.0000000000,6.0000000000),(7,7.0000000000,8.0000000000,9.0000000000),(8,4.0000000000,5.0000000000,6.0000000000),(9,7.0000000000,8.0000000000,9.0000000000),(10,4.0000000000,5.0000000000,6.0000000000),(11,7.0000000000,8.0000000000,9.0000000000),(12,4.0000000000,5.0000000000,6.0000000000),(13,7.0000000000,8.0000000000,9.0000000000),(14,4.0000000000,5.0000000000,6.0000000000),(15,7.0000000000,8.0000000000,9.0000000000),(16,4.0000000000,5.0000000000,6.0000000000),(17,7.0000000000,8.0000000000,9.0000000000),(18,4.0000000000,5.0000000000,6.0000000000),(19,7.0000000000,8.0000000000,9.0000000000),(20,1.0000000000,2.0000000000,3.0000000000),(23,4.0000000000,5.0000000000,6.0000000000),(24,7.0000000000,8.0000000000,9.0000000000),(25,4.0000000000,5.0000000000,6.0000000000),(26,7.0000000000,8.0000000000,9.0000000000),(27,4.0000000000,5.0000000000,6.0000000000),(28,7.0000000000,8.0000000000,9.0000000000),(29,4.0000000000,5.0000000000,6.0000000000),(30,7.0000000000,8.0000000000,9.0000000000),(31,4.0000000000,5.0000000000,6.0000000000),(32,7.0000000000,8.0000000000,9.0000000000),(33,4.0000000000,5.0000000000,6.0000000000),(34,7.0000000000,8.0000000000,9.0000000000),(35,7.0000000000,5.0000000000,6.0000000000),(36,6.0000000000,8.0000000000,9.0000000000),(37,45.4642040000,9.1899820000,0.0000000000),(38,45.4620350000,9.1899820000,0.0000000000),(39,45.4642040000,9.1899820000,0.0000000000),(40,45.4620350000,9.1899820000,0.0000000000),(41,45.4642035000,9.1899820000,0.0000000000),(42,45.4620350000,9.1899820000,0.0000000000),(44,45.4642035000,9.1899820000,0.0000000000),(45,45.4620350000,9.1899820000,0.0000000000),(46,45.4642035000,9.1899820000,0.0000000000),(47,45.4620350000,9.1899820000,0.0000000000),(48,4.0000000000,5.0000000000,6.0000000000),(49,7.0000000000,8.0000000000,9.0000000000),(50,45.4642035000,9.1899820000,0.0000000000),(51,45.4620350000,9.1899820000,0.0000000000),(52,1.0000000000,1.0000000000,1.0000000000),(53,1.0000000000,1.0000000000,1.0000000000),(54,4.0000000000,5.0000000000,6.0000000000),(55,7.0000000000,8.0000000000,9.0000000000),(56,45.4801297000,9.2229332000,127.0000000000),(57,45.4801297000,9.2229332000,127.0000000000),(58,1.0000000000,1.0000000000,1.0000000000),(59,1.0000000000,1.0000000000,1.0000000000),(60,4.0000000000,5.0000000000,6.0000000000),(61,7.0000000000,8.0000000000,9.0000000000),(62,45.4642035000,9.1899820000,0.0000000000),(63,45.4620350000,9.1899820000,0.0000000000),(64,45.4801297000,9.2229332000,127.0000000000),(65,45.4801297000,9.2229332000,127.0000000000),(66,45.4712450000,9.2110290000,150.0000000000),(67,45.4712450000,9.2110290000,150.0000000000),(68,45.4712450000,9.2110290000,150.0000000000),(69,45.4712450000,9.2110290000,150.0000000000),(70,45.4712450000,9.2110290000,150.0000000000),(71,45.4712450000,9.2110290000,150.0000000000),(72,45.4801297000,9.2229332000,127.0000000000),(73,45.4801297000,9.2229332000,127.0000000000),(74,4.0000000000,5.0000000000,6.0000000000),(75,7.0000000000,8.0000000000,9.0000000000),(76,45.4801297000,9.2229332000,127.0000000000),(77,45.4801297000,9.2229332000,127.0000000000),(78,45.4801297000,9.2229332000,127.0000000000),(79,45.4801297000,9.2229332000,127.0000000000),(80,45.4801297000,9.2229332000,127.0000000000),(81,45.4801297000,9.2229332000,127.0000000000),(82,45.4801297000,9.2229332000,127.0000000000),(83,45.4801297000,9.2229332000,127.0000000000),(84,45.4712450000,9.2110290000,150.0000000000),(85,4.0000000000,5.0000000000,6.0000000000),(86,7.0000000000,8.0000000000,9.0000000000),(87,4.0000000000,5.0000000000,6.0000000000),(88,7.0000000000,8.0000000000,9.0000000000),(89,4.0000000000,5.0000000000,6.0000000000),(90,7.0000000000,8.0000000000,9.0000000000),(91,45.4712450000,9.2110290000,150.0000000000),(92,45.4712450000,9.2110290000,150.0000000000),(93,4.0000000000,5.0000000000,6.0000000000),(94,7.0000000000,8.0000000000,9.0000000000),(95,45.4801297000,9.2229332000,127.0000000000),(96,45.4801297000,9.2229332000,127.0000000000),(97,45.4712450000,9.2110290000,150.0000000000);
/*!40000 ALTER TABLE `coordinate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `municipality`
--

DROP TABLE IF EXISTS `municipality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `municipality` (
  `contract_code` varchar(128) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` char(128) DEFAULT NULL,
  `pass_salt` char(16) DEFAULT NULL,
  `place_id` int(11) NOT NULL,
  PRIMARY KEY (`contract_code`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_Municipality_Place1_idx` (`place_id`),
  CONSTRAINT `fk_municipality_place1` FOREIGN KEY (`place_id`) REFERENCES `place` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `municipality`
--

LOCK TABLES `municipality` WRITE;
/*!40000 ALTER TABLE `municipality` DISABLE KEYS */;
INSERT INTO `municipality` VALUES ('14',NULL,NULL,NULL,52),('16',NULL,NULL,NULL,59),('5186b6b20e77f0ee6254dfa8d7e06dd33d3138160b77d5338c279d0ff6f48d0c432e1a7dea6aa241c6de109763090be603029b1b33226ea30c4b165945605002','Venezia','ea8451e8b66f57b84bcba312d960f5fe0b318df82acbb9f6e59bd31e34e5c343759ddb0f933afa82067d50002f87b2a80800b6dfa26471a962ecdf987f7d2f6f','J%I:jc}wz%/MQj|l',2),('7e952b0619989c96a691f727d58549c4e332975c96a3c0648694079f9f2c1cebf26e48e173b10232cb0a76744e3823a02csae396e6346','Roma','2d12b626cc5ca9018158f5816de48eb0207416c9c0eb2c07164b2876f28d0e118b2c2ed4209f5e25fcb8be48bb8bd0c13748d4f017c1b7db256ae92bdc89a233','LWf:.!4d C{up-UM',19),('7e952b0619989c96a691f727d58549c4e332975c96a3c0648694079f9f2c1cebf26e48e173b10232cb0a76744e3823a02fde0eaa11c08678517f1a6e396e6346','Milano','a09d5c018da86ba34709f629e12efc69cf441caaef43fcdd1bf51630623f3e704a0b0ea90b04a95ad09e7c2c82d5e18b5e1e792946803c01c849ed5d2f28e24d','7tkDk/X$6e4ry@k?',1);
/*!40000 ALTER TABLE `municipality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_picture`
--

DROP TABLE IF EXISTS `other_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `other_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picture` varchar(100) DEFAULT NULL,
  `user_report_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_OtherPicture_UserReport1_idx` (`user_report_id`),
  CONSTRAINT `fk_otherpicture_userreport1` FOREIGN KEY (`user_report_id`) REFERENCES `user_report` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_picture`
--

LOCK TABLES `other_picture` WRITE;
/*!40000 ALTER TABLE `other_picture` DISABLE KEYS */;
INSERT INTO `other_picture` VALUES (1,'../picturesData/park-on-sidewalk.png',1),(2,'../picturesData/ParkingOnReservedStall.png',2),(3,'../picturesData/OtherPictureFromReportOfjak40.png',13),(4,'../picturesData/OtherPictureFromReportOfjak41.png',13),(5,'../picturesData/OtherPictureFromReportOfjak40.png',14),(6,'../picturesData/OtherPictureFromReportOfjak41.png',14),(7,'../picturesData/OtherPictureFromReportOfjak40.png',15),(8,'../picturesData/OtherPictureFromReportOfjak41.png',15),(9,'../picturesData/OtherPictureFromReportOfjak40.png',16),(10,'../picturesData/OtherPictureFromReportOfjak41.png',16),(11,'../picturesData/OtherPictureFromReportOfjak40.png',17),(12,'../picturesData/OtherPictureFromReportOfjak41.png',17),(13,'../picturesData/OtherPictureFromReportOfjak40.png',18),(14,'../picturesData/OtherPictureFromReportOfjak41.png',18),(15,'../picturesData/OtherPictureFromReportOfjak40.png',19),(16,'../picturesData/OtherPictureFromReportOfjak41.png',19),(17,'../picturesData/ParkingOnReservedStall.png',4),(18,'../picturesData/park-on-sidewalk.png',1),(19,'../picturesData/OtherPictureFromReportOfjak40.png',30),(20,'../picturesData/OtherPictureFromReportOfjak41.png',30),(21,'../picturesData/OtherPictureFromReportOfjak40.png',31),(22,'../picturesData/OtherPictureFromReportOfjak41.png',31),(23,'../picturesData/OtherPictureFromReportOfjak40.png',32),(24,'../picturesData/OtherPictureFromReportOfjak41.png',32),(25,'../picturesData/OtherPictureFromReportOfjak40.png',35),(26,'../picturesData/OtherPictureFromReportOfjak41.png',35);
/*!40000 ALTER TABLE `other_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `place`
--

DROP TABLE IF EXISTS `place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(20) NOT NULL,
  `address` varchar(30) DEFAULT NULL,
  `house_code` varchar(10) DEFAULT NULL,
  `coordinate_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Place_Coordinate1_idx` (`coordinate_id`),
  CONSTRAINT `fk_place_coordinate1` FOREIGN KEY (`coordinate_id`) REFERENCES `coordinate` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place`
--

LOCK TABLES `place` WRITE;
/*!40000 ALTER TABLE `place` DISABLE KEYS */;
INSERT INTO `place` VALUES (1,'Milano',NULL,NULL,1),(2,'Venezia',NULL,NULL,2),(3,'Milano','Via Camillo Golgi','10',1),(4,'Venezia','Via Antonio Fradeletto',NULL,3),(5,'Milano','Piazza della Scala','2',2),(6,'Venezia','Via Amerigo Vespucci','4',3),(7,'Molveno',NULL,NULL,4),(8,'Molveno',NULL,NULL,4),(9,'Molveno',NULL,NULL,4),(10,'Molveno',NULL,NULL,4),(11,'Bologna','Via Avesella','12',6),(12,'Ravenna','Via Alfredo Baccarini','3',7),(13,'Bologna','Via Avesella','12',8),(14,'Ravenna','Via Alfredo Baccarini','3',9),(15,'Bologna','Via Avesella','12',10),(16,'Ravenna','Via Alfredo Baccarini','3',11),(17,'Bologna','Via Avesella','12',12),(18,'Ravenna','Via Alfredo Baccarini','3',13),(19,'Roma',NULL,NULL,NULL),(20,'Bologna','Via Avesella','12',14),(21,'Ravenna','Via Alfredo Baccarini','3',15),(22,'Bologna','Via Avesella','12',16),(23,'Ravenna','Via Alfredo Baccarini','3',17),(24,'Bologna','Via Avesella','12',18),(25,'Ravenna','Via Alfredo Baccarini','3',19),(28,'Bologna','Via Avesella','12',23),(29,'Ravenna','Via Alfredo Baccarini','3',24),(30,'Bologna','Via Avesella','12',25),(31,'Ravenna','Via Alfredo Baccarini','3',26),(32,'Bologna','Via Avesella','12',27),(33,'Ravenna','Via Alfredo Baccarini','3',28),(34,'Bologna','Via Avesella','12',29),(35,'Ravenna','Via Alfredo Baccarini','3',30),(36,'Bologna','Via Avesella','12',31),(37,'Ravenna','Via Alfredo Baccarini','3',32),(38,'Bologna','Via Avesella','12',33),(39,'Ravenna','Via Alfredo Baccarini','3',34),(40,'Bologna','Via Avesella','12',35),(41,'Ravenna','Via Alfredo Baccarini','3',36),(42,'Milano','Via Aristide de Togni','12',37),(43,'Milano','Via Aristide de Togni','12',38),(44,'Milano','Via Aristide de Togni','12',39),(45,'Milano','Via Aristide de Togni','12',40),(46,'Milano','Via Aristide de Togni','12',41),(47,'Milano','Via Aristide de Togni','12',42),(48,'Milano','Via Aristide de Togni','12',44),(49,'Milano','Via Aristide de Togni','12',45),(50,'Milano','Via Aristide de Togni','12',46),(51,'Milano','Via Aristide de Togni','12',47),(52,'Firenze',NULL,NULL,NULL),(53,'Bologna','Via Avesella','12',48),(54,'Ravenna','Via Alfredo Baccarini','3',49),(55,'Milano','Via Aristide de Togni','12',50),(56,'Milano','Via Aristide de Togni','12',51),(57,'Milano',NULL,NULL,52),(58,'Milano',NULL,NULL,53),(59,'Torino',NULL,NULL,NULL),(60,'Bologna','Via Avesella','12',54),(61,'Ravenna','Via Alfredo Baccarini','3',55),(62,'Milano','Piazzale Gabrio Piola',NULL,56),(63,'Milano','Piazzale Gabrio Piola',NULL,57),(64,'Milano',NULL,NULL,59),(65,'Bologna','Via Avesella','12',60),(66,'Ravenna','Via Alfredo Baccarini','3',61),(67,'Milano','Via Aristide de Togni','12',62),(68,'Milano','Via Aristide de Togni','12',63),(69,'Milano','Piazzale Gabrio Piola',NULL,64),(70,'Milano','Piazzale Gabrio Piola',NULL,65),(71,'Milano','Via Carlo Pisacane',NULL,66),(72,'Milano','Via Carlo Pisacane',NULL,66),(73,'Milano','Via Carlo Pisacane',NULL,66),(74,'Milano','Via Carlo Pisacane',NULL,66),(75,'Milano','Via Carlo Pisacane',NULL,66),(76,'Milano','Via Carlo Pisacane',NULL,66),(77,'Milano','Via Carlo Pisacane',NULL,67),(78,'Milano','Via Carlo Pisacane',NULL,68),(79,'Milano','Via Carlo Pisacane',NULL,69),(80,'Milano','Via Carlo Pisacane',NULL,70),(81,'Milano','Via Carlo Pisacane',NULL,71),(82,'Milano','Piazzale Gabrio Piola',NULL,72),(83,'Milano','Piazzale Gabrio Piola',NULL,73),(84,'Bologna','Via Avesella','12',74),(85,'Ravenna','Via Alfredo Baccarini','3',75),(86,'Milano','Piazzale Gabrio Piola',NULL,76),(87,'Milano','Piazzale Gabrio Piola',NULL,77),(88,'Milano','Piazzale Gabrio Piola',NULL,78),(89,'Milano','Piazzale Gabrio Piola',NULL,79),(90,'Milano','Piazzale Gabrio Piola',NULL,80),(91,'Milano','Piazzale Gabrio Piola',NULL,81),(92,'Milano','Piazzale Gabrio Piola',NULL,82),(93,'Milano','Piazzale Gabrio Piola',NULL,83),(94,'Milano','Via Carlo Pisacane',NULL,84),(95,'Bologna','Via Avesella','12',85),(96,'Ravenna','Via Alfredo Baccarini','3',86),(97,'Bologna','Via Avesella','12',87),(98,'Ravenna','Via Alfredo Baccarini','3',88),(99,'Bologna','Via Avesella','12',89),(100,'Ravenna','Via Alfredo Baccarini','3',90),(101,'Milano','Via Carlo Pisacane',NULL,91),(102,'Milano','Via Carlo Pisacane',NULL,92),(103,'Bologna','Via Avesella','12',93),(104,'Ravenna','Via Alfredo Baccarini','3',94),(105,'Milano','Piazzale Gabrio Piola',NULL,95),(106,'Milano','Piazzale Gabrio Piola',NULL,96),(107,'Milano','Via Carlo Pisacane',NULL,97);
/*!40000 ALTER TABLE `place` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `place_of_birth_id` int(11) NOT NULL,
  `place_of_residence_id` int(11) NOT NULL,
  `picture` varchar(100) DEFAULT NULL,
  `id_card` varchar(100) NOT NULL,
  `fiscal_code` varchar(30) NOT NULL,
  `date_of_birth` date NOT NULL,
  `password` char(128) NOT NULL,
  `salt` char(16) NOT NULL,
  `date_of_registration` date NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_User_Place1_idx` (`place_of_birth_id`),
  KEY `fk_User_Place2_idx` (`place_of_residence_id`),
  CONSTRAINT `fk_user_place1` FOREIGN KEY (`place_of_birth_id`) REFERENCES `place` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_place2` FOREIGN KEY (`place_of_residence_id`) REFERENCES `place` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('jak4','jak@gmail.com','Jak','Red',1,5,'../picturesData/bob45Picture.png','../picturesData/bob45IdCard.png','RDEJKA80A01F205W','1983-01-01','6c09e5eb7de868abc5b4a036ce126915d234e582bcb69f4996442d5876d4f4641d1357bb60db189eaa5b023160bd796184da7c34353e05acc61da697dcbb1c35','\":xa?(Cc7o]t5f6$','2019-11-02'),('Justin76','justin2@hotmail.it','Justin','See',2,6,'../picturesData/bob45Picture.png','../picturesData/bob45IdCard.png','SEEJTN83A01L736A','1980-01-01','7d885571378195102c08f5f862bdfdeb68bc5f95a353143fd9d9d0ad5878c4a5b2d4e066514565e4c90ace6e21a374aa102113da25a8ed3320d4f2c2cb1bc987','M#T\\3|Yzoja`sIu(','2019-10-11'),('smith40','smith40@m.com','Tim','Smith',1,5,'../picturesData/bob45Picture.png','../picturesData/bob45IdCard.png','SMTTMI90M01F205P','1990-08-01','2aab10118bd7c4b651f34d310b72cc2d05f8f49b82566fe74c1f3d5262e42e832bee5a1560458f23b43ec369305d57c45cde5910916b78a2175e330548fcf690','M#c\\3|Yzoja`sIu(','2019-12-11');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_report`
--

DROP TABLE IF EXISTS `user_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_time_stamp` datetime NOT NULL,
  `time_stamp_of_watched_violation` datetime DEFAULT NULL,
  `violation_type` varchar(35) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `main_picture` varchar(100) NOT NULL,
  `place_id` int(11) NOT NULL,
  `vehicle_license_plate` varchar(15) DEFAULT NULL,
  `user` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_UserReport_User1_idx` (`user`),
  KEY `fk_UserReport_Place_idx` (`place_id`),
  KEY `fk_vehicle_license_plate_idx` (`vehicle_license_plate`),
  CONSTRAINT `fk_user_report_place` FOREIGN KEY (`place_id`) REFERENCES `place` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_report_user1` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `fk_vehicle_license_plate` FOREIGN KEY (`vehicle_license_plate`) REFERENCES `vehicle` (`license_plate`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_report`
--

LOCK TABLES `user_report` WRITE;
/*!40000 ALTER TABLE `user_report` DISABLE KEYS */;
INSERT INTO `user_report` VALUES (1,'2019-11-12 15:50:00',NULL,'PARKING_ON_SIDEWALK',NULL,'../picturesData/park-on-sidewalk.png',3,'FF456ZZ','jak4'),(2,'2019-12-15 15:50:00','2019-12-15 15:20:00','PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall.png',6,'DS473OP','Justin76'),(3,'2019-11-25 17:50:00',NULL,'PARKING_ON_SIDEWALK',NULL,'../picturesData/park-on-sidewalk2.png',3,'FF456ZZ','Justin76'),(4,'2019-10-12 17:50:00',NULL,'PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall2.png',5,'DS473OP','Justin76'),(5,'2020-01-04 08:30:30',NULL,'PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall2.png',62,'12dvg47','jak4'),(6,'2020-01-04 08:30:30',NULL,'PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall2.png',63,'12dvg47','jak4'),(7,'2020-01-04 08:30:30',NULL,'PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall2.png',69,'12dvg47','jak4'),(8,'2020-01-04 08:30:30',NULL,'PARKING_ON_RESERVED_STALL',NULL,'../picturesData/ParkingOnReservedStall2.png',70,'12dvg47','jak4'),(13,'2020-01-04 08:30:30',NULL,'PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',75,'fb452rt','jak4'),(14,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',76,'fb452rt','jak4'),(15,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',77,'fb452rt','jak4'),(16,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',78,'fb452rt','jak4'),(17,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',79,'fb452rt','jak4'),(18,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',80,'fb452rt','jak4'),(19,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',81,'fb452rt','jak4'),(30,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',94,'fb452rt','jak4'),(31,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',101,'fb452rt','jak4'),(32,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',102,'fb452rt','jak4'),(35,'2020-01-04 08:30:30','2020-01-04 08:50:30','PARKING_ON_RESERVED_STALL','','../picturesData/MainPictureFromReportOfjak4.png',107,'fb452rt','jak4');
/*!40000 ALTER TABLE `user_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicle` (
  `license_plate` varchar(15) NOT NULL,
  PRIMARY KEY (`license_plate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES ('12dvg45'),('12dvg46'),('12dvg47'),('12dvg49'),('12dvg50'),('AI538FG'),('AP234IJ'),('BS884HD'),('DS473OP'),('fb452rt'),('FF456ZZ'),('FR342FB');
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-11 10:57:35
