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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modificado_por` varchar(255) DEFAULT NULL,
  `criador_por` varchar(255) DEFAULT NULL,
  `data_criacao` datetime(6) DEFAULT NULL,
  `data_modificacao` datetime(6) DEFAULT NULL,
  `password` varchar(200) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_CLIENTE') NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (26,'raditz@gmail.com','anonymousUser','2025-06-05 12:53:58.544275','2025-06-05 13:00:01.193090','$2a$10$bxyUsFJtaiRAtKdbbrUU4e88F0YUlT2wB.DNKhwakAzq3U045QLUS','ROLE_CLIENTE','raditz@gmail.com'),(27,'acrisiopb@gmail.com','anonymousUser','2025-06-05 13:58:02.510777','2025-06-05 15:39:17.527099','$2a$10$m051TBcQKnKr9ZDjUilwDOzyAFskb83wIwmJUYoXj2xPggUtJpWmW','ROLE_ADMIN','acrisiopb@gmail.com'),(29,'anonymousUser','anonymousUser','2025-06-09 16:15:12.369459','2025-06-09 16:15:12.369459','$2a$10$DkUPfsS5PUMXrZYcPgzOS.5QNbijEH7sCL2HFjqLhMzuSaYna8/nu','ROLE_CLIENTE','luffy@gmail.com'),(30,'anonymousUser','anonymousUser','2025-06-09 16:15:18.084303','2025-06-09 16:15:18.084303','$2a$10$c0aVmrasI4AVte2.MQlyhucKP72vAn26Ye1WZHcyPbBr/JYJFd3Vm','ROLE_CLIENTE','teste@gmail.com'),(31,'anonymousUser','anonymousUser','2025-06-09 16:15:24.303336','2025-06-09 16:15:24.303336','$2a$10$cxJg/1fjaZXZ4F2I1gh/Xezo4YD9bmzTblpKcNzgWCAG3CREY5nZu','ROLE_CLIENTE','teste2@gmail.com'),(32,'anonymousUser','anonymousUser','2025-06-09 16:15:27.551706','2025-06-09 16:15:27.551706','$2a$10$cdO1Qyu3Kvo4B8Xa3paqTepCxypaGU1Wun4Na5I76jn834Hw4CiQi','ROLE_CLIENTE','teste3@gmail.com'),(33,'anonymousUser','anonymousUser','2025-06-09 16:15:30.644727','2025-06-09 16:15:30.644727','$2a$10$IAbImSC/lCvi7yq.U8AkwOaHMZsACnnFUxxIC5pALNKAk//.JqTdS','ROLE_CLIENTE','teste4@gmail.com'),(34,'anonymousUser','anonymousUser','2025-06-09 16:15:33.470218','2025-06-09 16:15:33.470218','$2a$10$8xn8JI.rbR4NgPgeCwHw0u9BSui9swIe3s.JFu2Ni9HCRwg0E2bOu','ROLE_CLIENTE','teste5@gmail.com'),(35,'anonymousUser','anonymousUser','2025-06-09 16:15:37.457704','2025-06-09 16:15:37.457704','$2a$10$BcWYHx7MIbh1O1rE2HQo7OImEmH5oz/yclYCxDP.bHrLU6r0XT11m','ROLE_CLIENTE','teste6@gmail.com'),(36,'anonymousUser','anonymousUser','2025-06-09 16:15:40.719589','2025-06-09 16:15:40.719589','$2a$10$TxT8ytf8FVrN8cADsQKBcO7CX9tDqkckcZnwOCydICWjJI276Rrmq','ROLE_CLIENTE','teste7@gmail.com'),(42,'teste5@gmail.com','teste5@gmail.com','2025-06-09 16:23:15.523857','2025-06-09 16:23:15.523857','$2a$10$OJP7JWH1SbnbzoFobin6JOFmeLI76X5nh1j14oYjs8TTYsLgw/mnO','ROLE_CLIENTE','teste8@gmail.com');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
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
