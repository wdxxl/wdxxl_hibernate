--
-- Table structure for table `brand_synonym`
--

DROP TABLE IF EXISTS `brand_synonym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand_synonym` (
  `brand_synonym_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `language` varchar(8) DEFAULT NULL,
  `indexable` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`brand_synonym_id`),
  UNIQUE KEY `brand_synonym_name_language` (`brand_id`,`name`,`language`),
  KEY `brand_brand_synonym_id` (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4743 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;