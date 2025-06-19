-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demo_park
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `vagas`
--

DROP TABLE IF EXISTS `vagas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vagas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modificado_por` varchar(255) DEFAULT NULL,
  `codigo` varchar(4) NOT NULL,
  `criador_por` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `data_modificacao` datetime(6) DEFAULT NULL,
  `status` enum('LIVRE','OCUPADA') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK54hpxc5myuvl930vp05tvrd14` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vagas`
--

LOCK TABLES `vagas` WRITE;
/*!40000 ALTER TABLE `vagas` DISABLE KEYS */;
INSERT INTO `vagas` VALUES (1,'acrisiopb@gmail.com','A-01','acrisiopb@gmail.com','2025-06-11 11:10:30.953836','2025-06-13 13:20:12.108591','OCUPADA'),(2,'acrisiopb@gmail.com','A-02','acrisiopb@gmail.com','2025-06-11 12:27:09.314270','2025-06-17 16:41:19.504848','LIVRE'),(3,'acrisiopb@gmail.com','A-03','acrisiopb@gmail.com','2025-06-11 12:27:09.314270','2025-06-17 16:41:28.172201','LIVRE'),(4,'acrisiopb@gmail.com','A-04','acrisiopb@gmail.com','2025-06-11 12:27:09.314270','2025-06-17 16:41:35.607808','LIVRE'),(5,'acrisiopb@gmail.com','A-05','acrisiopb@gmail.com','2025-06-11 12:27:09.314270','2025-06-17 16:41:42.806892','LIVRE'),(6,'acrisiopb@gmail.com','A-06','acrisiopb@gmail.com','2025-06-11 12:27:09.314270','2025-06-17 16:40:33.627606','LIVRE'),(7,'acrisiopb@gmail.com','A-09','acrisiopb@gmail.com','2025-06-16 10:01:31.388668','2025-06-17 16:35:18.470004','LIVRE'),(8,'acrisiopb@gmail.com','A-10','acrisiopb@gmail.com','2025-06-16 10:01:37.106898','2025-06-17 16:41:11.028426','LIVRE'),(9,'acrisiopb@gmail.com','A-11','acrisiopb@gmail.com','2025-06-16 10:01:40.359693','2025-06-16 10:01:40.359693','LIVRE'),(10,'acrisiopb@gmail.com','A-12','acrisiopb@gmail.com','2025-06-16 10:01:43.599101','2025-06-16 10:01:43.599101','LIVRE'),(11,'acrisiopb@gmail.com','A-13','acrisiopb@gmail.com','2025-06-16 10:01:47.448869','2025-06-16 10:01:47.448869','LIVRE'),(12,'acrisiopb@gmail.com','A-14','acrisiopb@gmail.com','2025-06-16 10:01:51.518322','2025-06-16 10:01:51.518322','LIVRE'),(13,'acrisiopb@gmail.com','A-15','acrisiopb@gmail.com','2025-06-16 10:01:54.465243','2025-06-16 10:01:54.465243','LIVRE'),(14,'acrisiopb@gmail.com','A-16','acrisiopb@gmail.com','2025-06-16 10:01:58.807447','2025-06-16 10:01:58.807447','LIVRE'),(15,'acrisiopb@gmail.com','A-17','acrisiopb@gmail.com','2025-06-16 10:02:01.911162','2025-06-16 10:02:01.911162','LIVRE'),(16,'acrisiopb@gmail.com','A-18','acrisiopb@gmail.com','2025-06-16 10:02:04.889851','2025-06-16 10:02:04.889851','LIVRE');
/*!40000 ALTER TABLE `vagas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19  8:57:00
