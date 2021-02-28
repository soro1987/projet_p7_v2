-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: p7
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `exemplaire`
--

DROP TABLE IF EXISTS `exemplaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exemplaire` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `disponible` bit(1) NOT NULL,
  `user` tinyblob,
  `bibliotheque` bigint DEFAULT NULL,
  `emprunt_id` bigint DEFAULT NULL,
  `ouvrage` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKla0sfkxk2dqlm9nnpdg0rqquj` (`bibliotheque`),
  KEY `FKr1fr4mcyegj8inu8oiua1us4h` (`emprunt_id`),
  KEY `FKcmw0d2pqgbol9dxrbkv27numo` (`ouvrage`)
) ENGINE=MyISAM AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exemplaire`
--

LOCK TABLES `exemplaire` WRITE;
/*!40000 ALTER TABLE `exemplaire` DISABLE KEYS */;
INSERT INTO `exemplaire` VALUES (20,_binary '\0',NULL,1,14,1),(21,_binary '',NULL,1,NULL,1),(22,_binary '',NULL,1,NULL,1),(23,_binary '',NULL,1,NULL,1),(24,_binary '',NULL,2,NULL,1),(25,_binary '',NULL,2,NULL,1),(26,_binary '',NULL,2,NULL,1),(27,_binary '',NULL,3,NULL,1),(28,_binary '',NULL,3,NULL,1),(29,_binary '',NULL,3,NULL,1),(30,_binary '',NULL,3,NULL,1),(31,_binary '',NULL,3,NULL,2),(32,_binary '',NULL,3,NULL,2),(33,_binary '',NULL,3,NULL,2),(34,_binary '',NULL,3,NULL,2),(35,_binary '',NULL,2,NULL,2),(36,_binary '',NULL,2,NULL,2),(37,_binary '',NULL,2,NULL,2),(38,_binary '',NULL,2,NULL,2),(39,_binary '',NULL,1,NULL,2),(40,_binary '',NULL,1,NULL,3),(41,_binary '',NULL,1,NULL,3),(42,_binary '',NULL,2,NULL,3),(43,_binary '',NULL,2,NULL,3),(44,_binary '',NULL,2,NULL,4),(45,_binary '',NULL,2,NULL,4),(46,_binary '',NULL,3,NULL,4),(47,_binary '',NULL,3,NULL,5),(48,_binary '',NULL,3,NULL,5),(49,_binary '',NULL,1,NULL,5),(50,_binary '',NULL,1,NULL,6),(51,_binary '',NULL,1,NULL,6),(52,_binary '',NULL,2,NULL,6),(53,_binary '',NULL,3,NULL,6),(54,_binary '',NULL,3,NULL,7),(55,_binary '',NULL,2,NULL,7),(56,_binary '',NULL,2,NULL,8),(57,_binary '',NULL,2,NULL,8),(58,_binary '',NULL,2,NULL,8),(59,_binary '',NULL,2,NULL,9),(60,_binary '',NULL,3,NULL,9),(61,_binary '',NULL,3,NULL,9),(62,_binary '',NULL,3,NULL,10),(63,_binary '',NULL,3,NULL,11),(64,_binary '',NULL,1,NULL,11),(65,_binary '',NULL,1,NULL,11),(66,_binary '',NULL,1,NULL,12);
/*!40000 ALTER TABLE `exemplaire` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-30 18:36:16
