/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : fxiaoke

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/03/2020 19:33:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fxk_report
-- ----------------------------
DROP TABLE IF EXISTS `fxk_report`;
CREATE TABLE `fxk_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `owner` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `error` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fxk_sequence
-- ----------------------------
DROP TABLE IF EXISTS `fxk_sequence`;
CREATE TABLE `fxk_sequence`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `yf` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `xh` int(11) NOT NULL,
  `suffix` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '#',
  `pattern` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'yyyyMMdd',
  `max` int(11) NOT NULL,
  `len` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_sequence`(`yf`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_detail
-- ----------------------------
DROP TABLE IF EXISTS `job_detail`;
CREATE TABLE `job_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` int(11) NOT NULL,
  `data_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: 待处理，1：已处理，-1：错误',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `error` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 408 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_log
-- ----------------------------
DROP TABLE IF EXISTS `job_log`;
CREATE TABLE `job_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrtz_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `start_time` timestamp(0) NULL DEFAULT NULL,
  `end_time` timestamp(0) NULL DEFAULT NULL,
  `count` int(11) NOT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `success` int(11) NOT NULL DEFAULT 0,
  `fail` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 347 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_type
-- ----------------------------
DROP TABLE IF EXISTS `job_type`;
CREATE TABLE `job_type`  (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `executor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `api_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_type
-- ----------------------------
INSERT INTO `job_type` VALUES (1, '报价单接口', 'fxkJobQueueExecutor', 'apprN88C5U0RHN__crmappr', 0);
INSERT INTO `job_type` VALUES (2, '合同接口', 'fxkJobQueueExecutor', 'apprSKZAG0EK10__crmappr', 1);
INSERT INTO `job_type` VALUES (3, '订单接口', 'erpJobQueueExecutor', '/dingdan?startTime={startTime}&endTime={endTime}&page={page}', 0);
INSERT INTO `job_type` VALUES (4, '回款接口', 'erpJobQueueExecutor', '/huikuan?startTime={startTime}&endTime={endTime}&page={page}', 0);
INSERT INTO `job_type` VALUES (5, '报销单接口', 'fxkJobQueueExecutor', 'apprNK1W5VL9XA__crmappr', 0);
INSERT INTO `job_type` VALUES (6, '设备接口', 'erpJobQueueExecutor', '/shebei?startTime={startTime}&endTime={endTime}&page={page}', 0);

-- ----------------------------
-- Table structure for qrtz_group
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_group`;
CREATE TABLE `qrtz_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_group
-- ----------------------------
INSERT INTO `qrtz_group` VALUES (1, 'DEFAULT', 1);
INSERT INTO `qrtz_group` VALUES (10, 'DEFAULT2', -1);

-- ----------------------------
-- Table structure for qrtz_job
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job`;
CREATE TABLE `qrtz_job`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bean` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `child_ids` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job
-- ----------------------------
INSERT INTO `qrtz_job` VALUES (7, 'DEFAULT', '任务队列管理器', '', 'jobLog', '', '8', 2, '2019-12-01 21:02:24', NULL);
INSERT INTO `qrtz_job` VALUES (8, 'DEFAULT', '任务队列处理器', '', 'jobDetail', '', NULL, 2, '2019-12-01 21:02:41', NULL);
INSERT INTO `qrtz_job` VALUES (9, 'DEFAULT', '计划任务测试', NULL, 'testJob', '', '', 0, '2020-02-25 23:06:37', NULL);

-- ----------------------------
-- Table structure for qrtz_log
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_log`;
CREATE TABLE `qrtz_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL,
  `job_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp(0) NULL DEFAULT NULL,
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 317 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_trigger
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_trigger`;
CREATE TABLE `qrtz_trigger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `trigger_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time_interval` int(11) NULL DEFAULT NULL,
  `job_id` int(11) NOT NULL DEFAULT 0,
  `status` tinyint(4) NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_trigger
-- ----------------------------
INSERT INTO `qrtz_trigger` VALUES (3, 'DEFAULT', '任务队列-管理-触发器', '', 60, 7, 0, '2019-12-01 21:03:42', '2019-12-02 02:12:59');
INSERT INTO `qrtz_trigger` VALUES (5, 'DEFAULT', '任务队列-处理-触发器', '', 100000, 8, 0, '2019-12-02 10:12:40', NULL);
INSERT INTO `qrtz_trigger` VALUES (6, 'DEFAULT', '计划任务测试-触发器', '', 1000, 9, 0, '2020-02-25 23:08:10', NULL);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_sys_config`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 1001, '{\"id\":1,\"type\":1001,\"appId\":\"FSAID_***\",\"appSecret\":\"***\",\"permanentCode\":\"***\",\"token\":\"***\",\"encodingAesKey\":\"***\",\"authorizeUrl\":\"https://open.fxiaoke.com/oauth2/authorize\",\"syncOpenId\":\"***\"}');
INSERT INTO `sys_config` VALUES (2, 1002, '{\"id\":2,\"status\":1,\"openid\":\"FSUID_F93A48F35DB2F9E0274C023C6C0C4CEB\"}');
INSERT INTO `sys_config` VALUES (12, 10010, '{\"id\":12,\"type\":1001,\"appId\":\"FSAID_13185ec\",\"appSecret\":\"75193ed3510c43e0be323b55f6fc13cc\",\"permanentCode\":\"0D74A41E30895C2BE9E0EFDD25BDEDEA\",\"token\":\"0450c9ad989246dbaf6932462a6644aa\",\"encodingAesKey\":\"NWVlZWUzM2I2NWRlNDM4YmEyY2IzNmY4Yzc1ZGQ3MTc\",\"authorizeUrl\":\"https://open.fxiaoke.com/oauth2/authorize\",\"syncOpenId\":\"FSUID_6E61DFE00DB6B8F5CE519839CB89A7F5\"}');
INSERT INTO `sys_config` VALUES (13, 1003, '{\"apiQuoteObj\":{\"pattern\":\"\'A\'yyyyMMdd\",\"max\":99,\"len\":2},\"apiObject_snPZx__c\":{\"pattern\":\"\'B\'yyyyMMdd\",\"max\":99,\"len\":2},\"apiObject_okom1__c\":{\"pattern\":\"\'C\'yyyyMM\",\"max\":999,\"len\":2}}');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_sys_user`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '$2a$10$es7OMCwWtUpTWMOV7SeKoubE4OUFCf5JbXNPLvGYgcXXfWu1DuttW');

SET FOREIGN_KEY_CHECKS = 1;
