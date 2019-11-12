-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ngtask
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `file_cfg`
--

DROP TABLE IF EXISTS `file_cfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_cfg` (
  `file_id` int(11) NOT NULL,
  `file_name` varchar(45) DEFAULT NULL COMMENT '文件描述',
  `file_local_path` varchar(100) DEFAULT NULL COMMENT '文件本地路径',
  `file_remote_path` varchar(100) DEFAULT NULL COMMENT '文件远程路径',
  `sftp_id` int(11) DEFAULT NULL COMMENT 'sftp配置id',
  `ftp_id` int(11) DEFAULT NULL COMMENT 'ftp配置id',
  `recursive` tinyint(4) DEFAULT NULL,
  `ext` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件操作配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ftp_cfg`
--

DROP TABLE IF EXISTS `ftp_cfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ftp_cfg` (
  `ftp_id` int(11) DEFAULT NULL,
  `ftp_addr` varchar(20) DEFAULT NULL,
  `ftp_port` int(11) DEFAULT NULL,
  `ftp_user_name` varchar(20) DEFAULT NULL,
  `ftp_user_passwd` varchar(20) DEFAULT NULL,
  `ext` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sftp_cfg`
--

DROP TABLE IF EXISTS `sftp_cfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sftp_cfg` (
  `sftp_id` int(11) NOT NULL AUTO_INCREMENT,
  `sftp_addr` varchar(20) DEFAULT NULL,
  `sftp_port` int(11) DEFAULT NULL,
  `sftp_user_name` varchar(20) DEFAULT NULL,
  `sftp_user_passwd` varchar(20) DEFAULT NULL,
  `ext` varchar(20) DEFAULT NULL,
  `sftp_encoding` varchar(10) DEFAULT NULL,
  `sftp_local_path` varchar(500) DEFAULT NULL,
  `sftp_remote_path` varchar(500) DEFAULT NULL,
  `recursion` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`sftp_id`),
  UNIQUE KEY `sftp_id_UNIQUE` (`sftp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL COMMENT '主键id',
  `task_name` varchar(128) DEFAULT NULL,
  `task_class_name` varchar(128) NOT NULL COMMENT '定时任务完整类名',
  `task_expression` varchar(20) NOT NULL COMMENT 'cron表达式',
  `task_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '任务描述',
  `task_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态，1：正常；2：停用',
  `after_task` varchar(100) DEFAULT NULL,
  `ftp_id` int(11) DEFAULT NULL,
  `sftp_id` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_log`
--

DROP TABLE IF EXISTS `task_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_name` varchar(128) NOT NULL COMMENT '定时任务完整类名',
  `task_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '任务描述',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '结束时间',
  `task_id` int(11) DEFAULT NULL,
  `task_result` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `cron_class_name` (`task_name`),
  UNIQUE KEY `task_log_unique_idx` (`task_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务执行日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'ngtask'
--

--
-- Dumping routines for database 'ngtask'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-08 17:12:13



DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) not null,
  `password` varchar(20) not null,
  `name` varchar(255) not NULL,
  `tel` varchar(255) not NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
