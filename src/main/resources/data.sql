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
-- Dumping data for table `file_cfg`
--

/*!40000 ALTER TABLE `file_cfg` DISABLE KEYS */;
INSERT INTO `file_cfg` VALUES (1,'测试文件','F:/workspace/idea/ngtask/fuckOff/.*OrdMember_{yyyyMMdd-1}.*',NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `file_cfg` ENABLE KEYS */;


--
-- Dumping data for table `sftp_cfg`
--

/*!40000 ALTER TABLE `sftp_cfg` DISABLE KEYS */;
INSERT INTO `sftp_cfg` VALUES (1,'10.7.5.69',22,'ftpuser','ftpuser','10.7.5.69-sftp','gbk','F:\\workspace\\idea\\ngtask\\fuckOff','/app/ftpuser/100_OrdMember_{yyyy}*',1),(2,'10.7.5.69',22,'ftpuser','ftpuser','10.7.5.69-sftp','gbk',NULL,'/app/ftpuser/Test/1545734*',0),(3,'10.7.5.69',22,'ftpuser','ftpuser','10.7.5.69-sftp','gbk',NULL,NULL,NULL);
/*!40000 ALTER TABLE `sftp_cfg` ENABLE KEYS */;

--
-- Dumping data for table `task`
--

INSERT INTO `task` VALUES (1,'100','com.cmos.bj.ngtask.task.impl.sftp.SftpDownloadTaskImpl','0 0/1 * * * *','Test',2,'NULL',NULL,1,NULL),(2,'154','com.cmos.bj.ngtask.task.impl.sftp.SftpDownloadTaskImpl','0 0/1 * * * *','TestFtp',2,'CREATEFILE:CSMS010{yyyyMMddHHmmss}.verf',NULL,2,NULL),(3,'deleteFileTest','com.cmos.bj.ngtask.task.impl.file.DeleteLocalFileTaskImpl','0 0/1 * * * *','deleteFileTest',2,'NULL',NULL,NULL,1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES (1,'test','test');

insert into `admin` values (1, 'kongzheng', 'kongzheng', '15810692477', '孔征');
insert into `admin` values (2, 'kongzheng1', 'kongzheng1', '15810692477', '孔征1');