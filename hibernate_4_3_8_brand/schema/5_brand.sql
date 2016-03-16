--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `brand` (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `deleted` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `explicit_match_only` bit(1) NOT NULL,
  `bad_sem_keyword` bit(1) NOT NULL,
  `excluded_from_sem` bit(1) NOT NULL,
  `is_featured` bit(1) NOT NULL DEFAULT b'0',
  `size_prefix` varchar(5) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `parent_brand_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`brand_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32818 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;