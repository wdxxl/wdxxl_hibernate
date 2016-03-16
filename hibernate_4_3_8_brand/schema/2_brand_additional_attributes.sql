--
-- Table structure for table `brand_additional_attributes`
--

DROP TABLE IF EXISTS `brand_additional_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand_additional_attributes` (
  `brand_id` bigint(20) NOT NULL,
  `attribute_value` varchar(3072) DEFAULT NULL,
  `attribute_name` varchar(128) NOT NULL,
  PRIMARY KEY (`brand_id`,`attribute_name`),
  KEY `attribute_name` (`attribute_name`,`attribute_value`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
