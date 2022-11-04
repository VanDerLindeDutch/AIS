-- MySQL dump 10.13  Distrib 5.5.23, for Win64 (x86)
--
-- Host: 127.0.0.1    Database: ais_mark1
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `bc_link`
--

DROP TABLE IF EXISTS `bc_link`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bc_link`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `birth_date` date         DEFAULT NULL,
    `email`      varchar(100) DEFAULT NULL,
    `first_name` varchar(20)  DEFAULT NULL,
    `login`      varchar(20)  DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bc_link`
--

LOCK TABLES `bc_link` WRITE;
/*!40000 ALTER TABLE `bc_link`
    DISABLE KEYS */;
INSERT INTO `bc_link` (`id`, `birth_date`, `email`, `first_name`, `login`, `password`)
VALUES (1, '2021-05-02', 'sotn.daniil@yandex.ru', 'Даниил', 'daniil',
        '$2a$12$KV8ptVytz.xIdslpa8OmMeIJfFxjJTy5Or9dR8I3OcgJpK/0kJkpq'),
       (2, '2021-05-14', 's@sdaniildaniil.ru', 'Даниил', 'VanDerLindeDutch',
        '$2a$12$VWTQR/y8drGxf2br02WcuOYW3m8VkpD8Q9nXFJYtkT7UhyKdFDj56');
/*!40000 ALTER TABLE `bc_link`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_center`
--

DROP TABLE IF EXISTS `business_center`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_center`
(
    `bc_id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `address`    varchar(100) DEFAULT NULL,
    `image_path` varchar(255) DEFAULT NULL,
    `name`       varchar(100) DEFAULT NULL,
    `short_name` varchar(100) DEFAULT NULL,
    `bc_link_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`bc_id`),
    KEY `FKt12srsjj861d46qus4bccn4up` (`bc_link_id`),
    CONSTRAINT `FKt12srsjj861d46qus4bccn4up` FOREIGN KEY (`bc_link_id`) REFERENCES `bc_link` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_center`
--

LOCK TABLES `business_center` WRITE;
/*!40000 ALTER TABLE `business_center`
    DISABLE KEYS */;
INSERT INTO `business_center` (`bc_id`, `address`, `image_path`, `name`, `short_name`, `bc_link_id`)
VALUES (1, 'набережная Обводного канала, 118 лит.С, Санкт-Петербург,', 'tenor.gif', 'Варшавский Экспресс', 'Варшавский',
        1);
/*!40000 ALTER TABLE `business_center`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floor`
--

DROP TABLE IF EXISTS `floor`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `floor`
(
    `floor_id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `description`     varchar(255) DEFAULT NULL,
    `floor_number`    bigint(20)   DEFAULT NULL,
    `image_path`      varchar(255) DEFAULT NULL,
    `business_center` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`floor_id`),
    KEY `FK1rbk98xs1owsrpqgj1vvr85w9` (`business_center`),
    CONSTRAINT `FK1rbk98xs1owsrpqgj1vvr85w9` FOREIGN KEY (`business_center`) REFERENCES `business_center` (`bc_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floor`
--

LOCK TABLES `floor` WRITE;
/*!40000 ALTER TABLE `floor`
    DISABLE KEYS */;
INSERT INTO `floor` (`floor_id`, `description`, `floor_number`, `image_path`, `business_center`)
VALUES (1, 'Цокольный', 1, 'tenor.gif', 1),
       (2, 'adsaddasad', 2,
        '18a521dc-f021-44fd-902b-752d6eacc2bc-3-8c18937d-d196-435d-b95e-e1991bb2d8f5.jpg__1024x683_q85_subsampling-2.jpg.1024x0_q85.jpg',
        1);
/*!40000 ALTER TABLE `floor`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placement`
--

DROP TABLE IF EXISTS `placement`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placement`
(
    `placement_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `image_path`   varchar(255) DEFAULT NULL,
    `price`        bigint(20) NOT NULL,
    `surface`      bigint(20) NOT NULL,
    `floor_id`     bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`placement_id`),
    KEY `FKs91suhhh0li6ibmtdc1ehhfrm` (`floor_id`),
    CONSTRAINT `FKs91suhhh0li6ibmtdc1ehhfrm` FOREIGN KEY (`floor_id`) REFERENCES `floor` (`floor_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placement`
--

LOCK TABLES `placement` WRITE;
/*!40000 ALTER TABLE `placement`
    DISABLE KEYS */;
INSERT INTO `placement` (`placement_id`, `image_path`, `price`, `surface`, `floor_id`)
VALUES (4, 'tenor.gif', 5001, 1, 1),
       (8, 'HtD_UbJzRiQ.jpg', 5002, 3, 1),
       (9, 'best-java-tutorial-2.jpg', 5004, 3, 2);
/*!40000 ALTER TABLE `placement`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `placement_service`
--

DROP TABLE IF EXISTS `placement_service`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `placement_service`
(
    `placement_id` bigint(20) NOT NULL,
    `service_id`   bigint(20) NOT NULL,
    PRIMARY KEY (`placement_id`, `service_id`),
    KEY `FKogsrida8s0j6yv3wu013sn58t` (`service_id`),
    CONSTRAINT `FKogsrida8s0j6yv3wu013sn58t` FOREIGN KEY (`service_id`) REFERENCES `service` (`service_id`),
    CONSTRAINT `FKt0x8gnen86xsbvkn8m9kdcna6` FOREIGN KEY (`placement_id`) REFERENCES `placement` (`placement_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `placement_service`
--

LOCK TABLES `placement_service` WRITE;
/*!40000 ALTER TABLE `placement_service`
    DISABLE KEYS */;
