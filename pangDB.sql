CREATE DATABASE  IF NOT EXISTS `pang_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pang_db`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pang_db
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.16-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `registrytype`
--

DROP TABLE IF EXISTS `registrytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registrytype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `shortname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrytype`
--

LOCK TABLES `registrytype` WRITE;
/*!40000 ALTER TABLE `registrytype` DISABLE KEYS */;
INSERT INTO `registrytype` VALUES (1,'1','1');
/*!40000 ALTER TABLE `registrytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `registryType_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK4a71pvnb5xgyhel7kpyod7lh7` (`registryType_id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FK4a71pvnb5xgyhel7kpyod7lh7` FOREIGN KEY (`registryType_id`) REFERENCES `registrytype` (`id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'1','k@k.k','k','k','k','123',1,1),(3,'2','t@t.t','t','t','t','321',1,1),(4,'3','g@g.g','g','g','g','312',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userconnection`
--

DROP TABLE IF EXISTS `userconnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userconnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(512) NOT NULL,
  `secret` varchar(512) DEFAULT NULL,
  `refreshToken` varchar(512) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userconnection`
--

LOCK TABLES `userconnection` WRITE;
/*!40000 ALTER TABLE `userconnection` DISABLE KEYS */;
INSERT INTO `userconnection` VALUES ('208087f4-b169-4aa6-940a-02195291b1be','facebook','916217945189772',1,'Makli Tahiri','https://www.facebook.com/app_scoped_user_id/916217945189772/','https://graph.facebook.com/v2.5/916217945189772/picture','EAAFapMy0fH4BANnANGRIDEyuomuu9jd0n2o0TWyQ92uiAA3CWaAhHh3GJoNGjqPjO12XAOZBbEP6QBZB3s7NQSo8AiPeJhE05Bptb5zd4cHiJYLIB7Rmrfvrw2V6TAKcHXSUjPDpLjIzPT4DR9Ru38SMnrPt0ZD',NULL,NULL,1485501900449),('de057453-ab86-4d27-925c-6232ec9e48c6','facebook','10209877683012212',1,'Bariampas Thomas','https://www.facebook.com/app_scoped_user_id/10209877683012212/','https://graph.facebook.com/v2.5/10209877683012212/picture','EAAFapMy0fH4BAGEZBR0rI1NDXsFt33qTpZCaAJWLPPkhHHXO8ArQNZAt8gTUPLXLc9wcGvek8mf6wfcMwIZATFRvepUQrK1G8no7CAzDU9ZC6uDZApOFsZCuFFCDUG9KxAb5cagkvlzn4AQdTkeq1ufdcqyZBuleHtcZD',NULL,NULL,1485263000678);
/*!40000 ALTER TABLE `userconnection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'pang_db'
--

--
-- Dumping routines for database 'pang_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `TopUsers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `TopUsers`(	IN IN_UserId 	INT(11),
														IN IN_HowMany 	INT(11),
                                                        IN IN_AppId 	INT(11),
                                                        IN IN_AppLevel	INT(11))
BEGIN
	DECLARE UPPER_UNION_LIMIT INTEGER DEFAULT(FLOOR(IN_HowMany / 2) + 1);
    DECLARE LOWER_UNION_LIMIT INTEGER DEFAULT(FLOOR(IN_HowMany / 2));
    DECLARE CURRENT_USER_HIGHSCORE DOUBLE DEFAULT(0.00);
    SET CURRENT_USER_HIGHSCORE := (	SELECT high_score
									FROM userstats
									WHERE user_id = IN_UserId 
									AND app_id = IN_AppId
                                    AND app_level = IN_AppLevel	);

	-- SELECT stats.app_level, stats.latest_score, stats.high_score, stats.reward_points
    SELECT *
	FROM ((	SELECT *
			FROM userstats
			WHERE app_id = IN_AppId
            AND app_level = IN_AppLevel
			AND high_score >= CURRENT_USER_HIGHSCORE
			ORDER BY high_score ASC 
            LIMIT UPPER_UNION_LIMIT) 
            
			UNION 
		  
          (	SELECT *
			FROM userstats
			WHERE app_id = IN_AppId
            AND app_level = IN_AppLevel
			AND high_score < CURRENT_USER_HIGHSCORE
			ORDER BY high_score DESC
			LIMIT LOWER_UNION_LIMIT)) stats
	-- INNER JOIN user u
    -- ON u.user_id = stats.user_id
	ORDER BY stats.high_score DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-01  9:03:33
