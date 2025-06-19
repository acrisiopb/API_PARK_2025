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
-- Table structure for table `clientes_tem_vagas`
--

DROP TABLE IF EXISTS `clientes_tem_vagas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes_tem_vagas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modificado_por` varchar(255) DEFAULT NULL,
  `cor` varchar(45) NOT NULL,
  `criador_por` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `data_entrada` datetime(6) NOT NULL,
  `data_modificacao` datetime(6) DEFAULT NULL,
  `data_saida` datetime(6) DEFAULT NULL,
  `desconto` decimal(7,2) DEFAULT NULL,
  `marca` varchar(8) NOT NULL,
  `modelo` varchar(45) NOT NULL,
  `placa` varchar(8) NOT NULL,
  `numero_recibo` varchar(15) NOT NULL,
  `valor` decimal(7,2) DEFAULT NULL,
  `id_cliente` bigint NOT NULL,
  `id_vaga` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKagpe1wwe3uc5qblayk35eob6k` (`numero_recibo`),
  KEY `FK5ntw30dh0og8dcyng5pcyixgx` (`id_cliente`),
  KEY `FKs0tf5qlrta3jj8cuqgwf0qsf2` (`id_vaga`),
  CONSTRAINT `FK5ntw30dh0og8dcyng5pcyixgx` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FKs0tf5qlrta3jj8cuqgwf0qsf2` FOREIGN KEY (`id_vaga`) REFERENCES `vagas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes_tem_vagas`
--

LOCK TABLES `clientes_tem_vagas` WRITE;
/*!40000 ALTER TABLE `clientes_tem_vagas` DISABLE KEYS */;
INSERT INTO `clientes_tem_vagas` VALUES (2,'acrisiopb@gmail.com','red','acrisiopb@gmail.com','2025-06-13 13:44:12.453688','2025-06-13 13:44:12.452692','2025-06-17 16:34:57.133598','2025-06-17 16:34:57.059642',0.00,'test','deada','AXC-1452','20250613-164412',695.25,3,2),(3,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-13 14:05:15.390274','2025-06-13 14:05:15.371283','2025-06-17 16:27:58.237596','2025-06-17 16:27:57.518049',0.00,'Leed','Black jack - v1','ACC-2025','20250613-170515',691.75,6,3),(4,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-13 14:05:37.588574','2025-06-13 14:05:37.576580','2025-06-17 16:35:28.776560','2025-06-17 16:35:28.709602',0.00,'Leed','Black jack - v1','ACC-2025','20250613-170537',691.75,6,4),(5,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-13 14:06:29.658977','2025-06-13 14:06:29.657978','2025-06-17 16:35:07.141470','2025-06-17 16:35:07.079509',0.00,'Leed','Black jack - v1','ACC-2025','20250613-170629',691.75,6,5),(6,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-16 08:24:41.636753','2025-06-16 08:24:41.355915','2025-06-17 16:35:13.027706','2025-06-17 16:35:12.990727',0.00,'Leed','Black jack - v1','ACC-2025','20250616-112441',228.00,6,6),(7,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-16 10:02:08.597170','2025-06-16 10:02:08.573183','2025-06-17 16:35:18.471004','2025-06-17 16:35:18.217174',0.00,'Leed','Black jack - v8','ACC-2025','20250616-130208',217.50,6,7),(8,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-16 10:03:35.983537','2025-06-16 10:03:35.983537','2025-06-17 16:41:11.031425','2025-06-17 16:41:10.931487',0.00,'Teste v1','jack - v8','BBG-2026','20250616-130335',217.50,6,8),(9,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-17 16:39:38.989078','2025-06-17 16:39:38.981082','2025-06-17 16:41:19.504848','2025-06-17 16:41:19.446877',0.00,'Leed','Black jack - v8','ACC-2025','20250617-193938',5.00,6,2),(10,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-17 16:39:42.659836','2025-06-17 16:39:42.658837','2025-06-17 16:41:28.173201','2025-06-17 16:41:28.129226',0.00,'Leed','Black jack - v8','ACC-2025','20250617-193942',5.00,6,3),(11,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-17 16:39:45.240681','2025-06-17 16:39:45.239680','2025-06-17 16:41:35.607808','2025-06-17 16:41:35.574826',0.00,'Leed','Black jack - v8','ACC-2025','20250617-193945',5.00,6,4),(12,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-17 16:39:47.486311','2025-06-17 16:39:47.484305','2025-06-17 16:41:42.806892','2025-06-17 16:41:42.694959',1.50,'Leed','Black jack - v8','ACC-2025','20250617-193947',5.00,6,5),(13,'acrisiopb@gmail.com','Dark','acrisiopb@gmail.com','2025-06-17 16:39:49.699607','2025-06-17 16:39:49.698608','2025-06-17 16:40:33.627606','2025-06-17 16:40:33.585633',0.00,'Leed','Black jack - v8','ACC-2025','20250617-193949',5.00,6,6);
/*!40000 ALTER TABLE `clientes_tem_vagas` ENABLE KEYS */;
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
