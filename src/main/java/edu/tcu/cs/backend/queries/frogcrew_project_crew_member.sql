-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: localhost    Database: frogcrew_project
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `crew_member`
--

DROP TABLE IF EXISTS `crew_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crew_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `qualified_position` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crew_member`
--

LOCK TABLES `crew_member` WRITE;
/*!40000 ALTER TABLE `crew_member` DISABLE KEYS */;
INSERT INTO `crew_member` VALUES (19,'jaxon.woods@example.com','Jaxon','Woods','password123','214-555-1010','Replay','Crew Member'),(20,'ellie.rivera@example.com','Ellie','Rivera','password123','682-555-2020','Cameras','Crew Member'),(21,'liam.foster@example.com','Liam','Foster','password123','817-555-3030','Graphics','Crew Member'),(22,'sophia.brooks@example.com','Sophia','Brooks','password123','972-555-4040','Audio 1','Crew Member'),(23,'mason.hayes@example.com','Mason','Hayes','password123','469-555-5050','Stage Manager','Crew Member'),(24,'ava.sanders@example.com','Ava','Sanders','password123','806-555-6060','Director','Crew Member'),(25,'noah.cruz@example.com','Noah','Cruz','password123','430-555-7070','Score Bug Operator','Crew Member'),(26,'mia.young@example.com','Mia','Young','password123','737-555-8080','Audio 2','Crew Member'),(27,'riley.morgan@example.com','Riley','Morgan','TestPass123','214-555-0187','Paid Freelancer','Camera Operator'),(28,'avacarter@email.com','Ava','Carter','password123','123-456-7890','Sport Broadcaster','Camera Operator'),(29,'avery.sullivan@example.com','Avery','Sullivan','Password123!','123-456-7890','Paid Freelancer','Camera Operator'),(30,'avery.sullivan@example.com','Avery','Sullivan','Password123!','123-456-7890','Paid Freelancer','Camera Operator'),(31,'avery.sullivan@example.com','Avery','Sullivan','Password123!','123-456-7890','Paid Freelancer','Camera Operator'),(32,'avery.sullivan@example.com','Avery','Sullivan','Password123!','123-456-7890','Sport Broadcaster','Camera Operator'),(33,'jordan.rivers@example.com','Jordan','Rivers',' SoccerFan2025!','123-456-7890','Paid Student','Camera'),(34,'admin@example.com','Admin','User','admin123','123-456-7890','Admin','admin'),(35,'harper.walker@example.com','Harper','Walker','Crew1234','214-555-7890','Paid Student','Graphics');
/*!40000 ALTER TABLE `crew_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-28 23:46:04
