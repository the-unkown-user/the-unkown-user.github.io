/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : iaeep

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 02/10/2022 08:57:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_type` enum('表情','文件','图片','文字') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '聊天记录类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '聊天内容',
  `time` date NULL DEFAULT NULL COMMENT '时间',
  `contacts_id` int(11) NULL DEFAULT NULL COMMENT '所属用户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `contacts_id`(`contacts_id`) USING BTREE,
  CONSTRAINT `chat_record_ibfk_1` FOREIGN KEY (`contacts_id`) REFERENCES `contacts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_record
-- ----------------------------

-- ----------------------------
-- Table structure for comment_information
-- ----------------------------
DROP TABLE IF EXISTS `comment_information`;
CREATE TABLE `comment_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NULL DEFAULT NULL COMMENT '所属贴子编号',
  `send_user_id` int(11) NULL DEFAULT NULL COMMENT '发送用户编号',
  `receive_user_id` int(11) NULL DEFAULT NULL COMMENT '接收用户编号',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `comment_like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `time` date NULL DEFAULT NULL COMMENT '发表评论时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_id`(`post_id`) USING BTREE,
  INDEX `send_user_id`(`send_user_id`) USING BTREE,
  INDEX `receive_user_id`(`receive_user_id`) USING BTREE,
  CONSTRAINT `comment_information_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_information_ibfk_2` FOREIGN KEY (`send_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_information_ibfk_3` FOREIGN KEY (`receive_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_information
-- ----------------------------

-- ----------------------------
-- Table structure for comment_reply1
-- ----------------------------
DROP TABLE IF EXISTS `comment_reply1`;
CREATE TABLE `comment_reply1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply_time` date NULL DEFAULT NULL COMMENT '回复时间',
  `reply_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '回复内容',
  `reply_like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `send_reply_user_id` int(11) NULL DEFAULT NULL COMMENT '发送回复用户编号',
  `receive_reply_user_id` int(11) NULL DEFAULT NULL COMMENT '接收回复用户编号',
  `comment_id` int(11) NULL DEFAULT NULL COMMENT '所属评论id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_id`(`comment_id`) USING BTREE,
  INDEX `send_reply_user_id`(`send_reply_user_id`) USING BTREE,
  INDEX `receive_reply_user_id`(`receive_reply_user_id`) USING BTREE,
  CONSTRAINT `comment_reply1_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comment_information` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_reply1_ibfk_2` FOREIGN KEY (`send_reply_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_reply1_ibfk_3` FOREIGN KEY (`receive_reply_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_reply1
-- ----------------------------

-- ----------------------------
-- Table structure for comment_reply2
-- ----------------------------
DROP TABLE IF EXISTS `comment_reply2`;
CREATE TABLE `comment_reply2`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply_time` date NULL DEFAULT NULL COMMENT '回复时间',
  `reply_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '回复内容',
  `reply_like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `send_reply_user_id` int(11) NULL DEFAULT NULL COMMENT '发送回复用户编号',
  `receive_reply_user_id` int(11) NULL DEFAULT NULL COMMENT '接收回复用户编号',
  `reply_id` int(11) NULL DEFAULT NULL COMMENT '所属回复id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_id`(`reply_id`) USING BTREE,
  INDEX `send_reply_user_id`(`send_reply_user_id`) USING BTREE,
  INDEX `receive_reply_user_id`(`receive_reply_user_id`) USING BTREE,
  CONSTRAINT `comment_reply2_ibfk_1` FOREIGN KEY (`reply_id`) REFERENCES `comment_reply1` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_reply2_ibfk_2` FOREIGN KEY (`send_reply_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_reply2_ibfk_3` FOREIGN KEY (`receive_reply_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_reply2
-- ----------------------------

-- ----------------------------
-- Table structure for contacts
-- ----------------------------
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) NOT NULL COMMENT '用户编号1',
  `to_user_id` int(11) NOT NULL COMMENT '用户编号2',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `from_user_id`(`from_user_id`) USING BTREE,
  INDEX `to_user_id`(`to_user_id`) USING BTREE,
  CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `contacts_ibfk_2` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contacts
-- ----------------------------

-- ----------------------------
-- Table structure for enterprise_information
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_information`;
CREATE TABLE `enterprise_information`  (
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业名称',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业地址',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '企业简介',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业服务电话',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业邮箱',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `enterprise_information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enterprise_information
-- ----------------------------

-- ----------------------------
-- Table structure for hot_posts
-- ----------------------------
DROP TABLE IF EXISTS `hot_posts`;
CREATE TABLE `hot_posts`  (
  `post_id` int(11) NOT NULL,
  PRIMARY KEY (`post_id`) USING BTREE,
  CONSTRAINT `hot_posts_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hot_posts
-- ----------------------------

-- ----------------------------
-- Table structure for log_info
-- ----------------------------
DROP TABLE IF EXISTS `log_info`;
CREATE TABLE `log_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` date NULL DEFAULT NULL COMMENT '时间',
  `logger` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志产生位置',
  `level` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志级别',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '日志内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_info
-- ----------------------------

-- ----------------------------
-- Table structure for personal_information
-- ----------------------------
DROP TABLE IF EXISTS `personal_information`;
CREATE TABLE `personal_information`  (
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `sex` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `graduate_school` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业院校',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `personal_information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of personal_information
-- ----------------------------

-- ----------------------------
-- Table structure for post_collection
-- ----------------------------
DROP TABLE IF EXISTS `post_collection`;
CREATE TABLE `post_collection`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '帖子编号',
  `time` date NULL DEFAULT NULL COMMENT '时间',
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `post_id`(`post_id`) USING BTREE,
  CONSTRAINT `post_collection_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `post_collection_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_collection
-- ----------------------------

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `post_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '帖子内容',
  `enclosure` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件地址',
  `enclosure_type` enum('图片','文件') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件类型，0:图片，文件',
  `post_kind` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子分类',
  `like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '所属用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------

-- ----------------------------
-- Table structure for project_information
-- ----------------------------
DROP TABLE IF EXISTS `project_information`;
CREATE TABLE `project_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `charge_user_id` int(11) NULL DEFAULT NULL COMMENT '项目负责人Id',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人电话',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人邮箱',
  `project_introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '项目介绍',
  `project_personNum` int(11) NULL DEFAULT NULL COMMENT '项目团队人数',
  `project_status` enum('进行中','已结项','项目终止') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目状态',
  `project_type` enum('个人','企业') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目类别',
  `project_label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目标签',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `charge_user_id`(`charge_user_id`) USING BTREE,
  CONSTRAINT `project_information_ibfk_1` FOREIGN KEY (`charge_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project_information
-- ----------------------------

-- ----------------------------
-- Table structure for team_information
-- ----------------------------
DROP TABLE IF EXISTS `team_information`;
CREATE TABLE `team_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `project_id`(`project_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `team_information_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_information` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `team_information_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team_information
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `user_type` enum('个人','企业','管理员') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
