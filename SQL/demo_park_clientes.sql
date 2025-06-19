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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modificado_por` varchar(255) DEFAULT NULL,
  `cpf` varchar(11) NOT NULL,
  `criador_por` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `data_modificacao` datetime(6) DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  `id_usuario` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7wflw78ibh162cmq12ii6ffly` (`cpf`),
  UNIQUE KEY `UKl8nfa1qkfrk958v1rx9h8anvb` (`id_usuario`),
  CONSTRAINT `FKf6u564o4dprnq7uln5gjvidp3` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'raditz@gmail.com','30570215064','raditz@gmail.com','2025-06-09 11:02:46.548052','2025-06-09 11:02:46.548052','RaditzLu ',26),(3,'teste7@gmail.com','77172549058','teste7@gmail.com','2025-06-09 16:18:08.730462','2025-06-09 16:18:08.730462','joao maria ',36),(4,'teste@gmail.com','74943660088','teste@gmail.com','2025-06-09 16:18:48.171271','2025-06-09 16:18:48.171271','maria ',30),(5,'teste2@gmail.com','09415723076','teste2@gmail.com','2025-06-09 16:19:20.087367','2025-06-09 16:19:20.087367','marcos ',31),(6,'teste3@gmail.com','48913092026','teste3@gmail.com','2025-06-09 16:19:48.055917','2025-06-09 16:19:48.055917','felipe ',32);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
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
