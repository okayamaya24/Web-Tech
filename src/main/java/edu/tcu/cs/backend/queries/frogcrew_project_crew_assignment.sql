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
-- Table structure for table `crew_assignment`
--

DROP TABLE IF EXISTS `crew_assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crew_assignment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `position` varchar(255) DEFAULT NULL,
  `report_location` varchar(255) DEFAULT NULL,
  `report_time` datetime(6) DEFAULT NULL,
  `crew_member_id` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd1krrm12m6adojklkh47n2e4n` (`crew_member_id`),
  KEY `FKbbee17sjyounrq76cn2a6b7t7` (`game_id`),
  CONSTRAINT `FKbbee17sjyounrq76cn2a6b7t7` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
  CONSTRAINT `FKd1krrm12m6adojklkh47n2e4n` FOREIGN KEY (`crew_member_id`) REFERENCES `crew_member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crew_assignment`
--

LOCK TABLES `crew_assignment` WRITE;
/*!40000 ALTER TABLE `crew_assignment` DISABLE KEYS */;
INSERT INTO `crew_assignment` VALUES (19,'Producer','Stadium Control Room','2025-09-15 18:00:00.000000',NULL,2,'Jaxon Woods'),(20,'Director','Control Truck','2025-09-15 17:45:00.000000',NULL,3,'Ella Johnson'),(21,'Technical Director','Tech Room','2025-09-15 17:30:00.000000',NULL,4,'Noah Kim'),(22,'Producer','Stadium Control Room','2025-09-07 18:00:00.000000',NULL,2,'Jaxon Woods'),(23,'Producer','Stadium Control Room','2025-09-06 17:00:00.000000',NULL,6,'Jaxon Woods'),(24,'Producer','Control Room','2025-09-06 18:00:00.000000',NULL,2,'Jaxon Woods'),(25,'Producer','Control Room','2025-09-06 18:00:00.000000',NULL,6,'Jaxon Woods'),(26,'Producer','Volleyball Control Room','2025-10-14 16:00:00.000000',NULL,3,'Sophia Martin'),(27,'Camera','Field Side','2025-09-06 18:00:00.000000',NULL,2,'Amelia Reed'),(28,'Replay','Replay Room','2025-09-06 18:00:00.000000',NULL,2,'Liam Taylor'),(29,'Producer','Control Room','2025-10-14 17:00:00.000000',NULL,3,'Sophia Martin'),(30,'Camera','Court Side','2025-10-14 17:00:00.000000',NULL,3,'Mason Hill'),(31,'Replay','Replay Station','2025-10-14 17:00:00.000000',NULL,3,'Isabella Clark'),(32,'Producer','Control Booth','2025-11-30 17:00:00.000000',NULL,4,'Ethan Turner'),(33,'Camera','Baseline','2025-11-30 17:00:00.000000',NULL,4,'Olivia Scott'),(34,'Replay','Replay Room','2025-11-30 17:00:00.000000',NULL,4,'Lucas Adams'),(35,'Producer','Control Room','2025-08-19 18:00:00.000000',NULL,5,'Ava Mitchell'),(36,'Camera','Field Side','2025-08-19 18:00:00.000000',NULL,5,'Logan Bennett'),(37,'Replay','Replay Room','2025-08-19 18:00:00.000000',NULL,5,'Chloe Perez'),(38,'Producer','Arena Booth','2026-01-26 17:00:00.000000',NULL,7,'Mila Foster'),(39,'Camera','Center Court','2026-01-26 17:00:00.000000',NULL,7,'Henry Ramirez'),(40,'Replay','Replay Control','2026-01-26 17:00:00.000000',NULL,7,'Zoe Morgan'),(41,'Producer','Production Tent','2025-09-09 18:00:00.000000',NULL,8,'Benjamin Rivera'),(42,'Camera','Goal Line','2025-09-09 18:00:00.000000',NULL,8,'Aria James'),(43,'Replay','Replay Trailer','2025-09-09 18:00:00.000000',NULL,8,'David Gonzales'),(44,'Producer','Press Box','2026-04-14 16:00:00.000000',NULL,9,'Harper Long'),(45,'Camera','First Base Line','2026-04-14 16:00:00.000000',NULL,9,'Jackson Flores'),(46,'Replay','Media Room','2026-04-14 16:00:00.000000',NULL,9,'Layla Price'),(47,'Replay','Media Room','2026-04-14 16:00:00.000000',NULL,8,'Layla Price'),(48,'Replay','Media Room','2026-04-14 16:00:00.000000',NULL,7,'Layla Price'),(49,'Replay','Media Room','2026-04-14 16:00:00.000000',NULL,14,'Layla Price'),(50,'Producer','Control Room','2025-08-19 18:00:00.000000',NULL,10,'Ava Mitchell'),(51,'Camera','Field Side','2025-08-19 18:00:00.000000',NULL,10,'Logan Bennett'),(52,'Replay','Replay Room','2025-08-19 18:00:00.000000',NULL,10,'Chloe Perez'),(53,'Producer','Broadcast Truck','2025-08-18 18:00:00.000000',NULL,11,'Noah Hayes'),(54,'Camera','Sideline','2025-08-18 18:00:00.000000',NULL,11,'Ella Cooper'),(55,'Replay','Truck Bay','2025-08-18 18:00:00.000000',NULL,11,'James Brooks'),(56,'Producer','Arena Booth','2026-01-26 17:00:00.000000',NULL,12,'Mila Foster'),(57,'Camera','Center Court','2026-01-26 17:00:00.000000',NULL,12,'Henry Ramirez'),(58,'Replay','Replay Control','2026-01-26 17:00:00.000000',NULL,12,'Zoe Morgan'),(59,'Producer','Production Tent','2025-09-09 18:00:00.000000',NULL,13,'Benjamin Rivera'),(60,'Camera','Goal Line','2025-09-09 18:00:00.000000',NULL,13,'Aria James'),(61,'Replay','Replay Trailer','2025-09-09 18:00:00.000000',NULL,13,'David Gonzales'),(62,'Producer','Press Box','2026-04-14 16:00:00.000000',NULL,14,'Harper Long'),(63,'Camera','First Base Line','2026-04-14 16:00:00.000000',NULL,14,'Jackson Flores'),(64,'Replay','Media Room','2026-04-14 16:00:00.000000',NULL,15,'Layla Price'),(65,'Replay','Replay Trailer','2025-09-09 18:00:00.000000',NULL,16,'David Gonzales'),(66,'Replay','Replay Control','2026-01-26 17:00:00.000000',NULL,16,'Zoe Morgan');
/*!40000 ALTER TABLE `crew_assignment` ENABLE KEYS */;
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
