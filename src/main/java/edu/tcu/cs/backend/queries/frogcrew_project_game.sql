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
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `opponent` varchar(255) DEFAULT NULL,
  `sport` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `venue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (2,'2025-09-07','University of Texas','Football','7:30 PM','Amon G. Carter Stadium'),(3,'2025-10-15','SMU','Volleyball','5:30 PM','Schollmaier Arena'),(4,'2025-12-01','Baylor University','Basketball','6:00 PM','Schollmaier Arena'),(5,'2025-08-20','University of Oklahoma','Soccer','7:00 PM','Garvey-Rosenthal Soccer Stadium'),(6,'2025-08-19','University of Texas','Football','7:00 PM','DKR-Texas Memorial Stadium'),(7,'2026-01-27','Baylor University','Basketball','6:30 PM','Ferrell Center'),(8,'2025-09-10','University of Oklahoma','Soccer','6:00 PM','OU Soccer Complex'),(9,'2026-04-15','Texas A&M University','Baseball','6:30 PM','Lupton Baseball Stadium'),(10,'2026-05-02','Texas A&M University','Baseball','7:00 PM','Blue Bell Park'),(11,'2025-09-14','Oklahoma State University','Football','7:00 PM','Amon G. Carter Stadium'),(12,'2025-09-28','Kansas State University','Football','6:30 PM','Bill Snyder Family Stadium'),(13,'2025-01-18','University of Houston','Basketball','7:00 PM','Schollmaier Arena'),(14,'2025-02-05','University of Kansas','Basketball','6:00 PM','Allen Fieldhouse'),(15,'2025-09-12','Brigham Young University','Soccer','7:00 PM','Garvey-Rosenthal Soccer Stadium'),(16,'2025-09-26','West Virginia University','Soccer','6:00 PM','Dick Dlesk Soccer Stadium'),(17,NULL,'University of Texas','Football',NULL,'TCU Stadium');
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
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
