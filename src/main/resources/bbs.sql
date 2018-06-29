/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : 127.0.0.1:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-06-29 15:24:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `replyId` int(8) NOT NULL AUTO_INCREMENT COMMENT '回帖ID',
  `content` varchar(255) NOT NULL COMMENT '回帖内容',
  `accountId` varchar(32) NOT NULL COMMENT '回帖人ID',
  `topicId` int(8) NOT NULL COMMENT '帖子ID',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`replyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `topicId` int(8) NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `title` varchar(255) NOT NULL COMMENT '帖子标题',
  `content` varchar(255) NOT NULL COMMENT '帖子内容',
  `accountId` varchar(32) NOT NULL COMMENT '发帖人ID',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`topicId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `accountId` varchar(32) NOT NULL COMMENT '登录账号',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `email` varchar(32) NOT NULL COMMENT '邮箱',
  `sex` varchar(2) NOT NULL COMMENT '性别',
  `age` varchar(2) DEFAULT NULL COMMENT '年龄',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '用户激活状态：0表示未激活，1表示激活',
  `code` varchar(255) NOT NULL COMMENT '激活码',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
