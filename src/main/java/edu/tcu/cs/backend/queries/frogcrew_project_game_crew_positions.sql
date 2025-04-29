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
-- Table structure for table `game_crew_positions`
--

DROP TABLE IF EXISTS `game_crew_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_crew_positions` (
  `game_id` bigint NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  KEY `FKebcs0s8o9l1f8i4niarh4xm77` (`game_id`),
  CONSTRAINT `FKebcs0s8o9l1f8i4niarh4xm77` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_crew_positions`
--

LOCK TABLES `game_crew_positions` WRITE;
/*!40000 ALTER TABLE `game_crew_positions` DISABLE KEYS */;
INSERT INTO `game_crew_positions` VALUES (2,'Producer'),(2,'Director'),(2,'Technical Director'),(2,'Cameras'),(2,'Replay'),(2,'Graphics'),(2,'Audio 1'),(2,'Audio 2'),(2,'Stage Manager'),(2,'Talent'),(2,'Score Bug Operator'),(3,'Producer'),(3,'Director'),(3,'Technical Director'),(3,'Cameras'),(3,'Replay'),(3,'Audio 1'),(3,'Graphics'),(3,'Stage Manager'),(4,'Producer'),(4,'Director'),(4,'Technical Director'),(4,'Cameras'),(4,'Replay'),(4,'Audio 1'),(4,'Audio 2'),(4,'Graphics'),(4,'Stage Manager'),(4,'Talent'),(5,'Producer'),(5,'Director'),(5,'Cameras'),(5,'Replay'),(5,'Audio 1'),(5,'Audio 2'),(5,'Graphics'),(5,'Stage Manager'),(5,'Stats'),(6,'Producer'),(6,'Director'),(6,'Technical Director'),(6,'Cameras'),(6,'Replay'),(6,'Audio 1'),(6,'Audio 2'),(6,'Graphics'),(6,'Stage Manager'),(6,'Talent'),(7,'Producer'),(7,'Director'),(7,'Technical Director'),(7,'Cameras'),(7,'Replay'),(7,'Audio 1'),(7,'Audio 2'),(7,'Graphics'),(8,'Producer'),(8,'Director'),(8,'Cameras'),(8,'Replay'),(8,'Audio 1'),(8,'Stage Manager'),(8,'Stats'),(9,'Producer'),(9,'Director'),(9,'Technical Director'),(9,'Cameras'),(9,'Replay'),(9,'Audio 1'),(9,'Audio 2'),(9,'Graphics'),(9,'Stage Manager'),(9,'Talent'),(10,'Producer'),(10,'Director'),(10,'Technical Director'),(10,'Cameras'),(10,'Replay'),(10,'Audio 1'),(10,'Graphics'),(10,'Stage Manager'),(11,'Producer'),(11,'Director'),(11,'Technical Director'),(11,'Cameras'),(11,'Replay'),(11,'Audio 1'),(11,'Graphics'),(11,'Stage Manager'),(11,'Talent'),(12,'Producer'),(12,'Director'),(12,'Technical Director'),(12,'Cameras'),(12,'Replay'),(12,'Audio 1'),(12,'Audio 2'),(12,'Graphics'),(13,'Producer'),(13,'Director'),(13,'Technical Director'),(13,'Cameras'),(13,'Replay'),(13,'Graphics'),(13,'Audio 1'),(13,'Stage Manager'),(13,'Talent'),(14,'Producer'),(14,'Director'),(14,'Technical Director'),(14,'Cameras'),(14,'Replay'),(14,'Audio 1'),(14,'Audio 2'),(14,'Graphics'),(14,'Stage Manager'),(15,'Producer'),(15,'Director'),(15,'Cameras'),(15,'Replay'),(15,'Audio 1'),(15,'Stage Manager'),(15,'Graphics'),(16,'Producer'),(16,'Director'),(16,'Cameras'),(16,'Replay'),(16,'Audio 1'),(16,'Graphics'),(16,'Stage Manager'),(17,'Replay'),(17,'Graphics'),(17,'Camera Operator');
/*!40000 ALTER TABLE `game_crew_positions` ENABLE KEYS */;
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
