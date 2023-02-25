-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: bankdb
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientacclogs`
--

DROP TABLE IF EXISTS `clientacclogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientacclogs` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Date_Time` varchar(50) DEFAULT NULL,
  `AccountNo` varchar(16) NOT NULL,
  `Action_` varchar(50) NOT NULL,
  `Remarks_` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `AccountNo` (`AccountNo`),
  CONSTRAINT `clientacclogs_ibfk_1` FOREIGN KEY (`AccountNo`) REFERENCES `clientinfo` (`AccountNo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientacclogs`
--

LOCK TABLES `clientacclogs` WRITE;
/*!40000 ALTER TABLE `clientacclogs` DISABLE KEYS */;
INSERT INTO `clientacclogs` VALUES (1,'2020/07/15 17:23:54','1000000073993948','DEPOSITED AMOUNT OF 20000','-----'),(2,'2020/07/15 17:25:35','1000000073993948','TRANSFERRED AMOUNT OF 10000 TO 1000000044682954','-----'),(3,'2020/07/15 17:27:23','1000000073993948','WITHDRAWN AMOUNT OF 5000','-----'),(4,'2020/07/15 17:40:55','1000000073993948','DEPOSITED AMOUNT OF 5000','-----'),(5,'2020/07/22 16:14:58','1000000073993948','DEPOSITED AMOUNT OF 5000','-----'),(6,'2020/07/22 16:15:32','1000000073993948','WITHDRAWN AMOUNT OF 5000','-----'),(7,'2020/07/22 16:16:29','1000000073993948','TRANSFERRED AMOUNT OF 5000 TO 1000000044682954','-----');
/*!40000 ALTER TABLE `clientacclogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientaccstatus`
--

DROP TABLE IF EXISTS `clientaccstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientaccstatus` (
  `AccountNo` varchar(16) NOT NULL,
  `Balance` int DEFAULT NULL,
  `LogInStatus` tinyint(1) DEFAULT NULL,
  KEY `AccountNo` (`AccountNo`),
  CONSTRAINT `clientaccstatus_ibfk_1` FOREIGN KEY (`AccountNo`) REFERENCES `clientinfo` (`AccountNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientaccstatus`
--

LOCK TABLES `clientaccstatus` WRITE;
/*!40000 ALTER TABLE `clientaccstatus` DISABLE KEYS */;
INSERT INTO `clientaccstatus` VALUES ('1000000073993948',5000,0),('1000000044682954',15000,0);
/*!40000 ALTER TABLE `clientaccstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientinfo`
--

DROP TABLE IF EXISTS `clientinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientinfo` (
  `AccountNo` varchar(16) NOT NULL,
  `Client_Name` varchar(50) NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Birth_Year` int NOT NULL,
  `Address` varchar(100) NOT NULL,
  `City` varchar(20) NOT NULL,
  `State` varchar(20) NOT NULL,
  `Phone_Number` varchar(12) DEFAULT NULL,
  `PIN` int NOT NULL,
  PRIMARY KEY (`AccountNo`),
  CONSTRAINT `clientinfo_chk_1` CHECK ((`Gender` in (_utf8mb4'Male',_utf8mb4'Female',_utf8mb4'Other')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientinfo`
--

LOCK TABLES `clientinfo` WRITE;
/*!40000 ALTER TABLE `clientinfo` DISABLE KEYS */;
INSERT INTO `clientinfo` VALUES ('1000000044682954','Zeeshan Ahmed Channa','Male',2000,'abc street','Khairpur','Sindh','147258369',6905),('1000000073993948','Sagar','Male',1998,'abc','Sukkur','Sindh','123456789',1240);
/*!40000 ALTER TABLE `clientinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-30 14:46:07