INSERT INTO `placement_service` (`placement_id`, `service_id`)
VALUES (4, 1),
       (8, 1),
       (9, 1),
       (9, 4),
       (9, 16);
/*!40000 ALTER TABLE `placement_service`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rented_placement`
--

DROP TABLE IF EXISTS `rented_placement`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rented_placement`
(
    `placement_id`  bigint(20) NOT NULL,
    `end_of_rent`   datetime   DEFAULT NULL,
    `start_of_rent` datetime   DEFAULT NULL,
    `total_amount`  bigint(20) DEFAULT NULL,
    `r_id`          bigint(20) DEFAULT NULL,
    PRIMARY KEY (`placement_id`),
    KEY `FKjml1lb1bp1ghvgp1ebs4wl4kn` (`r_id`),
    CONSTRAINT `FKhvrpeydphaib6ddvp4j1uy3fg` FOREIGN KEY (`placement_id`) REFERENCES `placement` (`placement_id`),
    CONSTRAINT `FKjml1lb1bp1ghvgp1ebs4wl4kn` FOREIGN KEY (`r_id`) REFERENCES `renterlink` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rented_placement`
--

LOCK TABLES `rented_placement` WRITE;
/*!40000 ALTER TABLE `rented_placement`
    DISABLE KEYS */;
INSERT INTO `rented_placement` (`placement_id`, `end_of_rent`, `start_of_rent`, `total_amount`, `r_id`)
VALUES (9, '2021-12-01 00:00:00', '2021-04-01 00:00:00', 40032, 1);
/*!40000 ALTER TABLE `rented_placement`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `renterlink`
--

DROP TABLE IF EXISTS `renterlink`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `renterlink`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `birth_date` date         DEFAULT NULL,
    `email`      varchar(100) DEFAULT NULL,
    `first_name` varchar(20)  DEFAULT NULL,
    `login`      varchar(20)  DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `renterlink`
--

LOCK TABLES `renterlink` WRITE;
/*!40000 ALTER TABLE `renterlink`
    DISABLE KEYS */;
INSERT INTO `renterlink` (`id`, `birth_date`, `email`, `first_name`, `login`, `password`)
VALUES (1, '2021-05-02', 'sotn.daniil@yandex.ru', 'Даниил', 'daniil',
        '$2a$12$1nUNjaPQkzzO/nfGvvNbq.Bwpj1RQ7AxgUIkeW01QGsvfUHXy.9qu'),
       (2, '2021-05-01', 'daniil.sotnikoff@yandex.ru', 'Даниил', 'VanDerLindeDutch',
        '$2a$12$gVHSmeFEbubVY6YGVun7O./Xs4zrzV1NBWS4MngDOBltO4RWbs4Ou');
/*!40000 ALTER TABLE `renterlink`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_to_bc_link`
--

DROP TABLE IF EXISTS `request_to_bc_link`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_to_bc_link`
(
    `request_id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `end_of_rent`     datetime   DEFAULT NULL,
    `expiration_date` datetime   DEFAULT NULL,
    `is_checked`      bit(1)     DEFAULT NULL,
    `start_of_rent`   datetime   DEFAULT NULL,
    `total_amount`    bigint(20) DEFAULT NULL,
    `bc_link_id`      bigint(20) DEFAULT NULL,
    `pl_id`           bigint(20) DEFAULT NULL,
    `r_id`            bigint(20) DEFAULT NULL,
    PRIMARY KEY (`request_id`),
    KEY `FKm2xg0sg1upd0xg4cul29f232t` (`bc_link_id`),
    KEY `FKevw7kec0xax5owg9sqafdsahk` (`pl_id`),
    KEY `FKswg68kg0yn54j7grmkhutyht4` (`r_id`),
    CONSTRAINT `FKevw7kec0xax5owg9sqafdsahk` FOREIGN KEY (`pl_id`) REFERENCES `placement` (`placement_id`),
    CONSTRAINT `FKm2xg0sg1upd0xg4cul29f232t` FOREIGN KEY (`bc_link_id`) REFERENCES `bc_link` (`id`),
    CONSTRAINT `FKswg68kg0yn54j7grmkhutyht4` FOREIGN KEY (`r_id`) REFERENCES `renterlink` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_to_bc_link`
--

LOCK TABLES `request_to_bc_link` WRITE;
/*!40000 ALTER TABLE `request_to_bc_link`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `request_to_bc_link`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service`
(
    `service_id`  bigint(20) NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`service_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service`
    DISABLE KEYS */;
INSERT INTO `service` (`service_id`, `description`)
VALUES (1, 'Канализация'),
       (2, 'Интернет'),
       (3, 'Уборка'),
       (4, 'es'),
       (5, 'sdsad'),
       (6, 'sad'),
       (7, 'asdas'),
       (8, 'sad'),
       (9, 'sadsad'),
       (10, 'sda'),
       (11, 'asd'),
       (12, 'asdas'),
       (13, 'asd'),
       (14, 'asdaad'),
       (15, 'wqdwqdwq'),
       (16, 'asdasddsadasdsadsdsasdasdas');
/*!40000 ALTER TABLE `service`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-05-17 16:03:37
