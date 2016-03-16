--
-- Table structure for table `brand_common_name`
--

DROP TABLE IF EXISTS `brand_common_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand_common_name` (
  `brand_id` bigint(20) NOT NULL,
  `common_name` varchar(255) DEFAULT NULL,
  `common_name_index` int(11) NOT NULL,
  PRIMARY KEY (`brand_id`,`common_name_index`),
  KEY `brand_common_name_brand_id` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;