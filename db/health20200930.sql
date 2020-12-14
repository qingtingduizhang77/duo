/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : health

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-09-30 16:10:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for advert_content
-- ----------------------------
DROP TABLE IF EXISTS `advert_content`;
CREATE TABLE `advert_content` (
  `content_id` varchar(32) NOT NULL COMMENT '内容id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '发布状态',
  `content_title` varchar(80) DEFAULT NULL COMMENT '标题',
  `publish_time` datetime DEFAULT NULL COMMENT '发表时间',
  `summary` varchar(200) DEFAULT NULL COMMENT '摘要',
  `content_type` varchar(32) DEFAULT NULL COMMENT '内容分类',
  `content_body` text COMMENT '广告内容',
  `suit_place` varchar(200) DEFAULT NULL COMMENT '适用广告位',
  `edit_username` varchar(40) DEFAULT NULL COMMENT '编辑人',
  `edit_userid` varchar(32) DEFAULT NULL COMMENT '编辑人id',
  `check_username` varchar(40) DEFAULT NULL COMMENT '审批人',
  `check_userid` varchar(32) DEFAULT NULL COMMENT '审批人id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '广告主',
  `company_id` varchar(32) DEFAULT NULL COMMENT '广告主id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告内容管理';

-- ----------------------------
-- Records of advert_content
-- ----------------------------

-- ----------------------------
-- Table structure for advert_place
-- ----------------------------
DROP TABLE IF EXISTS `advert_place`;
CREATE TABLE `advert_place` (
  `place_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `palce_code` varchar(40) DEFAULT NULL COMMENT '广告位编号',
  `place_name` varchar(80) DEFAULT NULL COMMENT '广告位名称',
  `place_version` varchar(60) DEFAULT NULL COMMENT '版本号',
  `place_type` varchar(20) DEFAULT NULL COMMENT '广告位类型',
  `place_size` varchar(200) DEFAULT NULL COMMENT '广告位规格',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `place_level` int(11) DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位定义';

-- ----------------------------
-- Records of advert_place
-- ----------------------------
INSERT INTO `advert_place` VALUES ('1001', '', 'A1', '售卖机广告', '', '4', '', '', 'admin', '2020-07-14 16:06:28', null, null, '0', '2');
INSERT INTO `advert_place` VALUES ('10011001', '', 'A01', '首页', '', '4', '', '', 'admin', '2020-07-14 16:09:02', null, null, '0', '2');
INSERT INTO `advert_place` VALUES ('1002', '', '2', '2', '', '4', '', '', 'admin', '2020-07-14 16:08:37', null, null, '0', '1');
INSERT INTO `advert_place` VALUES ('1003', '', '2', '2', '', '4', '', '', 'admin', '2020-07-14 16:08:37', null, null, '0', '1');

-- ----------------------------
-- Table structure for base_field
-- ----------------------------
DROP TABLE IF EXISTS `base_field`;
CREATE TABLE `base_field` (
  `field_id` varchar(32) NOT NULL COMMENT 'field_id',
  `field_code` varchar(40) DEFAULT NULL COMMENT '区域编码',
  `field_name` varchar(80) DEFAULT NULL COMMENT '区域名称',
  `field_fullname` varchar(200) DEFAULT NULL COMMENT '区域全称',
  `order_index` int(11) DEFAULT NULL COMMENT '排序',
  `field_level` int(11) DEFAULT NULL COMMENT '层级',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地理区域';

-- ----------------------------
-- Records of base_field
-- ----------------------------
INSERT INTO `base_field` VALUES ('1001', 'GD', '广东省', '', '1', '1', '', '', 'admin', '2020-06-19 11:03:55', 'admin', '2020-06-19 13:17:32', '0');
INSERT INTO `base_field` VALUES ('10011001', 'GZ', '广州市', '', '5', '2', '', '', 'admin', '2020-06-19 13:17:45', null, null, '0');
INSERT INTO `base_field` VALUES ('100110011001', 'SXQ', '市辖区', '', '5', '3', '', '', 'admin', '2020-06-24 16:12:55', null, null, '0');
INSERT INTO `base_field` VALUES ('1001100110011001', 'A001', '新华街道', '', '5', '4', '', '', 'admin', '2020-08-24 15:21:35', null, null, '0');
INSERT INTO `base_field` VALUES ('100110011002', 'THQ', '天河区', '', '5', '3', '', '', 'admin', '2020-08-24 12:18:06', null, null, '0');
INSERT INTO `base_field` VALUES ('100110011003', 'PYQ', '番禺区', '', '5', '3', '', '', 'admin', '2020-08-24 12:18:06', null, null, '0');
INSERT INTO `base_field` VALUES ('10011002', 'SZ', '深圳市', '', '15', '2', '', '', 'admin', '2020-06-19 13:17:47', 'admin', '2020-06-19 13:18:27', '0');
INSERT INTO `base_field` VALUES ('10011003', 'FS', '佛山市', '', '25', '2', '', '', 'admin', '2020-06-19 13:17:50', 'admin', '2020-06-19 13:18:27', '0');
INSERT INTO `base_field` VALUES ('10011004', 'ZS', '中山市', '', '35', '2', '', '', 'admin', '2020-06-19 13:17:50', 'admin', '2020-06-19 13:18:27', '0');
INSERT INTO `base_field` VALUES ('10011005', 'ZH', '珠海市', '', '45', '2', '', '', 'admin', '2020-06-19 13:18:52', null, null, '0');
INSERT INTO `base_field` VALUES ('10011006', 'JM', '江门市', '', '55', '2', '', '', 'admin', '2020-06-19 13:18:52', 'admin', '2020-06-19 13:19:02', '0');
INSERT INTO `base_field` VALUES ('1002', 'FJ', '福建省', '', '5', '1', '', '', 'admin', '2020-08-24 15:28:43', null, null, '0');

-- ----------------------------
-- Table structure for base_position
-- ----------------------------
DROP TABLE IF EXISTS `base_position`;
CREATE TABLE `base_position` (
  `position_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `position_code` varchar(40) DEFAULT NULL COMMENT '点位编号',
  `field_name` varchar(60) DEFAULT NULL COMMENT '所属区域',
  `field_id` varchar(32) DEFAULT NULL COMMENT '所属区域id',
  `address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `longitude` varchar(20) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) DEFAULT NULL COMMENT '纬度',
  `position_property` varchar(20) DEFAULT NULL COMMENT '点位性质',
  `is_indoor` varchar(10) DEFAULT NULL COMMENT '是否室内',
  `provider_name` varchar(60) DEFAULT NULL COMMENT '所属业主',
  `provider_id` varchar(32) DEFAULT NULL COMMENT '所属业主id',
  `contract_expire` datetime DEFAULT NULL COMMENT '合同到期时间',
  `usable_area` varchar(100) DEFAULT NULL COMMENT '可用面积',
  `advantage` varchar(100) DEFAULT NULL COMMENT '位置特点',
  `video_id` varchar(32) DEFAULT NULL COMMENT '网络摄像头id',
  `owner_username` varchar(40) DEFAULT NULL COMMENT '责任人',
  `owner_userid` varchar(32) DEFAULT NULL COMMENT '责任人id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '管理部门',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '管理部门id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点位管理';

-- ----------------------------
-- Records of base_position
-- ----------------------------
INSERT INTO `base_position` VALUES ('824777258649567711', '', '1', '1', '广东省', '1001', 'guangzhou', '', '', 'factory', '1', '4665', '800678575107721407', '2020-07-09 15:40:25', '', '', '', 'test', 'a1001', 'DUO信息科技有限公司', '1001', '', 'admin', '2020-07-09 15:40:41', 'admin', '2020-07-11 09:51:16', '0');
INSERT INTO `base_position` VALUES ('827414660627177971', '', '0', '1', '广东省', '1001', 'guangzhou', '', '', 'factory', '1', '4665', '800678575107721407', '2020-07-09 15:40:25', '', '', '', 'test', 'a1001', 'DUO信息科技有限公司', '1001', '', 'admin', '2020-07-11 10:27:10', '', null, '0');

-- ----------------------------
-- Table structure for comment_message
-- ----------------------------
DROP TABLE IF EXISTS `comment_message`;
CREATE TABLE `comment_message` (
  `message_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '留言状态',
  `message_time` datetime DEFAULT NULL COMMENT '留言时间',
  `message_content` text COMMENT '留言内容',
  `source_type` varchar(20) DEFAULT NULL COMMENT '留言来源',
  `source_id` varchar(32) DEFAULT NULL COMMENT '来源id',
  `reply_content` text COMMENT '回复内容',
  `reply_username` varchar(40) DEFAULT NULL COMMENT '回复人',
  `reply_userid` varchar(32) DEFAULT NULL COMMENT '回复人id',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `last_message_id` varchar(32) DEFAULT NULL COMMENT '上一条留言id',
  `is_sms` varchar(10) DEFAULT NULL COMMENT '是否短信通知',
  `member_name` varchar(40) DEFAULT NULL COMMENT '留言用户',
  `member_id` varchar(32) DEFAULT NULL COMMENT '留言用户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论留言管理';

-- ----------------------------
-- Records of comment_message
-- ----------------------------

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info` (
  `company_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '管理状态',
  `company_type` varchar(20) DEFAULT NULL COMMENT '商户类型',
  `company_name` varchar(80) DEFAULT NULL COMMENT '商户名称',
  `company_code` varchar(40) DEFAULT NULL COMMENT '商户编号',
  `company_sortname` varchar(40) DEFAULT NULL COMMENT '商户简称',
  `field_id` varchar(32) DEFAULT NULL COMMENT 'field_id',
  `field_name` varchar(80) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(200) DEFAULT NULL COMMENT '商户地址',
  `contract_user` varchar(80) DEFAULT NULL COMMENT '联系人',
  `contract_phone` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `lose_memo` varchar(200) DEFAULT NULL COMMENT '注销原因',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户注册';

-- ----------------------------
-- Records of company_info
-- ----------------------------
INSERT INTO `company_info` VALUES ('798180862646370973', '', '0', 'drugstore', '2', '1', '', '10011001', '广州市', '天天', '', '', '', '', 'admin', '2020-06-21 19:42:36', 'admin', '2020-07-10 13:39:23', '0');
INSERT INTO `company_info` VALUES ('800678575107721407', '', '0', 'drugstore', '4665', '2', '', '', '中山市', '', '', '', '', '', 'admin', '2020-06-24 16:14:04', 'admin', '2020-07-17 14:51:02', '0');
INSERT INTO `company_info` VALUES ('836323539490808765', '', '0', 'hospital', '中医院', '3', '中医院', '', '中山市', '', '', '', '', '', 'admin', '2020-07-17 14:48:27', 'admin', '2020-07-17 14:51:02', '0');

-- ----------------------------
-- Table structure for contract_info
-- ----------------------------
DROP TABLE IF EXISTS `contract_info`;
CREATE TABLE `contract_info` (
  `contract_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '合同状态',
  `contract_code` varchar(40) DEFAULT NULL COMMENT '合同编号',
  `contract_date` datetime DEFAULT NULL COMMENT '合同签订日期',
  `contract_name` varchar(80) DEFAULT NULL COMMENT '合同名称',
  `provider_code` varchar(40) DEFAULT NULL COMMENT '供应商编号',
  `provider_name` varchar(80) DEFAULT NULL COMMENT '供应商名称',
  `contract_money` float DEFAULT NULL COMMENT '合同总额(元)',
  `sup_user` varchar(40) DEFAULT NULL COMMENT '供方经办人',
  `price` float DEFAULT NULL COMMENT '设备总价(元)',
  `contract_descript` text COMMENT '合同概要',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '管理部门',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '管理部门id',
  `user_name` varchar(40) DEFAULT NULL COMMENT '授权人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '授权人id',
  `provider_id` varchar(32) DEFAULT NULL COMMENT '供应商id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同管理';

-- ----------------------------
-- Records of contract_info
-- ----------------------------

-- ----------------------------
-- Table structure for coupon_activities
-- ----------------------------
DROP TABLE IF EXISTS `coupon_activities`;
CREATE TABLE `coupon_activities` (
  `activities_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(10) DEFAULT NULL COMMENT '是否有效',
  `activities_name` varchar(80) DEFAULT NULL COMMENT '活动名称',
  `coupon_type` varchar(10) DEFAULT NULL COMMENT '券类型',
  `apply_date` datetime DEFAULT NULL COMMENT '审批日期',
  `coupon_name` varchar(80) DEFAULT NULL COMMENT '优惠券名称',
  `img_url` varchar(200) DEFAULT NULL COMMENT '优惠券图片',
  `coupon_num` int(11) DEFAULT NULL COMMENT '数量',
  `coupon_price` float DEFAULT NULL COMMENT '优惠金额',
  `total_money` float DEFAULT NULL COMMENT '最小可用金额',
  `future_days` int(11) DEFAULT NULL COMMENT '获得起几天',
  `begin_time` datetime DEFAULT NULL COMMENT '指定可用开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '指定可用结束时间',
  `use_type` varchar(20) DEFAULT NULL COMMENT '叠加方式',
  `user_name` varchar(40) DEFAULT NULL COMMENT '审核人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`activities_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠活动管理';

-- ----------------------------
-- Records of coupon_activities
-- ----------------------------

-- ----------------------------
-- Table structure for coupon_list
-- ----------------------------
DROP TABLE IF EXISTS `coupon_list`;
CREATE TABLE `coupon_list` (
  `coupon_id` varchar(32) NOT NULL COMMENT '主键id',
  `activities_id` varchar(32) DEFAULT NULL COMMENT 'activities_id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '使用状态',
  `coupon_type` varchar(20) DEFAULT NULL COMMENT '券类型',
  `coupon_code` varchar(32) DEFAULT NULL COMMENT '优惠券编号',
  `coupon_name` varchar(80) DEFAULT NULL COMMENT '优惠券名称',
  `img_url` varchar(200) DEFAULT NULL COMMENT '优惠券图片',
  `coupon_price` float DEFAULT NULL COMMENT '优惠金额',
  `total_money` float DEFAULT NULL COMMENT '最小可用金额',
  `use_type` varchar(20) DEFAULT NULL COMMENT '叠加方式',
  `begin_time` datetime DEFAULT NULL COMMENT '可用开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '可用结束时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号id',
  `member_name` varchar(40) DEFAULT NULL COMMENT '所属用户',
  `member_id` varchar(32) DEFAULT NULL COMMENT '所属用户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券明细';

-- ----------------------------
-- Records of coupon_list
-- ----------------------------

-- ----------------------------
-- Table structure for device_connect
-- ----------------------------
DROP TABLE IF EXISTS `device_connect`;
CREATE TABLE `device_connect` (
  `connect_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `connect_code` varchar(40) DEFAULT NULL COMMENT '终端号',
  `connect_ip` varchar(40) DEFAULT NULL COMMENT '终端ip',
  `connect_time` datetime DEFAULT NULL COMMENT '连接时间',
  `accept_connect` varchar(10) DEFAULT NULL COMMENT '是否允许',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`connect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备连接';

-- ----------------------------
-- Records of device_connect
-- ----------------------------

-- ----------------------------
-- Table structure for device_connect_message
-- ----------------------------
DROP TABLE IF EXISTS `device_connect_message`;
CREATE TABLE `device_connect_message` (
  `message_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `connect_id` varchar(32) DEFAULT NULL COMMENT 'connect_id',
  `connect_ip` varchar(40) DEFAULT NULL COMMENT '终端ip',
  `connect_time` datetime DEFAULT NULL COMMENT '日志时间',
  `is_sent` varchar(10) DEFAULT NULL COMMENT '是否下发?',
  `connect_message` text COMMENT '接收或发送信息',
  `is_callback` varchar(10) DEFAULT NULL COMMENT '是否反馈',
  `is_deal` varchar(10) DEFAULT NULL COMMENT '是否已处理',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备连接日志';

-- ----------------------------
-- Records of device_connect_message
-- ----------------------------

-- ----------------------------
-- Table structure for device_control
-- ----------------------------
DROP TABLE IF EXISTS `device_control`;
CREATE TABLE `device_control` (
  `control_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `message_type` varchar(20) DEFAULT NULL COMMENT '指令类型',
  `plan_time` datetime DEFAULT NULL COMMENT '计划执行时间',
  `auditing` varchar(20) DEFAULT NULL COMMENT '指令状态',
  `file_name` varchar(80) DEFAULT NULL COMMENT '升级包版本',
  `file_id` varchar(32) DEFAULT NULL COMMENT 'file_id',
  `file_size` float DEFAULT NULL COMMENT '升级包大小',
  `is_success` varchar(80) DEFAULT NULL COMMENT '是否执行成功',
  `call_backmessage` text COMMENT '反馈信息',
  `apply_username` varchar(40) DEFAULT NULL COMMENT '提交人',
  `apply_userid` varchar(32) DEFAULT NULL COMMENT '提交人id',
  `check_username` varchar(40) DEFAULT NULL COMMENT '审核人',
  `check_userid` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`control_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备控制指令';

-- ----------------------------
-- Records of device_control
-- ----------------------------
INSERT INTO `device_control` VALUES ('832092824905507776', '', '1', '2020-07-14 15:04:35', '1', 'ColorPix.exe', '524834626935963894', '11', '0', '', 'a', '', 'a', '', '', 'admin', '2020-07-14 15:05:07', 'admin', '2020-09-07 14:16:26', '0');
INSERT INTO `device_control` VALUES ('913822805555961889', '', null, '2020-09-08 15:54:06', '0', '0171121152053.jpg', '0171121152053.jpg', '11', '0', '', 'asd', '', '', '', '', 'admin', '2020-09-07 15:54:34', null, null, '');

-- ----------------------------
-- Table structure for device_control_message
-- ----------------------------
DROP TABLE IF EXISTS `device_control_message`;
CREATE TABLE `device_control_message` (
  `message_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `message_type` varchar(20) DEFAULT NULL COMMENT '指令类型',
  `auditing` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `connect_message` text COMMENT '指令信息',
  `control_id` varchar(32) DEFAULT NULL COMMENT 'control_id',
  `is_success` varchar(80) DEFAULT NULL COMMENT '是否执行成功',
  `file_id` varchar(32) DEFAULT NULL COMMENT 'file_id',
  `file_name` varchar(80) DEFAULT NULL COMMENT '升级包版本',
  `plan_time` datetime DEFAULT NULL COMMENT '计划执行时间',
  `call_backmessage` text COMMENT '反馈信息',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备控制指令明细';

-- ----------------------------
-- Records of device_control_message
-- ----------------------------
INSERT INTO `device_control_message` VALUES ('931468300736364560', 'Dev00001', '306', '2', '2020-09-19 12:53:35', '{\"msgId\":\"931468300736364560\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364561', 'Dev00001', '306', '2', '2020-09-19 12:53:35', '{\"msgId\":\"931468300736364561\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364562', 'Dev00001', '306', '2', '2020-09-19 12:53:40', '{\"msgId\":\"931468300736364562\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364563', 'Dev00001', '306', '2', '2020-09-19 12:53:45', '{\"msgId\":\"931468300736364563\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364565', 'Dev00001', '301', '1', '2020-09-19 12:54:38', '{\"msgId\":\"931468300736364565\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:54:38', null);
INSERT INTO `device_control_message` VALUES ('931468300736364566', 'Dev00002', '302', '1', '2020-09-19 12:54:40', '{\"msgId\":\"931468300736364566\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:54:40', null);
INSERT INTO `device_control_message` VALUES ('931468300736364567', 'Dev00001', '306', '1', '2020-09-19 12:54:40', '{\"msgId\":\"931468300736364567\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:54:40', null);
INSERT INTO `device_control_message` VALUES ('931468300736364569', 'Dev00002', '307', '1', '2020-09-19 12:55:08', '{\"msgId\":\"931468300736364569\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:55:08', null);
INSERT INTO `device_control_message` VALUES ('931468300736364570', 'Dev00001', '306', '2', '2020-09-19 12:57:22', '{\"msgId\":\"931468300736364570\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364571', 'Dev00001', '306', '2', '2020-09-19 12:57:22', '{\"msgId\":\"931468300736364571\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364572', 'Dev00001', '306', '2', '2020-09-19 12:57:27', '{\"msgId\":\"931468300736364572\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364573', 'Dev00001', '306', '2', '2020-09-19 12:57:32', '{\"msgId\":\"931468300736364573\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364574', 'testDev00001', '306', '1', '2020-09-19 12:57:45', '{\"msgId\":\"931468300736364574\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:57:46', null);
INSERT INTO `device_control_message` VALUES ('931468300736364575', 'Dev00001', '306', '2', '2020-09-19 12:58:02', '{\"msgId\":\"931468300736364575\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364576', 'Dev00001', '306', '2', '2020-09-19 12:58:07', '{\"msgId\":\"931468300736364576\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364577', 'Dev00001', '306', '2', '2020-09-19 12:58:12', '{\"msgId\":\"931468300736364577\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364578', 'Dev00001', '306', '2', '2020-09-19 12:58:50', '{\"msgId\":\"931468300736364578\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364579', 'testDev00001', '306', '1', '2020-09-19 12:58:52', '{\"msgId\":\"931468300736364579\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 12:58:52', null);
INSERT INTO `device_control_message` VALUES ('931468300736364580', 'Dev00001', '306', '2', '2020-09-19 12:58:55', '{\"msgId\":\"931468300736364580\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364581', 'Dev00001', '306', '2', '2020-09-19 12:59:00', '{\"msgId\":\"931468300736364581\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364582', 'Dev00001', '306', '2', '2020-09-19 13:01:37', '{\"msgId\":\"931468300736364582\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364583', 'Dev00001', '306', '2', '2020-09-19 13:01:42', '{\"msgId\":\"931468300736364583\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364584', 'Dev00001', '306', '2', '2020-09-19 13:01:47', '{\"msgId\":\"931468300736364584\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931468300736364585', 'Dev00001', '306', '1', '2020-09-19 13:04:49', '{\"msgId\":\"931468300736364585\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:04:49', null);
INSERT INTO `device_control_message` VALUES ('931468300736364586', 'Dev00001', '306', '1', '2020-09-19 13:04:54', '{\"msgId\":\"931468300736364586\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:04:54', null);
INSERT INTO `device_control_message` VALUES ('931468300736364587', 'Dev00001', '306', '1', '2020-09-19 13:04:59', '{\"msgId\":\"931468300736364587\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:04:59', null);
INSERT INTO `device_control_message` VALUES ('931468300736364588', 'testDev00001', '306', '1', '2020-09-19 13:05:28', '{\"msgId\":\"931468300736364588\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:05:28', null);
INSERT INTO `device_control_message` VALUES ('931468300736364589', 'testDev00001', '306', '1', '2020-09-19 13:07:31', '{\"msgId\":\"931468300736364589\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:31', null);
INSERT INTO `device_control_message` VALUES ('931468300736364590', 'testDev00001', '306', '1', '2020-09-19 13:07:31', '{\"msgId\":\"931468300736364590\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:32', null);
INSERT INTO `device_control_message` VALUES ('931468300736364591', 'testDev00001', '306', '1', '2020-09-19 13:07:36', '{\"msgId\":\"931468300736364591\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:36', null);
INSERT INTO `device_control_message` VALUES ('931468300736364592', 'testDev00001', '306', '1', '2020-09-19 13:07:41', '{\"msgId\":\"931468300736364592\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:42', null);
INSERT INTO `device_control_message` VALUES ('931468300736364594', 'testDev00001', '301', '1', '2020-09-19 13:07:58', '{\"msgId\":\"931468300736364594\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:58', null);
INSERT INTO `device_control_message` VALUES ('931468300736364595', 'testDev00002', '302', '1', '2020-09-19 13:08:01', '{\"msgId\":\"931468300736364595\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:08:01', null);
INSERT INTO `device_control_message` VALUES ('931468300736364596', 'testDev00001', '306', '1', '2020-09-19 13:08:02', '{\"msgId\":\"931468300736364596\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:08:02', null);
INSERT INTO `device_control_message` VALUES ('931468300736364598', 'testDev00002', '307', '1', '2020-09-19 13:08:25', '{\"msgId\":\"931468300736364598\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:08:25', null);
INSERT INTO `device_control_message` VALUES ('931468300736364599', 'testDev00002', '305', '1', '2020-09-19 13:08:39', '{\"msg\":\"支付成功！\",\"msgId\":\"931468300736364599\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:08:40', null);
INSERT INTO `device_control_message` VALUES ('931468300736364601', 'testDev00002', '307', '1', '2020-09-19 13:12:20', '{\"msgId\":\"931468300736364601\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:12:21', null);
INSERT INTO `device_control_message` VALUES ('931468300736364602', 'testDev00002', '305', '1', '2020-09-19 13:12:36', '{\"msg\":\"支付成功！\",\"msgId\":\"931468300736364602\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:12:37', null);
INSERT INTO `device_control_message` VALUES ('931497678312693760', 'testDev00001', '306', '1', '2020-09-19 13:12:27', '{\"msgId\":\"931497678312693760\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:12:27', null);
INSERT INTO `device_control_message` VALUES ('931512487359938560', 'testDev00001', '306', '1', '2020-09-19 13:27:10', '{\"msgId\":\"931512487359938560\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 13:27:11', null);
INSERT INTO `device_control_message` VALUES ('931512487359938562', 'Dev00001', '301', '1', '2020-09-19 15:13:54', '{\"msgId\":\"931512487359938562\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:13:54', null);
INSERT INTO `device_control_message` VALUES ('931512487359938563', 'Dev00002', '302', '1', '2020-09-19 15:13:56', '{\"msgId\":\"931512487359938563\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:13:56', null);
INSERT INTO `device_control_message` VALUES ('931512487359938564', 'Dev00001', '306', '1', '2020-09-19 15:13:56', '{\"msgId\":\"931512487359938564\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:13:57', null);
INSERT INTO `device_control_message` VALUES ('931512487359938567', 'Dev00002', '307', '1', '2020-09-19 15:15:06', '{\"msgId\":\"931512487359938567\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:15:06', null);
INSERT INTO `device_control_message` VALUES ('931512487359938569', 'Dev00001', '306', '2', '2020-09-19 15:29:45', '{\"msgId\":\"931512487359938569\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938570', 'Dev00001', '306', '2', '2020-09-19 15:29:45', '{\"msgId\":\"931512487359938570\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938571', 'Dev00001', '306', '2', '2020-09-19 15:29:50', '{\"msgId\":\"931512487359938571\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938572', 'Dev00001', '306', '2', '2020-09-19 15:29:55', '{\"msgId\":\"931512487359938572\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938573', 'Dev00001', '306', '2', '2020-09-19 15:49:26', '{\"msgId\":\"931512487359938573\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938574', 'Dev00001', '306', '2', '2020-09-19 15:49:26', '{\"msgId\":\"931512487359938574\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938575', 'Dev00001', '306', '2', '2020-09-19 15:49:31', '{\"msgId\":\"931512487359938575\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938576', 'Dev00001', '306', '2', '2020-09-19 15:49:36', '{\"msgId\":\"931512487359938576\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('931512487359938578', 'Dev00001', '301', '1', '2020-09-19 15:50:19', '{\"msgId\":\"931512487359938578\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:20', null);
INSERT INTO `device_control_message` VALUES ('931512487359938579', 'Dev00002', '302', '1', '2020-09-19 15:50:21', '{\"msgId\":\"931512487359938579\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:22', null);
INSERT INTO `device_control_message` VALUES ('931512487359938580', 'Dev00001', '306', '1', '2020-09-19 15:50:22', '{\"msgId\":\"931512487359938580\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:22', null);
INSERT INTO `device_control_message` VALUES ('931512487359938582', 'Dev00002', '307', '1', '2020-09-19 15:50:37', '{\"msgId\":\"931512487359938582\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:37', null);
INSERT INTO `device_control_message` VALUES ('931512487359938606', 'testDev00002', '304', '2', '2020-09-21 10:13:58', '{\"data\":\"[{\\\"add_userid\\\":\\\"\\\",\\\"buy_num\\\":1,\\\"det_id\\\":\\\"931512487359938604\\\",\\\"drug_usage\\\":\\\"冲服，每日2次 x3 天\\\",\\\"fullNameColumn\\\":\\\"\\\",\\\"goods_id\\\":\\\"897455234946220717\\\",\\\"goods_money\\\":0.01,\\\"goods_name\\\":\\\"999三九感冒灵颗粒\\\",\\\"goods_price\\\":0.01,\\\"goods_size\\\":\\\"10g*9袋*1盒\\\",\\\"levelColumn\\\":\\\"\\\",\\\"memo\\\":\\\"\\\",\\\"modify_userid\\\":\\\"\\\",\\\"project_id\\\":\\\"\\\",\\\"record_flag\\\":\\\"\\\",\\\"service_id\\\":\\\"925460637920108567\\\",\\\"textColumn\\\":\\\"\\\",\\\"unit\\\":\\\"盒\\\"},{\\\"add_userid\\\":\\\"\\\",\\\"buy_num\\\":1,\\\"det_id\\\":\\\"931512487359938605\\\",\\\"drug_usage\\\":\\\"口服，每日2次 x3 天\\\",\\\"fullNameColumn\\\":\\\"\\\",\\\"goods_id\\\":\\\"234535456657766346\\\",\\\"goods_money\\\":0.01,\\\"goods_name\\\":\\\"广药白云山复方银翘片（盒）\\\",\\\"goods_price\\\":0.01,\\\"goods_size\\\":\\\"0.5g*36粒*1盒\\\",\\\"levelColumn\\\":\\\"\\\",\\\"memo\\\":\\\"\\\",\\\"modify_userid\\\":\\\"\\\",\\\"project_id\\\":\\\"\\\",\\\"record_flag\\\":\\\"\\\",\\\"service_id\\\":\\\"925460637920108567\\\",\\\"textColumn\\\":\\\"\\\",\\\"unit\\\":\\\"盒\\\"}]\",\"msgId\":\"931512487359938606\",\"typeDes\":\"推送诊疗药品\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603841', 'Dev00001', '306', '2', '2020-09-21 10:21:37', '{\"msgId\":\"934289080637603841\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603842', 'Dev00001', '306', '2', '2020-09-21 10:21:37', '{\"msgId\":\"934289080637603842\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603843', 'Dev00001', '306', '1', '2020-09-21 10:21:42', '{\"msgId\":\"934289080637603843\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:21:42', null);
INSERT INTO `device_control_message` VALUES ('934289080637603844', 'Dev00001', '306', '1', '2020-09-21 10:21:47', '{\"msgId\":\"934289080637603844\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:21:47', null);
INSERT INTO `device_control_message` VALUES ('934289080637603846', 'Dev00001', '301', '1', '2020-09-21 10:22:18', '{\"msgId\":\"934289080637603846\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:22:18', null);
INSERT INTO `device_control_message` VALUES ('934289080637603847', 'Dev00002', '302', '1', '2020-09-21 10:22:20', '{\"msgId\":\"934289080637603847\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:22:20', null);
INSERT INTO `device_control_message` VALUES ('934289080637603848', 'Dev00001', '306', '1', '2020-09-21 10:22:20', '{\"msgId\":\"934289080637603848\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:22:22', null);
INSERT INTO `device_control_message` VALUES ('934289080637603853', 'Dev00002', '307', '1', '2020-09-21 10:25:45', '{\"msgId\":\"934289080637603853\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:25:45', null);
INSERT INTO `device_control_message` VALUES ('934289080637603854', 'Dev00002', '305', '1', '2020-09-21 10:26:15', '{\"msg\":\"支付成功！\",\"msgId\":\"934289080637603854\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:26:16', null);
INSERT INTO `device_control_message` VALUES ('934289080637603855', 'Dev00001', '306', '1', '2020-09-21 10:28:16', '{\"msgId\":\"934289080637603855\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:28:17', null);
INSERT INTO `device_control_message` VALUES ('934289080637603856', 'Dev00001', '306', '1', '2020-09-21 10:28:17', '{\"msgId\":\"934289080637603856\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:28:17', null);
INSERT INTO `device_control_message` VALUES ('934289080637603857', 'Dev00001', '306', '1', '2020-09-21 10:28:21', '{\"msgId\":\"934289080637603857\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:28:22', null);
INSERT INTO `device_control_message` VALUES ('934289080637603858', 'Dev00001', '306', '1', '2020-09-21 10:28:26', '{\"msgId\":\"934289080637603858\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:28:27', null);
INSERT INTO `device_control_message` VALUES ('934289080637603860', 'Dev00001', '301', '1', '2020-09-21 10:29:22', '{\"msgId\":\"934289080637603860\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:22', null);
INSERT INTO `device_control_message` VALUES ('934289080637603861', 'Dev00002', '302', '1', '2020-09-21 10:29:24', '{\"msgId\":\"934289080637603861\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:25', null);
INSERT INTO `device_control_message` VALUES ('934289080637603862', 'Dev00001', '306', '1', '2020-09-21 10:29:24', '{\"msgId\":\"934289080637603862\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:25', null);
INSERT INTO `device_control_message` VALUES ('934289080637603863', 'Dev00001', '306', '2', '2020-09-21 10:29:25', '{\"msgId\":\"934289080637603863\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603865', 'Dev00002', '307', '1', '2020-09-21 10:29:40', '{\"msgId\":\"934289080637603865\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:40', null);
INSERT INTO `device_control_message` VALUES ('934289080637603866', 'Dev00002', '305', '1', '2020-09-21 10:29:54', '{\"msg\":\"支付成功！\",\"msgId\":\"934289080637603866\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:54', null);
INSERT INTO `device_control_message` VALUES ('934289080637603868', 'Dev00002', '307', '1', '2020-09-21 10:30:27', '{\"msgId\":\"934289080637603868\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:30:28', null);
INSERT INTO `device_control_message` VALUES ('934289080637603869', 'Dev00002', '305', '1', '2020-09-21 10:30:39', '{\"msg\":\"支付成功！\",\"msgId\":\"934289080637603869\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:30:39', null);
INSERT INTO `device_control_message` VALUES ('934289080637603870', 'Dev00001', '306', '2', '2020-09-21 10:31:33', '{\"msgId\":\"934289080637603870\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603871', 'Dev00001', '306', '2', '2020-09-21 10:31:33', '{\"msgId\":\"934289080637603871\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603872', 'Dev00001', '306', '2', '2020-09-21 10:31:43', '{\"msgId\":\"934289080637603872\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603873', 'Dev00001', '306', '2', '2020-09-21 10:31:43', '{\"msgId\":\"934289080637603873\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603874', 'Dev00001', '306', '2', '2020-09-21 10:31:48', '{\"msgId\":\"934289080637603874\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603875', 'Dev00001', '306', '2', '2020-09-21 10:31:53', '{\"msgId\":\"934289080637603875\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('934289080637603877', 'Dev00001', '301', '1', '2020-09-21 10:32:15', '{\"msgId\":\"934289080637603877\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:15', null);
INSERT INTO `device_control_message` VALUES ('934289080637603878', 'Dev00002', '302', '1', '2020-09-21 10:32:17', '{\"msgId\":\"934289080637603878\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:17', null);
INSERT INTO `device_control_message` VALUES ('934289080637603879', 'Dev00001', '306', '1', '2020-09-21 10:32:17', '{\"msgId\":\"934289080637603879\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:18', null);
INSERT INTO `device_control_message` VALUES ('934289080637603881', 'Dev00002', '307', '1', '2020-09-21 10:32:36', '{\"msgId\":\"934289080637603881\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:37', null);
INSERT INTO `device_control_message` VALUES ('934289080637603882', 'Dev00002', '305', '1', '2020-09-21 10:32:47', '{\"msg\":\"支付成功！\",\"msgId\":\"934289080637603882\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:48', null);
INSERT INTO `device_control_message` VALUES ('937227662903017474', 'Dev00001', '301', '1', '2020-09-23 10:46:35', '{\"msgId\":\"937227662903017474\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:50:02', null);
INSERT INTO `device_control_message` VALUES ('937227662903017476', 'Dev00001', '301', '1', '2020-09-23 10:50:50', '{\"msgId\":\"937227662903017476\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:50:51', null);
INSERT INTO `device_control_message` VALUES ('937227662903017477', 'Dev00002', '302', '1', '2020-09-23 10:50:52', '{\"msgId\":\"937227662903017477\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:50:53', null);
INSERT INTO `device_control_message` VALUES ('937227662903017478', 'Dev00001', '306', '1', '2020-09-23 10:50:52', '{\"msgId\":\"937227662903017478\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:50:53', null);
INSERT INTO `device_control_message` VALUES ('937227662903017481', 'Dev00002', '307', '1', '2020-09-23 10:51:35', '{\"msgId\":\"937227662903017481\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:51:35', null);
INSERT INTO `device_control_message` VALUES ('937227662903017482', 'Dev00002', '305', '1', '2020-09-23 10:51:51', '{\"msg\":\"支付成功！\",\"msgId\":\"937227662903017482\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 10:51:51', null);
INSERT INTO `device_control_message` VALUES ('937643054960066562', 'Dev00001', '306', '2', '2020-09-23 17:29:23', '{\"msgId\":\"937643054960066562\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('937643054960066563', 'Dev00001', '306', '2', '2020-09-23 17:29:23', '{\"msgId\":\"937643054960066563\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('937643054960066564', 'Dev00001', '306', '2', '2020-09-23 17:29:28', '{\"msgId\":\"937643054960066564\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('937643054960066565', 'Dev00001', '306', '2', '2020-09-23 17:29:33', '{\"msgId\":\"937643054960066565\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('937643054960066566', 'Dev00001', '306', '2', '2020-09-23 17:29:40', '{\"msgId\":\"937643054960066566\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('937643054960066567', 'Dev00001', '306', '1', '2020-09-23 17:29:45', '{\"msgId\":\"937643054960066567\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:29:49', null);
INSERT INTO `device_control_message` VALUES ('937643054960066568', 'Dev00001', '306', '1', '2020-09-23 17:29:50', '{\"msgId\":\"937643054960066568\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:29:51', null);
INSERT INTO `device_control_message` VALUES ('937643054960066570', 'Dev00001', '301', '1', '2020-09-23 17:30:06', '{\"msgId\":\"937643054960066570\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:07', null);
INSERT INTO `device_control_message` VALUES ('937643054960066571', 'Dev00002', '302', '1', '2020-09-23 17:30:08', '{\"msgId\":\"937643054960066571\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:09', null);
INSERT INTO `device_control_message` VALUES ('937643054960066572', 'Dev00001', '306', '1', '2020-09-23 17:30:09', '{\"msgId\":\"937643054960066572\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:09', null);
INSERT INTO `device_control_message` VALUES ('937643054960066574', 'Dev00002', '307', '1', '2020-09-23 17:30:26', '{\"msgId\":\"937643054960066574\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:26', null);
INSERT INTO `device_control_message` VALUES ('937643054960066575', 'Dev00002', '305', '1', '2020-09-23 17:30:36', '{\"msg\":\"支付成功！\",\"msgId\":\"937643054960066575\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:37', null);
INSERT INTO `device_control_message` VALUES ('943536643444113409', 'Dev00001', '301', '2', '2020-09-27 15:54:28', '{\"msgId\":\"943536643444113409\",\"typeDes\":\"鎵爜鍙栧彿\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113410', 'Dev00001', '306', '2', '2020-09-27 15:59:07', '{\"msgId\":\"943536643444113410\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113411', 'Dev00001', '306', '2', '2020-09-27 15:59:07', '{\"msgId\":\"943536643444113411\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113412', 'Dev00001', '306', '1', '2020-09-27 15:59:12', '{\"msgId\":\"943536643444113412\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 15:59:14', null);
INSERT INTO `device_control_message` VALUES ('943536643444113413', 'Dev00001', '306', '1', '2020-09-27 15:59:17', '{\"msgId\":\"943536643444113413\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 15:59:17', null);
INSERT INTO `device_control_message` VALUES ('943536643444113415', 'Dev00001', '301', '1', '2020-09-27 16:00:26', '{\"msgId\":\"943536643444113415\",\"typeDes\":\"鎵爜鍙栧彿\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:00:27', null);
INSERT INTO `device_control_message` VALUES ('943536643444113416', 'Dev00002', '302', '1', '2020-09-27 16:00:28', '{\"msgId\":\"943536643444113416\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"鎵爜寮�闂�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:00:29', null);
INSERT INTO `device_control_message` VALUES ('943536643444113417', 'Dev00001', '306', '1', '2020-09-27 16:00:29', '{\"msgId\":\"943536643444113417\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:00:29', null);
INSERT INTO `device_control_message` VALUES ('943536643444113420', 'Dev00002', '307', '1', '2020-09-27 16:01:35', '{\"msgId\":\"943536643444113420\",\"pay_type\":\"1\",\"typeDes\":\"鑾峰彇鏀粯閾炬帴鍦板潃\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:01:36', null);
INSERT INTO `device_control_message` VALUES ('943536643444113421', 'Dev00002', '305', '1', '2020-09-27 16:02:06', '{\"msg\":\"鏀粯鎴愬姛锛�\",\"msgId\":\"943536643444113421\",\"typeDes\":\"寮�濮嬭棰戦棶璇�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:02:06', null);
INSERT INTO `device_control_message` VALUES ('943536643444113422', 'Dev00001', '402', '1', '2020-09-27 16:02:40', '{\"msgId\":\"943536643444113422\",\"typeDes\":\"鍐呯闀�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:02:41', null);
INSERT INTO `device_control_message` VALUES ('943536643444113423', 'Dev00002', '402', '1', '2020-09-27 16:02:40', '{\"msgId\":\"943536643444113423\",\"typeDes\":\"鍐呯闀�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:02:41', null);
INSERT INTO `device_control_message` VALUES ('943536643444113424', 'Dev00002', '404', '1', '2020-09-27 16:03:20', '{\"msgId\":\"943536643444113424\",\"typeDes\":\"娴嬮噺蹇冪巼\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:03:21', null);
INSERT INTO `device_control_message` VALUES ('943536643444113426', 'Dev00001', '403', '1', '2020-09-27 16:03:30', '{\"msgId\":\"943536643444113426\",\"typeDes\":\"鐢靛瓙鍚瘖\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:03:31', null);
INSERT INTO `device_control_message` VALUES ('943536643444113427', 'Dev00002', '403', '1', '2020-09-27 16:03:30', '{\"msgId\":\"943536643444113427\",\"typeDes\":\"鐢靛瓙鍚瘖\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:03:31', null);
INSERT INTO `device_control_message` VALUES ('943536643444113428', 'Dev00001', '306', '2', '2020-09-27 16:05:08', '{\"msgId\":\"943536643444113428\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113429', 'Dev00001', '306', '2', '2020-09-27 16:05:08', '{\"msgId\":\"943536643444113429\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113430', 'Dev00001', '306', '2', '2020-09-27 16:05:13', '{\"msgId\":\"943536643444113430\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113431', 'Dev00001', '306', '2', '2020-09-27 16:05:18', '{\"msgId\":\"943536643444113431\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113462', 'Dev00001', '306', '2', '2020-09-27 16:37:40', '{\"msgId\":\"943536643444113462\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113463', 'Dev00001', '306', '2', '2020-09-27 16:37:40', '{\"msgId\":\"943536643444113463\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113464', 'Dev00001', '306', '2', '2020-09-27 16:37:45', '{\"msgId\":\"943536643444113464\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113465', 'Dev00001', '306', '2', '2020-09-27 16:37:50', '{\"msgId\":\"943536643444113465\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943536643444113471', 'Dev00001', '301', '1', '2020-09-27 16:38:26', '{\"msgId\":\"943536643444113471\",\"typeDes\":\"鎵爜鍙栧彿\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:38:26', null);
INSERT INTO `device_control_message` VALUES ('943536643444113472', 'Dev00002', '302', '1', '2020-09-27 16:38:28', '{\"msgId\":\"943536643444113472\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"鎵爜寮�闂�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:38:28', null);
INSERT INTO `device_control_message` VALUES ('943536643444113473', 'Dev00001', '306', '1', '2020-09-27 16:38:28', '{\"msgId\":\"943536643444113473\",\"typeDes\":\"鍒锋柊鎺掑彿闃熷垪\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:38:29', null);
INSERT INTO `device_control_message` VALUES ('943536643444113480', 'Dev00002', '307', '1', '2020-09-27 16:39:01', '{\"msgId\":\"943536643444113480\",\"pay_type\":\"1\",\"typeDes\":\"鑾峰彇鏀粯閾炬帴鍦板潃\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:39:01', null);
INSERT INTO `device_control_message` VALUES ('943536643444113481', 'Dev00002', '305', '1', '2020-09-27 16:39:13', '{\"msg\":\"鏀粯鎴愬姛锛�\",\"msgId\":\"943536643444113481\",\"typeDes\":\"寮�濮嬭棰戦棶璇�\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:39:14', null);
INSERT INTO `device_control_message` VALUES ('943598284814761985', 'Dev00002', '307', '1', '2020-09-27 16:52:04', '{\"msgId\":\"943598284814761985\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:05', null);
INSERT INTO `device_control_message` VALUES ('943598284814761986', 'Dev00002', '305', '1', '2020-09-27 16:52:20', '{\"msg\":\"支付成功！\",\"msgId\":\"943598284814761986\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:20', null);
INSERT INTO `device_control_message` VALUES ('943598284814761987', 'Dev00001', '402', '1', '2020-09-27 16:52:32', '{\"msgId\":\"943598284814761987\",\"roomId\":\"943598284814761984\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:33', null);
INSERT INTO `device_control_message` VALUES ('943598284814761988', 'Dev00002', '402', '1', '2020-09-27 16:52:32', '{\"msgId\":\"943598284814761988\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:33', null);
INSERT INTO `device_control_message` VALUES ('943598284814761989', 'Dev00001', '403', '1', '2020-09-27 16:52:42', '{\"msgId\":\"943598284814761989\",\"roomId\":\"943598284814761984\",\"typeDes\":\"电子听诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:42', null);
INSERT INTO `device_control_message` VALUES ('943598284814761990', 'Dev00002', '403', '1', '2020-09-27 16:52:42', '{\"msgId\":\"943598284814761990\",\"typeDes\":\"电子听诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:42', null);
INSERT INTO `device_control_message` VALUES ('943598284814762026', 'Dev00001', '306', '2', '2020-09-27 17:21:06', '{\"msgId\":\"943598284814762026\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943598284814762027', 'Dev00001', '306', '2', '2020-09-27 17:21:06', '{\"msgId\":\"943598284814762027\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943598284814762028', 'Dev00001', '306', '2', '2020-09-27 17:21:11', '{\"msgId\":\"943598284814762028\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943598284814762029', 'Dev00001', '306', '2', '2020-09-27 17:21:16', '{\"msgId\":\"943598284814762029\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943637197218471937', 'Dev00001', '301', '1', '2020-09-27 17:29:32', '{\"msgId\":\"943637197218471937\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 17:29:33', null);
INSERT INTO `device_control_message` VALUES ('943637197218471938', 'Dev00002', '302', '1', '2020-09-27 17:29:35', '{\"msgId\":\"943637197218471938\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 17:30:08', null);
INSERT INTO `device_control_message` VALUES ('943637197218471944', 'Dev00001', '306', '1', '2020-09-27 17:30:07', '{\"msgId\":\"943637197218471944\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 17:30:08', null);
INSERT INTO `device_control_message` VALUES ('943637197218471945', 'Dev00001', '306', '1', '2020-09-27 17:30:09', '{\"msgId\":\"943637197218471945\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 17:30:10', null);
INSERT INTO `device_control_message` VALUES ('943637197218471947', 'Dev00002', '307', '2', '2020-09-27 17:32:33', '{\"msgId\":\"943637197218471947\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943637197218471948', 'Dev00002', '305', '1', '2020-09-27 17:33:04', '{\"msg\":\"支付成功！\",\"msgId\":\"943637197218471948\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 17:33:10', null);
INSERT INTO `device_control_message` VALUES ('943653913231196160', 'Dev00001', '306', '1', '2020-09-27 18:06:55', '{\"msgId\":\"943653913231196160\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:06:56', null);
INSERT INTO `device_control_message` VALUES ('943653913231196161', 'Dev00001', '306', '1', '2020-09-27 18:07:00', '{\"msgId\":\"943653913231196161\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:07:01', null);
INSERT INTO `device_control_message` VALUES ('943653913231196162', 'Dev00001', '306', '1', '2020-09-27 18:07:05', '{\"msgId\":\"943653913231196162\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:07:06', null);
INSERT INTO `device_control_message` VALUES ('943683204908171265', 'Dev00001', '301', '1', '2020-09-27 18:18:00', '{\"msgId\":\"943683204908171265\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:01', null);
INSERT INTO `device_control_message` VALUES ('943683204908171266', 'Dev00002', '302', '1', '2020-09-27 18:18:18', '{\"msgId\":\"943683204908171266\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:20', null);
INSERT INTO `device_control_message` VALUES ('943683204908171267', 'Dev00001', '306', '1', '2020-09-27 18:18:19', '{\"msgId\":\"943683204908171267\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:20', null);
INSERT INTO `device_control_message` VALUES ('943683204908171268', 'Dev00001', '306', '2', '2020-09-27 18:18:19', '{\"msgId\":\"943683204908171268\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171269', 'Dev00001', '306', '2', '2020-09-27 18:18:20', '{\"msgId\":\"943683204908171269\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171272', 'Dev00002', '307', '1', '2020-09-27 18:18:50', '{\"msgId\":\"943683204908171272\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:50', null);
INSERT INTO `device_control_message` VALUES ('943683204908171273', 'Dev00002', '305', '2', '2020-09-27 18:19:26', '{\"msg\":\"支付成功！\",\"msgId\":\"943683204908171273\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171274', 'Dev00001', '306', '2', '2020-09-27 18:21:59', '{\"msgId\":\"943683204908171274\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171275', 'Dev00001', '306', '2', '2020-09-27 18:21:59', '{\"msgId\":\"943683204908171275\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171276', 'Dev00001', '306', '2', '2020-09-27 18:22:04', '{\"msgId\":\"943683204908171276\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171277', 'Dev00001', '306', '2', '2020-09-27 18:22:09', '{\"msgId\":\"943683204908171277\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171279', 'Dev00001', '301', '1', '2020-09-27 18:46:41', '{\"msgId\":\"943683204908171279\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:46:41', null);
INSERT INTO `device_control_message` VALUES ('943683204908171280', 'Dev00002', '302', '2', '2020-09-27 18:46:44', '{\"msgId\":\"943683204908171280\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171281', 'Dev00001', '306', '1', '2020-09-27 18:49:44', '{\"msgId\":\"943683204908171281\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:49:45', null);
INSERT INTO `device_control_message` VALUES ('943683204908171282', 'Dev00001', '306', '1', '2020-09-27 18:50:26', '{\"msgId\":\"943683204908171282\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:26', null);
INSERT INTO `device_control_message` VALUES ('943683204908171283', 'Dev00001', '306', '1', '2020-09-27 18:50:28', '{\"msgId\":\"943683204908171283\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:29', null);
INSERT INTO `device_control_message` VALUES ('943683204908171284', 'Dev00001', '306', '1', '2020-09-27 18:50:40', '{\"msgId\":\"943683204908171284\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:40', null);
INSERT INTO `device_control_message` VALUES ('943683204908171285', 'Dev00001', '306', '1', '2020-09-27 18:50:55', '{\"msgId\":\"943683204908171285\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:55', null);
INSERT INTO `device_control_message` VALUES ('943683204908171286', 'Dev00001', '306', '1', '2020-09-27 18:50:56', '{\"msgId\":\"943683204908171286\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:56', null);
INSERT INTO `device_control_message` VALUES ('943683204908171287', 'Dev00001', '306', '1', '2020-09-27 18:50:57', '{\"msgId\":\"943683204908171287\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:50:57', null);
INSERT INTO `device_control_message` VALUES ('943683204908171288', 'Dev00001', '306', '1', '2020-09-27 18:52:17', '{\"msgId\":\"943683204908171288\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:52:17', null);
INSERT INTO `device_control_message` VALUES ('943683204908171289', 'Dev00001', '306', '1', '2020-09-27 18:52:55', '{\"msgId\":\"943683204908171289\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:52:55', null);
INSERT INTO `device_control_message` VALUES ('943683204908171290', 'Dev00001', '306', '1', '2020-09-27 18:52:56', '{\"msgId\":\"943683204908171290\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:52:57', null);
INSERT INTO `device_control_message` VALUES ('943683204908171291', 'Dev00001', '306', '1', '2020-09-27 18:52:57', '{\"msgId\":\"943683204908171291\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:52:57', null);
INSERT INTO `device_control_message` VALUES ('943683204908171292', 'Dev00001', '306', '1', '2020-09-27 18:52:57', '{\"msgId\":\"943683204908171292\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:52:59', null);
INSERT INTO `device_control_message` VALUES ('943683204908171293', 'Dev00001', '306', '1', '2020-09-27 18:53:02', '{\"msgId\":\"943683204908171293\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:53:02', null);
INSERT INTO `device_control_message` VALUES ('943683204908171294', 'Dev00001', '306', '1', '2020-09-27 18:53:02', '{\"msgId\":\"943683204908171294\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:53:04', null);
INSERT INTO `device_control_message` VALUES ('943683204908171295', 'Dev00001', '306', '1', '2020-09-27 18:53:06', '{\"msgId\":\"943683204908171295\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:53:07', null);
INSERT INTO `device_control_message` VALUES ('943683204908171296', 'Dev00001', '306', '1', '2020-09-27 18:53:07', '{\"msgId\":\"943683204908171296\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:53:08', null);
INSERT INTO `device_control_message` VALUES ('943683204908171298', 'Dev00001', '301', '1', '2020-09-27 18:54:20', '{\"msgId\":\"943683204908171298\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:54:21', null);
INSERT INTO `device_control_message` VALUES ('943683204908171299', 'Dev00002', '302', '1', '2020-09-27 18:54:24', '{\"msgId\":\"943683204908171299\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:56:54', null);
INSERT INTO `device_control_message` VALUES ('943683204908171300', 'Dev00001', '306', '1', '2020-09-27 18:56:54', '{\"msgId\":\"943683204908171300\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:56:55', null);
INSERT INTO `device_control_message` VALUES ('943683204908171302', 'Dev00002', '307', '1', '2020-09-27 18:57:14', '{\"msgId\":\"943683204908171302\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:57:15', null);
INSERT INTO `device_control_message` VALUES ('943683204908171303', 'Dev00002', '305', '1', '2020-09-27 18:57:36', '{\"msg\":\"支付成功！\",\"msgId\":\"943683204908171303\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:57:37', null);
INSERT INTO `device_control_message` VALUES ('943683204908171304', 'Dev00001', '402', '1', '2020-09-27 18:58:13', '{\"msgId\":\"943683204908171304\",\"roomId\":\"943683204908171301\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:58:13', null);
INSERT INTO `device_control_message` VALUES ('943683204908171305', 'Dev00002', '402', '1', '2020-09-27 18:58:13', '{\"msgId\":\"943683204908171305\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 18:58:13', null);
INSERT INTO `device_control_message` VALUES ('943683204908171306', 'Dev00001', '306', '2', '2020-09-27 19:08:40', '{\"msgId\":\"943683204908171306\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171307', 'Dev00001', '306', '2', '2020-09-27 19:08:40', '{\"msgId\":\"943683204908171307\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171308', 'Dev00001', '306', '1', '2020-09-27 19:08:46', '{\"msgId\":\"943683204908171308\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:08:46', null);
INSERT INTO `device_control_message` VALUES ('943683204908171309', 'Dev00001', '306', '1', '2020-09-27 19:08:50', '{\"msgId\":\"943683204908171309\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:08:51', null);
INSERT INTO `device_control_message` VALUES ('943683204908171311', 'Dev00001', '301', '1', '2020-09-27 19:09:07', '{\"msgId\":\"943683204908171311\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:09:08', null);
INSERT INTO `device_control_message` VALUES ('943683204908171312', 'Dev00002', '302', '1', '2020-09-27 19:09:11', '{\"msgId\":\"943683204908171312\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:09:31', null);
INSERT INTO `device_control_message` VALUES ('943683204908171313', 'Dev00001', '306', '1', '2020-09-27 19:09:31', '{\"msgId\":\"943683204908171313\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:09:31', null);
INSERT INTO `device_control_message` VALUES ('943683204908171315', 'Dev00002', '307', '1', '2020-09-27 19:09:59', '{\"msgId\":\"943683204908171315\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:10:01', null);
INSERT INTO `device_control_message` VALUES ('943683204908171316', 'Dev00002', '305', '1', '2020-09-27 19:10:13', '{\"msg\":\"支付成功！\",\"msgId\":\"943683204908171316\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:10:14', null);
INSERT INTO `device_control_message` VALUES ('943683204908171317', 'Dev00001', '402', '1', '2020-09-27 19:10:49', '{\"msgId\":\"943683204908171317\",\"roomId\":\"943683204908171314\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:10:50', null);
INSERT INTO `device_control_message` VALUES ('943683204908171318', 'Dev00002', '402', '1', '2020-09-27 19:10:49', '{\"msgId\":\"943683204908171318\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:10:50', null);
INSERT INTO `device_control_message` VALUES ('943683204908171319', 'Dev00001', '306', '1', '2020-09-27 19:20:07', '{\"msgId\":\"943683204908171319\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:20:08', null);
INSERT INTO `device_control_message` VALUES ('943683204908171320', 'Dev00001', '306', '1', '2020-09-27 19:20:07', '{\"msgId\":\"943683204908171320\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:20:08', null);
INSERT INTO `device_control_message` VALUES ('943683204908171321', 'Dev00001', '306', '1', '2020-09-27 19:20:12', '{\"msgId\":\"943683204908171321\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:20:13', null);
INSERT INTO `device_control_message` VALUES ('943683204908171322', 'Dev00001', '306', '1', '2020-09-27 19:20:17', '{\"msgId\":\"943683204908171322\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:20:18', null);
INSERT INTO `device_control_message` VALUES ('943683204908171324', 'Dev00001', '301', '1', '2020-09-27 19:21:02', '{\"msgId\":\"943683204908171324\",\"typeDes\":\"扫码取号\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:03', null);
INSERT INTO `device_control_message` VALUES ('943683204908171325', 'Dev00002', '302', '1', '2020-09-27 19:21:10', '{\"msgId\":\"943683204908171325\",\"userId\":\"ouYzq0Kt_N8YXmcQxs-p_CnINAA4\",\"typeDes\":\"扫码开门\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:11', null);
INSERT INTO `device_control_message` VALUES ('943683204908171326', 'Dev00001', '306', '1', '2020-09-27 19:21:10', '{\"msgId\":\"943683204908171326\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:11', null);
INSERT INTO `device_control_message` VALUES ('943683204908171327', 'Dev00001', '306', '1', '2020-09-27 19:21:11', '{\"msgId\":\"943683204908171327\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:11', null);
INSERT INTO `device_control_message` VALUES ('943683204908171329', 'Dev00002', '307', '1', '2020-09-27 19:21:25', '{\"msgId\":\"943683204908171329\",\"pay_type\":\"1\",\"typeDes\":\"获取支付链接地址\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:26', null);
INSERT INTO `device_control_message` VALUES ('943683204908171330', 'Dev00002', '305', '1', '2020-09-27 19:21:42', '{\"msg\":\"支付成功！\",\"msgId\":\"943683204908171330\",\"typeDes\":\"开始视频问诊\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:42', null);
INSERT INTO `device_control_message` VALUES ('943683204908171331', 'Dev00001', '402', '1', '2020-09-27 19:21:58', '{\"msgId\":\"943683204908171331\",\"roomId\":\"943683204908171328\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:59', null);
INSERT INTO `device_control_message` VALUES ('943683204908171332', 'Dev00002', '402', '1', '2020-09-27 19:21:58', '{\"msgId\":\"943683204908171332\",\"typeDes\":\"内窥镜\"}', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:59', null);
INSERT INTO `device_control_message` VALUES ('943683204908171333', 'Dev00001', '306', '2', '2020-09-27 19:23:37', '{\"msgId\":\"943683204908171333\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171334', 'Dev00001', '306', '2', '2020-09-27 19:23:37', '{\"msgId\":\"943683204908171334\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171335', 'Dev00001', '306', '2', '2020-09-27 19:23:42', '{\"msgId\":\"943683204908171335\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_control_message` VALUES ('943683204908171336', 'Dev00001', '306', '2', '2020-09-27 19:23:47', '{\"msgId\":\"943683204908171336\",\"typeDes\":\"刷新排号队列\"}', null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for device_fault
-- ----------------------------
DROP TABLE IF EXISTS `device_fault`;
CREATE TABLE `device_fault` (
  `fault_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '管理状态',
  `fault_name` varchar(80) DEFAULT NULL COMMENT '告警名称',
  `fault_message` text COMMENT '告警信息',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `deal_username` varchar(40) DEFAULT NULL COMMENT '处理人',
  `deal_userid` varchar(32) DEFAULT NULL COMMENT '处理人id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `begin_time` datetime DEFAULT NULL COMMENT '停机开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '停机结束时间',
  PRIMARY KEY (`fault_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警记录';

-- ----------------------------
-- Records of device_fault
-- ----------------------------

-- ----------------------------
-- Table structure for device_group
-- ----------------------------
DROP TABLE IF EXISTS `device_group`;
CREATE TABLE `device_group` (
  `group_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `group_name` varchar(80) DEFAULT NULL COMMENT '分组名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分组';

-- ----------------------------
-- Records of device_group
-- ----------------------------
INSERT INTO `device_group` VALUES ('1001', '', 'zu1', '', 'admin', '2020-06-22 06:56:22', null, null, '0');

-- ----------------------------
-- Table structure for device_group_device
-- ----------------------------
DROP TABLE IF EXISTS `device_group_device`;
CREATE TABLE `device_group_device` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `group_id` varchar(32) DEFAULT NULL COMMENT 'group_id',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分组明细';

-- ----------------------------
-- Records of device_group_device
-- ----------------------------
INSERT INTO `device_group_device` VALUES ('829420788311556598', '798408908229969478', null, null, null, null, null, null, '1001');

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `device_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `type_id` varchar(32) DEFAULT NULL COMMENT 'type_id',
  `position_id` varchar(32) DEFAULT NULL COMMENT '点位_id',
  `position_code` varchar(40) DEFAULT NULL COMMENT '点位编号',
  `group_id` varchar(32) DEFAULT NULL COMMENT 'group_id',
  `group_name` varchar(60) DEFAULT NULL COMMENT '设备分组',
  `auditing` varchar(20) DEFAULT NULL COMMENT '管理状态',
  `sell_ststue` varchar(20) DEFAULT NULL COMMENT '售卖状态',
  `online_ststue` varchar(20) DEFAULT NULL COMMENT '在线状态',
  `temperature_value` float DEFAULT NULL COMMENT '温度',
  `humidity_value` float DEFAULT NULL COMMENT '湿度',
  `soft_version` varchar(200) DEFAULT NULL COMMENT '软件版本',
  `advert_version` varchar(200) DEFAULT NULL COMMENT '广告版本',
  `dbm_value` varchar(60) DEFAULT NULL COMMENT '信号强度',
  `device_kind` varchar(60) DEFAULT NULL COMMENT '设备类型',
  `device_name` varchar(80) DEFAULT NULL COMMENT '设备名称',
  `device_code` varchar(40) DEFAULT NULL COMMENT '设备编号',
  `device_factory` varchar(80) DEFAULT NULL COMMENT '生产厂家',
  `device_type` varchar(80) DEFAULT NULL COMMENT '设备型号',
  `device_size` varchar(200) DEFAULT NULL COMMENT '设备规格',
  `device_price` float DEFAULT NULL COMMENT '出厂价',
  `out_date` datetime DEFAULT NULL COMMENT '出厂日期',
  `install_date` datetime DEFAULT NULL COMMENT '安装日期',
  `use_date` datetime DEFAULT NULL COMMENT '启用日期',
  `use_age` int(11) DEFAULT NULL COMMENT '使用寿命',
  `device_weight` float DEFAULT NULL COMMENT '设备重量(KG)',
  `device_energy` float DEFAULT NULL COMMENT '能耗(KWH/日)',
  `cargo_num` int(11) DEFAULT NULL COMMENT '货道容量',
  `has_stock` varchar(10) DEFAULT NULL COMMENT '是否有备库',
  `company_name` varchar(60) DEFAULT NULL COMMENT '运营商户',
  `company_id` varchar(32) DEFAULT NULL COMMENT '运营商户id',
  `fix_ip` varchar(40) DEFAULT NULL COMMENT '限制ip',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '管理部门',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '管理部门id',
  `temperature_status` varchar(20) DEFAULT NULL COMMENT '温控状态',
  `temperature_type` varchar(20) DEFAULT NULL COMMENT '温控模式',
  `temperature_set` float DEFAULT NULL COMMENT '目标温度',
  `humidity_set` float DEFAULT NULL COMMENT '目标湿度',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `dor_opened` varchar(10) DEFAULT '0' COMMENT '是否已开门 0：否  1：是',
  `screen_ratio` varchar(32) DEFAULT NULL COMMENT '分辨率',
  `system_version` varchar(32) DEFAULT NULL COMMENT '操作系统',
  `mem_info` varchar(32) DEFAULT NULL COMMENT '内存',
  `pic_url` varchar(32) DEFAULT NULL COMMENT '图片',
  `mem_use` varchar(32) DEFAULT NULL COMMENT '内存使用',
  `disk_use` varchar(32) DEFAULT NULL COMMENT '硬盘使用',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备卡片';

-- ----------------------------
-- Records of device_info
-- ----------------------------
INSERT INTO `device_info` VALUES ('825972152911127298', null, '798134099042443865', null, null, null, null, '0', null, null, null, null, null, null, null, 'medicine', '售药1号', null, '维蓝信息', '室外用', '2*1*1米', null, null, null, null, '3', '200', '1', null, '1', null, null, null, null, null, null, 'cool', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_info` VALUES ('829420788311558047', null, '798134099042443865', null, null, null, null, '0', null, null, null, null, null, null, null, 'medicine', '售药1号', null, '维蓝信息', '室外用', '2*1*1米', null, null, null, null, '3', '200', '1', null, '1', null, null, null, null, null, null, 'cool', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `device_info` VALUES ('893136353271611979', '', '893002831328166514', '', '', '', '', '1', '', '', null, null, '', '', '', 'treatment', '智慧问诊V1.0', 'Dev00001', '广州市拓维信息有限公司', '外控设备', '', null, '2020-09-04 09:54:42', '2020-09-04 09:54:57', '2020-09-04 09:55:09', '3', null, null, null, '0', '', '', '', '运营中心', '10041002', '', 'cool', null, null, '', '', '2020-09-04 09:55:25', 'admin', '2020-08-26 15:05:44', '', '1', null, null, null, null, null, null);
INSERT INTO `device_info` VALUES ('893136353271611988', '', '893002831328166514', '', '', '', '', '1', '', '', null, null, '', '', '', 'treatment', '智慧问诊V1.0', 'Dev00002', '广州市拓维信息有限公司', '内控设备', '', null, '2020-08-26 00:00:00', '2020-09-04 09:55:01', '2020-08-26 00:00:00', '3', null, null, null, '0', '', '', '', '运营中心', '10041002', '', 'cool', null, null, '', '', '2020-09-04 09:55:28', 'admin', '2020-09-03 14:22:12', '', '1', null, null, null, null, null, null);
INSERT INTO `device_info` VALUES ('917948293442658371', '', '893002831328166514', '', '', '', '', '1', '', '', null, null, '', '', '', 'treatment', '智慧问诊V1.0', 'testDev00001', '广州市拓维信息有限公司', '外控设备', '', null, '2020-09-04 00:00:00', '2020-09-04 00:00:00', '2020-09-04 00:00:00', '3', null, null, null, '0', '', '', '', '运营中心', '10041002', '', 'cool', null, null, '', 'admin', '2020-09-10 10:56:49', 'admin', '2020-09-10 10:57:22', '', '0', null, null, null, null, null, null);
INSERT INTO `device_info` VALUES ('917948293442658372', '', '893002831328166514', '', '', '', '', '1', '', '', null, null, '', '', '', 'treatment', '智慧问诊V1.0', 'testDev00002', '广州市拓维信息有限公司', '内控设备', '', null, '2020-08-26 00:00:00', '2020-09-04 00:00:00', '2020-08-26 00:00:00', '3', null, null, null, '0', '', '', '', '运营中心', '10041002', '', 'cool', null, null, '', 'admin', '2020-09-10 10:56:49', 'admin', '2020-09-10 10:57:40', '', '1', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for device_info_cargo
-- ----------------------------
DROP TABLE IF EXISTS `device_info_cargo`;
CREATE TABLE `device_info_cargo` (
  `cargo_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `group_name` varchar(20) DEFAULT NULL COMMENT '组柜',
  `cargo_code` varchar(60) DEFAULT NULL COMMENT '货道编号',
  `cargo_level` int(11) DEFAULT NULL COMMENT '层号',
  `cargo_column` int(11) DEFAULT NULL COMMENT '列号',
  `merge_column` varchar(50) DEFAULT NULL COMMENT '合并列',
  `safe_num` int(11) DEFAULT NULL COMMENT '安全库存',
  `max_num` int(11) DEFAULT NULL COMMENT '最大容量',
  `cargo_status` varchar(20) DEFAULT NULL COMMENT '状态',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`cargo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备货道';

-- ----------------------------
-- Records of device_info_cargo
-- ----------------------------

-- ----------------------------
-- Table structure for device_info_cargo_store
-- ----------------------------
DROP TABLE IF EXISTS `device_info_cargo_store`;
CREATE TABLE `device_info_cargo_store` (
  `store_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `cargo_id` varchar(32) DEFAULT NULL COMMENT 'cargo_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `store_code` varchar(200) DEFAULT NULL COMMENT '仓库号',
  `store_num` int(11) DEFAULT NULL COMMENT '库存数量',
  `machine_num` int(11) DEFAULT NULL COMMENT '实际数量',
  `sell_price` float DEFAULT NULL COMMENT '售价',
  `goods_price` float DEFAULT NULL COMMENT '指导价',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备货道库存';

-- ----------------------------
-- Records of device_info_cargo_store
-- ----------------------------

-- ----------------------------
-- Table structure for device_info_part
-- ----------------------------
DROP TABLE IF EXISTS `device_info_part`;
CREATE TABLE `device_info_part` (
  `part_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `part_code` varchar(40) DEFAULT NULL COMMENT '部件编号',
  `part_name` varchar(60) DEFAULT NULL COMMENT '部件名称',
  `part_status` varchar(20) DEFAULT NULL COMMENT '部件状态',
  `part_descript` varchar(400) DEFAULT NULL COMMENT '部件描述',
  `part_factory` varchar(80) DEFAULT NULL COMMENT '厂家',
  `part_type` varchar(80) DEFAULT NULL COMMENT '型号',
  `part_size` varchar(80) DEFAULT NULL COMMENT '规格',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备部件';

-- ----------------------------
-- Records of device_info_part
-- ----------------------------

-- ----------------------------
-- Table structure for device_rent
-- ----------------------------
DROP TABLE IF EXISTS `device_rent`;
CREATE TABLE `device_rent` (
  `rent_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `rent_no` varchar(40) DEFAULT NULL COMMENT '出租单号',
  `rent_date` datetime DEFAULT NULL COMMENT '办理日期',
  `begin_time` datetime DEFAULT NULL COMMENT '出租开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '出租到期时间',
  `apply_username` varchar(20) DEFAULT NULL COMMENT '提交人',
  `apply_userid` varchar(32) DEFAULT NULL COMMENT '提交人id',
  `company_name` varchar(80) DEFAULT NULL COMMENT '租用商户',
  `company_id` varchar(32) DEFAULT NULL COMMENT '租用商户id',
  `check_username` varchar(32) DEFAULT NULL COMMENT '审核人',
  `check_userid` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `settle_type` varchar(20) DEFAULT NULL COMMENT '结算方式',
  `per_price` float DEFAULT NULL COMMENT '月租金或费率',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`rent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备出租管理';

-- ----------------------------
-- Records of device_rent
-- ----------------------------
INSERT INTO `device_rent` VALUES ('832092824905507328', '', '1', '', '2020-07-14 14:10:01', '2020-07-14 00:00:00', '2020-07-31 00:00:00', '系统管理员', 'admin', '2', '798180862646370973', '', '', 'free', '0', '', 'admin', '2020-07-14 14:09:49', 'admin', '2020-07-20 15:08:47', '0');
INSERT INTO `device_rent` VALUES ('841068086783631867', '', '0', '', '1970-01-01 00:00:00', '2020-07-20 00:00:00', '2020-07-20 00:00:00', '系统管理员', 'admin', '', '', '', '', 'free', '0', '', 'admin', '2020-07-20 15:07:31', 'admin', '2020-07-20 15:25:42', '0');

-- ----------------------------
-- Table structure for device_rent_det
-- ----------------------------
DROP TABLE IF EXISTS `device_rent_det`;
CREATE TABLE `device_rent_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `rent_id` varchar(32) DEFAULT NULL COMMENT 'rent_id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出租设备明细';

-- ----------------------------
-- Records of device_rent_det
-- ----------------------------
INSERT INTO `device_rent_det` VALUES ('828908158194967195', '798393154289910305', '798408908229969478', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for device_rent_vary
-- ----------------------------
DROP TABLE IF EXISTS `device_rent_vary`;
CREATE TABLE `device_rent_vary` (
  `vary_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `vary_date` datetime DEFAULT NULL COMMENT '变动日期',
  `rent_id` varchar(32) DEFAULT NULL COMMENT 'rent_id',
  `rent_no` varchar(40) DEFAULT NULL COMMENT '出租单号',
  `apply_username` varchar(40) DEFAULT NULL COMMENT '提交人',
  `apply_userid` varchar(32) DEFAULT NULL COMMENT '提交人id',
  `check_username` varchar(40) DEFAULT NULL COMMENT '审核人',
  `check_userid` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `org_device_name` varchar(80) DEFAULT NULL COMMENT '原设备名称',
  `org_device_code` varchar(40) DEFAULT NULL COMMENT '原设备编号',
  `org_device_id` varchar(20) DEFAULT NULL COMMENT '原设备id',
  `device_name` varchar(80) DEFAULT NULL COMMENT '新设备名称',
  `device_code` varchar(40) DEFAULT NULL COMMENT '新设备编号',
  `device_id` varchar(32) DEFAULT NULL COMMENT '新设备id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`vary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分配移装';

-- ----------------------------
-- Records of device_rent_vary
-- ----------------------------

-- ----------------------------
-- Table structure for device_stop
-- ----------------------------
DROP TABLE IF EXISTS `device_stop`;
CREATE TABLE `device_stop` (
  `stop_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '停机状态',
  `begin_time` datetime DEFAULT NULL COMMENT '停机开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '停机结束时间',
  `stop_type` varchar(20) DEFAULT NULL COMMENT '停机类型',
  `stop_reason` varchar(20) DEFAULT NULL COMMENT '停机原因',
  `is_sent` varchar(20) DEFAULT NULL COMMENT '发送远程控制',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  PRIMARY KEY (`stop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='停机管理';

-- ----------------------------
-- Records of device_stop
-- ----------------------------
INSERT INTO `device_stop` VALUES ('832092824905508368', '', '0', '2020-07-14 15:57:32', '2020-07-14 15:57:34', 'plan', '', '1', '', 'admin', '2020-07-14 15:57:36', null, null, '0', '798408908229969478');

-- ----------------------------
-- Table structure for device_type
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
CREATE TABLE `device_type` (
  `type_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `device_kind` varchar(60) DEFAULT NULL COMMENT '设备类型',
  `device_name` varchar(80) DEFAULT NULL COMMENT '设备名称',
  `device_factory` varchar(80) DEFAULT NULL COMMENT '生产厂家',
  `device_type` varchar(80) DEFAULT NULL COMMENT '设备型号',
  `device_size` varchar(200) DEFAULT NULL COMMENT '设备规格',
  `period_make` int(11) DEFAULT NULL COMMENT '备货周期',
  `use_age` int(11) DEFAULT NULL COMMENT '使用寿命',
  `device_weight` float DEFAULT NULL COMMENT '设备重量(KG)',
  `device_energy` float DEFAULT NULL COMMENT '能耗(KWH/日)',
  `cargo_num` int(11) DEFAULT NULL COMMENT '货道容量',
  `has_stock` varchar(10) DEFAULT NULL COMMENT '是否有备库',
  `temperature_status` varchar(20) DEFAULT NULL COMMENT '温控状态',
  `temperature_type` varchar(20) DEFAULT NULL COMMENT '温控模式',
  `temperature_set` float DEFAULT NULL COMMENT '目标温度',
  `humidity_set` float DEFAULT NULL COMMENT '目标湿度',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备机型';

-- ----------------------------
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES ('798134099042443865', '', 'medicine', '售药1号', '维蓝信息', '室外用', '2*1*1米', '22', '3', '200', '1', '120', '1', '', 'cool', null, null, '', 'admin', '2020-06-21 17:13:48', 'admin', '2020-07-02 14:20:11', '0');

-- ----------------------------
-- Table structure for device_type_cargo
-- ----------------------------
DROP TABLE IF EXISTS `device_type_cargo`;
CREATE TABLE `device_type_cargo` (
  `cargo_id` varchar(32) NOT NULL COMMENT '主键id',
  `type_id` varchar(32) DEFAULT NULL COMMENT 'type_id',
  `group_name` varchar(10) DEFAULT NULL COMMENT '组',
  `cargo_code` varchar(60) DEFAULT NULL COMMENT '货道编号',
  `cargo_level` int(11) DEFAULT NULL COMMENT '层号',
  `cargo_column` int(11) DEFAULT NULL COMMENT '列号',
  `merge_column` varchar(50) DEFAULT NULL COMMENT '合并列',
  `safe_num` int(11) DEFAULT NULL COMMENT '安全库存',
  `max_num` int(11) DEFAULT NULL COMMENT '最大容量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`cargo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备标准货道';

-- ----------------------------
-- Records of device_type_cargo
-- ----------------------------
INSERT INTO `device_type_cargo` VALUES ('824790229450818877', '798134099042443865', 'A', 'A01', '1', '1', null, null, null, 'A-01-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818878', '798134099042443865', 'A', 'A02', '1', '2', null, null, null, 'A-01-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818879', '798134099042443865', 'A', 'A03', '2', '1', null, null, null, 'A-02-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818880', '798134099042443865', 'A', 'A04', '2', '2', null, null, null, 'A-02-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818881', '798134099042443865', 'A', 'A05', '3', '1', null, null, null, 'A-03-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818882', '798134099042443865', 'A', 'A06', '3', '2', null, null, null, 'A-03-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818883', '798134099042443865', 'A', 'A07', '4', '1', null, null, null, 'A-04-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818884', '798134099042443865', 'A', 'A08', '4', '2', null, null, null, 'A-04-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818885', '798134099042443865', 'A', 'A09', '5', '1', null, null, null, 'A-05-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818886', '798134099042443865', 'A', 'A10', '5', '2', null, null, null, 'A-05-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818887', '798134099042443865', 'A', 'A11', '6', '1', null, null, null, 'A-06-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818888', '798134099042443865', 'A', 'A12', '6', '2', null, null, null, 'A-06-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818889', '798134099042443865', 'A', 'A13', '7', '1', null, null, null, 'A-07-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818890', '798134099042443865', 'A', 'A14', '7', '2', null, null, null, 'A-07-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818891', '798134099042443865', 'A', 'A15', '8', '1', null, null, null, 'A-08-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818892', '798134099042443865', 'A', 'A16', '8', '2', null, null, null, 'A-08-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818893', '798134099042443865', 'A', 'A17', '9', '1', null, null, null, 'A-09-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818894', '798134099042443865', 'A', 'A18', '9', '2', null, null, null, 'A-09-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818895', '798134099042443865', 'A', 'A19', '10', '1', null, null, null, 'A-10-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818896', '798134099042443865', 'A', 'A20', '10', '2', null, null, null, 'A-10-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818897', '798134099042443865', 'A', 'A21', '11', '1', null, null, null, 'A-11-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818898', '798134099042443865', 'A', 'A22', '11', '2', null, null, null, 'A-11-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818899', '798134099042443865', 'A', 'A23', '12', '1', null, null, null, 'A-12-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818900', '798134099042443865', 'A', 'A24', '12', '2', null, null, null, 'A-12-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818901', '798134099042443865', 'A', 'A25', '13', '1', null, null, null, 'A-13-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818902', '798134099042443865', 'A', 'A26', '13', '2', null, null, null, 'A-13-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818903', '798134099042443865', 'A', 'A27', '14', '1', null, null, null, 'A-14-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818904', '798134099042443865', 'A', 'A28', '14', '2', null, null, null, 'A-14-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818905', '798134099042443865', 'A', 'A29', '15', '1', null, null, null, 'A-15-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818906', '798134099042443865', 'A', 'A30', '15', '2', null, null, null, 'A-15-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818907', '798134099042443865', 'A', 'A31', '16', '1', null, null, null, 'A-16-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818908', '798134099042443865', 'A', 'A32', '16', '2', null, null, null, 'A-16-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818909', '798134099042443865', 'A', 'A33', '17', '1', null, null, null, 'A-17-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818910', '798134099042443865', 'A', 'A34', '17', '2', null, null, null, 'A-17-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818911', '798134099042443865', 'A', 'A35', '18', '1', null, null, null, 'A-18-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818912', '798134099042443865', 'A', 'A36', '18', '2', null, null, null, 'A-18-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818913', '798134099042443865', 'A', 'A37', '19', '1', null, null, null, 'A-19-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818914', '798134099042443865', 'A', 'A38', '19', '2', null, null, null, 'A-19-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818915', '798134099042443865', 'A', 'A39', '20', '1', null, null, null, 'A-20-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818916', '798134099042443865', 'A', 'A40', '20', '2', null, null, null, 'A-20-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818917', '798134099042443865', 'A', 'A41', '21', '1', null, null, null, 'A-21-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818918', '798134099042443865', 'A', 'A42', '21', '2', null, null, null, 'A-21-02', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818919', '798134099042443865', 'A', 'A43', '22', '1', null, null, null, 'A-22-01', 'admin', '2020-07-10 08:46:25', null, null, '0');
INSERT INTO `device_type_cargo` VALUES ('824790229450818920', '798134099042443865', 'A', 'A44', '22', '2', null, null, null, 'A-22-02', 'admin', '2020-07-10 08:46:26', null, null, '0');

-- ----------------------------
-- Table structure for device_type_part
-- ----------------------------
DROP TABLE IF EXISTS `device_type_part`;
CREATE TABLE `device_type_part` (
  `part_id` varchar(32) NOT NULL COMMENT '主键id',
  `type_id` varchar(32) DEFAULT NULL COMMENT 'type_id',
  `part_code` varchar(40) DEFAULT NULL COMMENT '部件编号',
  `part_name` varchar(60) DEFAULT NULL COMMENT '部件名称',
  `part_status` varchar(20) DEFAULT NULL COMMENT '部件状态',
  `part_descript` varchar(400) DEFAULT NULL COMMENT '部件描述',
  `part_factory` varchar(80) DEFAULT NULL COMMENT '厂家',
  `part_type` varchar(80) DEFAULT NULL COMMENT '型号',
  `part_size` varchar(80) DEFAULT NULL COMMENT '规格',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备标准部件';

-- ----------------------------
-- Records of device_type_part
-- ----------------------------

-- ----------------------------
-- Table structure for express_address
-- ----------------------------
DROP TABLE IF EXISTS `express_address`;
CREATE TABLE `express_address` (
  `rec_id` varchar(32) NOT NULL COMMENT '配送地址id',
  `rec_user` varchar(32) NOT NULL COMMENT '收件人姓名',
  `rec_phone` varchar(16) NOT NULL COMMENT '收件人手机号',
  `rec_address` varchar(64) NOT NULL COMMENT '收件地址（省份、城市、区域、街道）',
  `rec_postcode` varchar(10) DEFAULT NULL COMMENT '邮编',
  `rec_default` int(11) DEFAULT '0' COMMENT '是否默认 0：不默认 1：默认',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `member_id` varchar(32) NOT NULL COMMENT '就诊人id',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of express_address
-- ----------------------------

-- ----------------------------
-- Table structure for goods_list
-- ----------------------------
DROP TABLE IF EXISTS `goods_list`;
CREATE TABLE `goods_list` (
  `goods_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '发布状态',
  `bar_code` varchar(40) DEFAULT NULL COMMENT '商品条码号',
  `goods_name` varchar(80) DEFAULT NULL COMMENT '商品名称',
  `type_name` varchar(80) DEFAULT NULL COMMENT '商品分类',
  `type_id` varchar(32) DEFAULT NULL COMMENT '商品分类id',
  `goods_size` varchar(200) DEFAULT NULL COMMENT '规格',
  `retail_price` float DEFAULT NULL COMMENT '零售价',
  `machine_price` float DEFAULT NULL COMMENT '设备价',
  `eshop_price` float DEFAULT NULL COMMENT '电商价',
  `trade_price` float DEFAULT NULL COMMENT '批发价',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `goods_descript` text COMMENT '商品介绍',
  `goods_price` float DEFAULT NULL COMMENT '单价-对应单位的价格',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片',
  `expiry_date` varchar(20) DEFAULT NULL COMMENT '有效期',
  `is_free` varchar(10) DEFAULT NULL COMMENT '是否赠品',
  `is_oct` varchar(10) DEFAULT NULL COMMENT '是否处方药',
  `is_tcm` varchar(10) DEFAULT NULL COMMENT '是否中药成分',
  `sick_label` varchar(200) DEFAULT NULL COMMENT '治疗疾病标签',
  `factory` varchar(80) DEFAULT NULL COMMENT '生产厂家',
  `is_certification` varchar(10) DEFAULT NULL COMMENT '是否需要实名',
  `temperature_demand` varchar(80) DEFAULT NULL COMMENT '温度要求',
  `humidity_demand` varchar(80) DEFAULT NULL COMMENT '湿度要求',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `add_username` varchar(40) DEFAULT NULL COMMENT '创建人',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品目录';

-- ----------------------------
-- Records of goods_list
-- ----------------------------
INSERT INTO `goods_list` VALUES ('234535456657766346', '', '1', '2424234', '广药白云山复方银翘片（盒）', '药品', '1002', '0.5g*36粒*1盒', '500', '0.01', '122', '100', '盒', '', '0.6', '', '', '0', '0', '0', '', '大世界', '0', '', '', '2', '798180862646370973', '', '', 'admin', '2020-06-22 06:44:42', 'admin', '2020-09-21 17:25:41', '0');
INSERT INTO `goods_list` VALUES ('798408908229970399', '', '0', '2424234', 'h\'h\'h\'h红红火火', '饮料', '1001', '', '500', '0.01', '122', '100', '', '', null, '', '', '0', '0', '0', '', '大世界', '0', '', '', '2', '798180862646370973', '', '', 'admin', '2020-06-22 06:44:42', null, null, '0');
INSERT INTO `goods_list` VALUES ('897455234946220717', '', '1', '0.017kg', '999三九感冒灵颗粒', '药品', '1002', '10g*9袋*1盒', '26', '0.01', '26', '26', '盒', '', '1.5', '', '', '0', '0', '0', '发热 头疼', '', '0', '', '', '', '', '', '', 'admin', '2020-08-27 16:21:17', 'admin', '2020-09-21 17:25:13', '0');
INSERT INTO `goods_list` VALUES ('947713825557225514', '', '1', '3212222', '感康 复方氨酚烷胺片 ', '', '', '6片/板*2板/盒', '12.5', '12.5', '12.5', null, '', '感冒药缓解发热头痛咽喉肿痛流涕鼻塞', null, '', '', '0', '0', '0', '上呼吸道感染,鼻炎', '感康', '0', '', '', '', '', '', '', 'admin', '2020-09-30 14:01:15', null, null, '');

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type` (
  `type_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `type_level` int(11) DEFAULT NULL COMMENT '级别',
  `type_name` varchar(80) DEFAULT NULL COMMENT '分类名称',
  `full_name` varchar(200) DEFAULT NULL COMMENT '分类全称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- ----------------------------
-- Records of goods_type
-- ----------------------------
INSERT INTO `goods_type` VALUES ('1001', '', '1', '饮料', '', '', 'admin', '2020-06-21 21:37:18', null, null, '0');
INSERT INTO `goods_type` VALUES ('1002', '', '1', '药品', '', '', 'admin', '2020-06-21 21:37:26', null, null, '0');
INSERT INTO `goods_type` VALUES ('1003', '', '1', '卫计用品', '', '', 'admin', '2020-06-21 21:37:39', null, null, '0');
INSERT INTO `goods_type` VALUES ('1004', '', '1', '保健品', '', '', 'admin', '2020-06-21 21:37:59', null, null, '0');
INSERT INTO `goods_type` VALUES ('1005', '', '1', '零食', '', '', 'admin', '2020-06-21 21:38:06', null, null, '0');

-- ----------------------------
-- Table structure for hospital_info
-- ----------------------------
DROP TABLE IF EXISTS `hospital_info`;
CREATE TABLE `hospital_info` (
  `hospital_id` varchar(32) NOT NULL COMMENT '主键id',
  `hospital_name` varchar(32) DEFAULT NULL COMMENT '医院名称',
  `hospital_address` varchar(200) DEFAULT NULL COMMENT '医院地址',
  `hospital_level` varchar(20) DEFAULT NULL COMMENT '医院等级',
  `hospital_category` varchar(20) DEFAULT NULL COMMENT '医院类别',
  `hospital_telephone` varchar(40) DEFAULT NULL COMMENT '医院电话',
  `hospital_profile` varchar(255) DEFAULT NULL COMMENT '医院简介',
  `hospital_introduce` text COMMENT '医院详细介绍',
  `hospital_wesite` varchar(100) DEFAULT NULL COMMENT '医院官网',
  `field_name` varchar(40) DEFAULT NULL COMMENT '所属区域',
  `field_code` varchar(20) DEFAULT NULL COMMENT '所属区域编码',
  `field_id` varchar(32) DEFAULT NULL COMMENT '所属区域id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`hospital_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院科室';

-- ----------------------------
-- Records of hospital_info
-- ----------------------------
INSERT INTO `hospital_info` VALUES ('2452452', '广东省人民医院', '广州市越秀区中山二路106号', '3', '1', '020-83827812', '广东省人民医院（广东人民医院）创建于1946年，是广东省最大的综合性医院，是国内规模最大、综合实力最强的医院之一。医院建筑面积近25.3万平方米，在职职工4925人，其中卫生技术人员3525人，高级职称人员632人。', '广东省人民医院（广东人民医院）创建于1946年，是广东省最大的综合性医院，是国内规模最大、综合实力最强的医院之一。', 'http://z.xywy.com/yiyuan-e5413.htm', '天河区', 'THQ', '100110011002', '', '', '2020-09-04 09:51:56', 'admin', '2020-09-04 14:05:17', '');
INSERT INTO `hospital_info` VALUES ('2452455', '广东省中医院', '广东省广州市大德路111号', '3', '1', '020-81887233', '广东省中医院（广州中医药大学第二附属医院、广州中医药大学第二临床医学院）始建于1933年，是我国近代史上最早的中医医院之一，被誉为“南粤杏林第一家”。', '广东省中医院（广州中医药大学第二附属医院、广州中医药大学第二临床医学院）始建于1933年，是我国近代史上最早的中医医院之一，被誉为“南粤杏林第一家”。目前，医院已发展成为一间拥有大德路总院、二沙岛分院、芳村分院（广州市慈善医院）、珠海医院、大学...', 'http://z.xywy.com/yiyuan-gdhtcm.htm', '天河区', 'THQ', '100110011002', '', '', '2020-09-04 09:52:01', 'admin', '2020-09-04 14:06:03', '');

-- ----------------------------
-- Table structure for hospital_info_room
-- ----------------------------
DROP TABLE IF EXISTS `hospital_info_room`;
CREATE TABLE `hospital_info_room` (
  `office_room_id` varchar(32) NOT NULL COMMENT '科室id',
  `office_room` varchar(60) DEFAULT NULL COMMENT '科室名称',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '医院id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `room_icon` varchar(200) DEFAULT NULL COMMENT '科室图标',
  `room_id` varchar(32) DEFAULT NULL COMMENT 'room_id',
  PRIMARY KEY (`office_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科室信息表';

-- ----------------------------
-- Records of hospital_info_room
-- ----------------------------
INSERT INTO `hospital_info_room` VALUES ('3', '消化内科', '2452452', '1', '', '', '2020-09-04 09:53:06', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('4', '外科', '2452452', '', '', '', '2020-09-04 09:53:09', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('5', '肝胆外科', '2452452', '4', '', '', '2020-09-04 09:53:12', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('6', '普外科', '2452452', '4', '', '', '2020-09-04 09:53:15', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('7', '五官科', '2452455', '', '', '', '2020-09-04 09:53:18', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('8', '眼科', '2452455', '7', '', '', '2020-09-04 09:53:20', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('9', '口腔科', '2452455', '7', '', '', '2020-09-04 09:53:23', '', '2020-09-04 09:53:33', '', null, null);
INSERT INTO `hospital_info_room` VALUES ('944916461817643187', '内科', '2452455', null, null, null, null, null, null, null, null, '1001');
INSERT INTO `hospital_info_room` VALUES ('944916461817643189', '外科', '2452455', null, null, null, null, null, null, null, null, '1002');
INSERT INTO `hospital_info_room` VALUES ('944916461817643191', '五官科', '2452455', null, null, null, null, null, null, null, null, '1003');
INSERT INTO `hospital_info_room` VALUES ('944916461817643193', '药剂科', '2452455', null, null, null, null, null, null, null, null, '1004');

-- ----------------------------
-- Table structure for hospital_room
-- ----------------------------
DROP TABLE IF EXISTS `hospital_room`;
CREATE TABLE `hospital_room` (
  `room_id` varchar(32) NOT NULL COMMENT 'room_id',
  `room_code` varchar(40) DEFAULT NULL COMMENT '科目编码',
  `room_name` varchar(80) DEFAULT NULL COMMENT '科目名称',
  `order_index` int(11) DEFAULT NULL COMMENT '排序',
  `room_level` int(11) DEFAULT NULL COMMENT '层级',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院科目';

-- ----------------------------
-- Records of hospital_room
-- ----------------------------
INSERT INTO `hospital_room` VALUES ('1001', '001', '内科', null, '1', '', '', 'admin', '2020-09-14 12:51:35', null, null, '');
INSERT INTO `hospital_room` VALUES ('10011001', '1001', '消化内科', null, '2', '', '', 'admin', '2020-09-15 17:10:03', null, null, '');
INSERT INTO `hospital_room` VALUES ('1002', '002', '外科', null, '1', '', '', 'admin', '2020-09-15 17:08:46', 'admin', '2020-09-15 17:09:00', '');
INSERT INTO `hospital_room` VALUES ('10021001', '2001', '普外科', null, '2', '', '', 'admin', '2020-09-15 17:10:49', null, null, '');
INSERT INTO `hospital_room` VALUES ('10021002', '2002', '肝胆外科', null, '2', '', '', 'admin', '2020-09-15 17:10:49', null, null, '');
INSERT INTO `hospital_room` VALUES ('1003', '003', '五官科', null, '1', '', '', 'admin', '2020-09-15 17:11:13', null, null, '');
INSERT INTO `hospital_room` VALUES ('10031001', '3001', '眼科', null, '2', '', '', 'admin', '2020-09-15 17:11:34', null, null, '');
INSERT INTO `hospital_room` VALUES ('10031002', '3002', '口腔科', null, '2', '', '', 'admin', '2020-09-15 17:11:48', null, null, '');
INSERT INTO `hospital_room` VALUES ('1004', '004', '药剂科', null, '1', '', '', 'admin', '2020-09-16 11:05:59', 'admin', '2020-09-16 11:09:47', '');
INSERT INTO `hospital_room` VALUES ('10041001', '4001', '中药科', null, '2', '', '', 'admin', '2020-09-16 11:09:34', null, null, '');
INSERT INTO `hospital_room` VALUES ('10041002', '4002', '西药科', null, '2', '', '', 'admin', '2020-09-16 11:09:34', null, null, '');
INSERT INTO `hospital_room` VALUES ('10041004', '4003', '中西药科', null, '2', '', '', 'admin', '2020-09-16 11:11:37', null, null, '');

-- ----------------------------
-- Table structure for order_record
-- ----------------------------
DROP TABLE IF EXISTS `order_record`;
CREATE TABLE `order_record` (
  `order_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `out_status` varchar(20) DEFAULT NULL COMMENT '出货状态',
  `pay_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `service_status` varchar(20) DEFAULT NULL COMMENT '售后状态',
  `order_no` varchar(40) DEFAULT NULL COMMENT '订单编号',
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `back_status` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `back_time` datetime DEFAULT NULL COMMENT '退款时间',
  `pay_type` varchar(20) DEFAULT NULL COMMENT '付款方式',
  `pay_id` varchar(40) DEFAULT NULL COMMENT '收单方id',
  `pay_money` float DEFAULT NULL COMMENT '付款金额',
  `order_money` float DEFAULT NULL COMMENT '订单金额',
  `discount_money` float DEFAULT NULL COMMENT '优惠金额',
  `member_name` varchar(40) DEFAULT NULL COMMENT '购买会员',
  `member_id` varchar(32) DEFAULT NULL COMMENT '购买会员id',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `id_card` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `social_card` varchar(40) DEFAULT NULL COMMENT '社保卡号',
  `mobile_no` varchar(40) DEFAULT NULL COMMENT '手机号',
  `is_invoice` varchar(10) DEFAULT NULL COMMENT '是否开发票',
  `e_mail` varchar(40) DEFAULT NULL COMMENT '发票接收邮箱',
  `invoice_url` varchar(400) DEFAULT NULL COMMENT '电子发票链接',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售订单管理';

-- ----------------------------
-- Records of order_record
-- ----------------------------
INSERT INTO `order_record` VALUES ('11', null, '0', '1', '1', '0', '2222', '2020-07-13 23:15:49', '2020-07-13 23:15:57', null, null, 'alipay', null, '100', '100', '0', null, null, '3', '824790229450818128', null, null, '111111111111111111111', null, '1333333333333333', null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for order_record_goods
-- ----------------------------
DROP TABLE IF EXISTS `order_record_goods`;
CREATE TABLE `order_record_goods` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `order_id` varchar(32) DEFAULT NULL COMMENT 'order_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `out_status` varchar(20) DEFAULT NULL COMMENT '出货状态',
  `cargo_code` varchar(40) DEFAULT NULL COMMENT '出货货道号',
  `cargo_id` varchar(32) DEFAULT NULL COMMENT 'cargo_id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品明细';

-- ----------------------------
-- Records of order_record_goods
-- ----------------------------
INSERT INTO `order_record_goods` VALUES ('1', '11', '798408908229970399', '3', '1', 'A11', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for store_check
-- ----------------------------
DROP TABLE IF EXISTS `store_check`;
CREATE TABLE `store_check` (
  `check_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '盘点状态',
  `check_no` varchar(40) DEFAULT NULL COMMENT '盘点单号',
  `check_date` datetime DEFAULT NULL COMMENT '盘点日期',
  `user_nane` varchar(40) DEFAULT NULL COMMENT '盘点人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '盘点人id',
  `stock_name` varchar(60) DEFAULT NULL COMMENT '仓库名称',
  `stock_id` varchar(32) DEFAULT NULL COMMENT '仓库id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存盘点';

-- ----------------------------
-- Records of store_check
-- ----------------------------

-- ----------------------------
-- Table structure for store_check_det
-- ----------------------------
DROP TABLE IF EXISTS `store_check_det`;
CREATE TABLE `store_check_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `check_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `store_price` float DEFAULT NULL COMMENT '采购单价',
  `store_num` int(11) DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `check_result` varchar(20) DEFAULT NULL COMMENT '盘点结果',
  `fact_num` int(11) DEFAULT NULL COMMENT '实际数量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点明细';

-- ----------------------------
-- Records of store_check_det
-- ----------------------------

-- ----------------------------
-- Table structure for store_in
-- ----------------------------
DROP TABLE IF EXISTS `store_in`;
CREATE TABLE `store_in` (
  `in_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(40) DEFAULT NULL COMMENT '入库单号',
  `in_type` varchar(20) DEFAULT NULL COMMENT '入库类型',
  `in_date` datetime DEFAULT NULL COMMENT '入库时间',
  `user_nane` varchar(40) DEFAULT NULL COMMENT '入库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '入库人id',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '入库仓库',
  `stock_id` varchar(32) DEFAULT NULL COMMENT '入库仓库id',
  `in_num` int(11) DEFAULT NULL COMMENT '入库数量',
  `in_money` float DEFAULT NULL COMMENT '入库金额',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `in_no` varchar(40) DEFAULT NULL COMMENT '入库单号',
  PRIMARY KEY (`in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单';

-- ----------------------------
-- Records of store_in
-- ----------------------------
INSERT INTO `store_in` VALUES ('798408908229971632', '', null, '1', '2020-06-22 08:53:33', '系统管理员', 'admin', '', '', '22', '33', '2', '798180862646370973', '', 'admin', '2020-06-22 08:53:45', 'admin', '2020-06-22 08:53:52', '0', null);

-- ----------------------------
-- Table structure for store_in_det
-- ----------------------------
DROP TABLE IF EXISTS `store_in_det`;
CREATE TABLE `store_in_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `in_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `in_price` float DEFAULT NULL COMMENT '采购单价',
  `in_num` int(11) DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `useable_num` int(11) DEFAULT NULL COMMENT '剩余数量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单明细';

-- ----------------------------
-- Records of store_in_det
-- ----------------------------

-- ----------------------------
-- Table structure for store_out
-- ----------------------------
DROP TABLE IF EXISTS `store_out`;
CREATE TABLE `store_out` (
  `out_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(40) DEFAULT NULL COMMENT '出库状态',
  `out_type` varchar(20) DEFAULT NULL COMMENT '出库类型',
  `out_date` datetime DEFAULT NULL COMMENT '出库时间',
  `user_nane` varchar(40) DEFAULT NULL COMMENT '出库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '出库人id',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '出库仓库',
  `stock_id` varchar(32) DEFAULT NULL COMMENT '出库仓库id',
  `out_num` int(11) DEFAULT NULL COMMENT '出库数量',
  `out_money` float DEFAULT NULL COMMENT '出库金额',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `out_no` varchar(40) DEFAULT NULL COMMENT '出库单号',
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单';

-- ----------------------------
-- Records of store_out
-- ----------------------------

-- ----------------------------
-- Table structure for store_out_det
-- ----------------------------
DROP TABLE IF EXISTS `store_out_det`;
CREATE TABLE `store_out_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `out_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `out_price` float DEFAULT NULL COMMENT '采购单价',
  `out_num` int(11) DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单明细';

-- ----------------------------
-- Records of store_out_det
-- ----------------------------

-- ----------------------------
-- Table structure for store_stock
-- ----------------------------
DROP TABLE IF EXISTS `store_stock`;
CREATE TABLE `store_stock` (
  `stock_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `bar_code` varchar(40) DEFAULT NULL COMMENT '条码号',
  `inplace_descript` varchar(200) DEFAULT NULL COMMENT '位置描述',
  `stock_level` int(11) DEFAULT NULL COMMENT '级别',
  `user_nane` varchar(40) DEFAULT NULL COMMENT '保管员',
  `user_id` varchar(32) DEFAULT NULL COMMENT '保管员id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `min_num` float DEFAULT NULL COMMENT '最小库存量',
  `max_num` float DEFAULT NULL COMMENT '最大库存量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库货架';

-- ----------------------------
-- Records of store_stock
-- ----------------------------
INSERT INTO `store_stock` VALUES ('1001', '', 'A1', 'AAAA', '111', '', '1', '', '', '', '', '0', '1000', '', 'admin', '2020-07-14 16:12:08', null, null, '0');

-- ----------------------------
-- Table structure for system_faultrule
-- ----------------------------
DROP TABLE IF EXISTS `system_faultrule`;
CREATE TABLE `system_faultrule` (
  `rule_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(10) DEFAULT NULL COMMENT '是否生效',
  `fault_name` varchar(80) DEFAULT NULL COMMENT '告警名称',
  `fault_descript` varchar(200) DEFAULT NULL COMMENT '描述',
  `device_type` varchar(80) DEFAULT NULL COMMENT '设备型号',
  `type_id` varchar(32) DEFAULT NULL COMMENT '设备种类id',
  `compute_sql` text COMMENT '判断条件',
  `warning_type` varchar(20) DEFAULT NULL COMMENT '通知方式',
  `sms_template` text COMMENT '通知模版',
  `is_repair` varchar(10) DEFAULT NULL COMMENT '通知维护？',
  `is_operation` varchar(10) DEFAULT NULL COMMENT '通知运营？',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警规则定义';

-- ----------------------------
-- Records of system_faultrule
-- ----------------------------
INSERT INTO `system_faultrule` VALUES ('832092824905508212', '', '1', '离线报警', '', '售药1号', '798134099042443865', '{在线状态}=[故障]', 'sms', '{设备号}当前离线，请检查网络！', '1', '1', '', 'admin', '2020-07-14 15:48:35', 'admin', '2020-07-14 15:49:20', '0');

-- ----------------------------
-- Table structure for system_paychannel
-- ----------------------------
DROP TABLE IF EXISTS `system_paychannel`;
CREATE TABLE `system_paychannel` (
  `paychannel_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `pay_type` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `pay_descript` varchar(100) DEFAULT NULL COMMENT '账户描述',
  `pay_usercode` varchar(80) DEFAULT NULL COMMENT '账号',
  `pay_password` varchar(80) DEFAULT NULL COMMENT '密码',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`paychannel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付通道设置';

-- ----------------------------
-- Records of system_paychannel
-- ----------------------------
INSERT INTO `system_paychannel` VALUES ('798408908229970546', '1001', null, 'weixinpay', null, '112', '3', null, null, null, 'admin', '2020-06-22 06:50:58', 'admin', '2020-06-22 06:56:02', '0');
INSERT INTO `system_paychannel` VALUES ('798408908229970677', '1001', null, 'alipay', null, '2', '3', null, null, null, 'admin', '2020-06-22 06:56:02', null, null, '0');

-- ----------------------------
-- Table structure for total_device
-- ----------------------------
DROP TABLE IF EXISTS `total_device`;
CREATE TABLE `total_device` (
  `total_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `total_month` varchar(20) DEFAULT NULL COMMENT '统计月份',
  `day_order_num` int(11) DEFAULT NULL COMMENT '当日订单数',
  `month_order_num` int(11) DEFAULT NULL COMMENT '当月订单数',
  `day_order_money` float DEFAULT NULL COMMENT '当日销售额',
  `month_order_money` float DEFAULT NULL COMMENT '当月销售额',
  `order_num` int(11) DEFAULT NULL COMMENT '累计订单数',
  `order_money` float DEFAULT NULL COMMENT '累计销售额',
  `settle_order_num` int(11) DEFAULT NULL COMMENT '待结算订单数',
  `settle_order_money` float DEFAULT NULL COMMENT '待结算金额',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`total_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备统计';

-- ----------------------------
-- Records of total_device
-- ----------------------------

-- ----------------------------
-- Table structure for treatment_record
-- ----------------------------
DROP TABLE IF EXISTS `treatment_record`;
CREATE TABLE `treatment_record` (
  `service_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `service_no` varchar(40) DEFAULT NULL COMMENT '诊疗单号',
  `auditing` varchar(10) DEFAULT NULL COMMENT '状态',
  `member_id` varchar(32) DEFAULT NULL COMMENT 'member_id',
  `member_name` varchar(40) DEFAULT NULL COMMENT '会员姓名',
  `reserve_id` varchar(32) DEFAULT NULL COMMENT 'reserve_id',
  `age` varchar(40) DEFAULT NULL COMMENT '年龄',
  `is_return` varchar(20) DEFAULT NULL COMMENT '是否复诊',
  `height` varchar(40) DEFAULT NULL COMMENT '身高',
  `weight` varchar(40) DEFAULT NULL COMMENT '体重',
  `temperature` varchar(40) DEFAULT NULL COMMENT '体温',
  `heart_rate` varchar(40) DEFAULT NULL COMMENT '心率',
  `sbp` varchar(40) DEFAULT NULL COMMENT '血压(收缩压)',
  `dbp` varchar(40) DEFAULT NULL COMMENT '血压(舒张压)',
  `symptom_describe` text COMMENT '病症表现',
  `diagnosis_result` text COMMENT '诊断结果',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT 'doctor_id',
  `doctor_name` varchar(40) DEFAULT NULL COMMENT '专家名称',
  `user_id` varchar(32) DEFAULT NULL COMMENT '系统帐号id',
  `start_time` datetime DEFAULT NULL COMMENT '诊疗开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '诊疗结束时间',
  `check_doctor_id` varchar(32) DEFAULT NULL COMMENT '审核药师id',
  `check_doctor_name` varchar(40) DEFAULT NULL COMMENT '审核药师',
  `check_user_id` varchar(32) DEFAULT NULL COMMENT '审核药师帐号',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `back_status` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `back_time` datetime DEFAULT NULL COMMENT '退款时间',
  `pay_type` varchar(10) DEFAULT NULL COMMENT '付款方式',
  `pay_id` varchar(40) DEFAULT NULL COMMENT '收单方id',
  `goods_money` float DEFAULT NULL COMMENT '药费',
  `service_money` float DEFAULT NULL COMMENT '诊疗费',
  `pay_money` float DEFAULT NULL COMMENT '付款金额',
  `order_money` float DEFAULT NULL COMMENT '订单金额',
  `discount_money` float DEFAULT NULL COMMENT '优惠金额',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `device_name` varchar(80) DEFAULT NULL COMMENT '设备名称',
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `man_id` varchar(32) DEFAULT NULL COMMENT '就诊人id ',
  `man_name` varchar(32) DEFAULT NULL COMMENT '就诊人名称 ',
  `endoscope` text COMMENT '窥镜检查 ',
  `sub_check_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `check_result` varchar(20) DEFAULT NULL COMMENT '审核结果',
  `check_remark` text COMMENT '审核结果备注',
  `sub_check_status` varchar(10) DEFAULT NULL COMMENT '是否提交审核',
  `current_history` text COMMENT '现病史',
  `diagnosis_suggest` text COMMENT '处理建议',
  `device_code` varchar(32) DEFAULT NULL COMMENT '设备编码',
  `device_address` varchar(255) DEFAULT NULL COMMENT '设备地址',
  `pay_status` varchar(10) DEFAULT NULL COMMENT '支付状态',
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='诊疗记录管理';

-- ----------------------------
-- Records of treatment_record
-- ----------------------------
INSERT INTO `treatment_record` VALUES ('913886336712220678', '', 'c88f4490002d484d8eb98cbaecfb58ec', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '913886336712220674', '27', '', '', '', '36.7', '81', '128', '78', '1111', '6666', '892952030454973158', '张三丰', '', '2020-09-07 16:53:37', '2020-09-11 10:12:32', '', '', '', '2020-09-15 11:00:32', '2020-09-11 10:12:53', '', '2020-09-11 10:12:56', '', '', '417', null, '417', '417', '0', '', '智慧问诊V1.0', '893136353271611988', '', '2020-09-07 16:53:31', '', '2020-09-11 10:13:09', '', '913886336712220673', '陈家杰', '', '2020-09-09 14:07:05', '1', '', '1', '3333', '7777', 'Dev00002', '', '0');
INSERT INTO `treatment_record` VALUES ('924052919440121858', null, '2020091407446a91d43d428c939f86115969f1f7', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '919884688038215721', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 12:50:01', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924080664928862225', null, '20200914c6d4dde860974070a6283710dc4dea8a', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924080664928862221', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 13:45:37', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 13:39:19', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924080664928862226', null, '202009146f65e4edbed24aeea21f9ffc7553f83f', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924080664928862221', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 13:39:41', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924108994532122636', null, '202009140f56cd46fab14666aa4d1050ca0ee046', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924108994532122632', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 13:52:06', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 13:51:48', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924108994532122638', null, '20200914ae64f46ce14d43599ce297af3858f521', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924108994532122632', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 13:54:00', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 13:53:49', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924125143609163780', null, '86685599611b4a9a9080e448e750e0e1', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924125143609163776', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 14:01:25', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 14:01:14', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924166753252335623', null, '744a9854bda34e0c86df458e5cb54157', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924166753252335616', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 14:44:32', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 14:44:20', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924199395003793412', null, 'b83ee4ef1d2249679fbbdc77c43b78d2', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924199395003793408', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 15:17:03', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 15:16:49', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924199395003793425', null, '5a5b0df4d28449bfa8bd3aaa25b07fb3', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924199395003793421', '27', null, null, null, null, null, null, null, 'asda', 'ddd', '892952030454973158', '张三丰', null, '2020-09-14 15:21:35', null, null, null, null, null, null, null, null, null, null, '26', '0.01', '26', '26', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 15:21:24', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, 'dsfs', 'asa', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('924220783940935731', null, 'bbcd5f297aae438db3a3092ba013d7d8', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924220783940935727', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 16:22:14', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 16:21:55', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924220783940935743', null, '7343014684a244269b29b6c58555c687', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924220783940935739', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 16:35:54', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 16:35:43', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924328209662951441', null, '340fe0f8395a437ea5d2ac851f09939a', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924328209662951437', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-14 20:51:19', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-14 20:51:06', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924328209662951553', null, 'fb4e0ea9956140b3b2db0bfb93831cef', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924328209662951549', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 10:17:23', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('924328209662951554', null, '3ec1940440e44c2b8c8b9445104e2104', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924328209662951549', '27', null, null, null, null, null, null, null, '病情主诉', '感冒', '892952030454973158', '张三丰', null, '2020-09-15 10:19:35', null, null, null, null, '2020-09-15 10:25:52', null, null, null, null, null, '965', '0.01', '965', '965', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 10:19:27', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 10:25:10', '1', null, '1', '现病史', '处理建议', 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('924328209662951572', null, '273caf93e9cd4ed4945c439d4acc8bcf', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '924328209662951567', '27', null, null, null, null, null, null, null, '病情主诉\n', '诊断', '892952030454973158', '张三丰', null, '2020-09-15 10:34:24', null, null, null, null, '2020-09-15 10:37:57', null, null, null, null, null, '26', '0.01', '26', '26', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 10:34:18', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 10:37:40', '1', null, '1', '病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925415489223876612', null, 'f79569a026db490da5e31fceb710ca94', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925415489223876608', '27', null, null, null, null, null, null, null, '1111', '66', '892952030454973158', '张三丰', null, '2020-09-15 10:56:23', null, null, null, null, '2020-09-15 10:57:52', null, null, null, null, null, '26', '0.01', '26', '26', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 10:56:16', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 10:57:06', '1', null, '1', '333', '777', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925415489223876632', null, 'fd31182d42d8423a8020c29ffbc8987d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925415489223876628', '27', null, null, null, null, null, null, null, '病情主诉', '初步诊断', '892952030454973158', '张三丰', null, '2020-09-15 11:11:42', null, null, null, null, '2020-09-15 11:13:28', null, null, null, null, null, '339', '0.01', '339', '339', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 11:10:23', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 11:13:08', '1', null, '1', '现病史', '处理建议\n', 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925440004897210375', null, '3f35fe351f064393a1f40cad67a74b16', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925440004897210370', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 11:18:27', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 11:18:18', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925440004897210377', null, '469311e8cd904d7890be11c9b5ee956a', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925440004897210370', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 11:18:56', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925440004897210390', null, '18e0e6a603ab445e8b3d10bdeb8b4498', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925440004897210386', '27', null, null, null, null, null, null, null, '病情主诉1', '初步诊断1', '892952030454973158', '张三丰', null, '2020-09-15 11:22:40', null, null, null, null, '2020-09-15 11:31:31', null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 11:22:31', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 11:31:25', '1', null, '1', '现病史1', '处理建议1', 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925460637920108567', null, '85d9aacd889b4c42a9cdd5a814261169', '5', 'ouYzq0EAMlfXwlGtWyofuBBLxFqU', '张宏量', '925460637920108553', '36', null, '180', '80', '36.7', '81', '128', '78', null, null, '892952030454973158', '张三丰', null, '2020-09-15 11:48:36', null, null, null, null, '2020-09-15 12:57:16', null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-21 08:48:26', null, null, null, '916925902247632965', '张宏量', null, '2020-09-15 12:57:04', '1', null, '1', null, null, 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925460637920108582', null, '34e9f89703bf420085998be3d6e46f20', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925460637920108578', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 12:29:18', null, null, null, null, null, null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 12:29:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925460637920108596', null, '40a305f6b43945bd97db3195bd4c91eb', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925460637920108591', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 12:31:31', null, null, null, null, null, null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 12:31:24', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925460637920108628', null, 'ef075dc1a7ea4f10bbb1721010e4cde2', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925460637920108624', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 12:45:13', null, null, null, null, null, null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 12:45:04', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925460637920108643', null, '992d417161474738ab4965c3303d1671', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925460637920108639', '27', null, null, null, null, null, null, null, '111', '666', '892952030454973158', '张三丰', null, '2020-09-15 12:54:25', null, null, null, null, '2020-09-15 12:58:58', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 12:54:17', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 12:58:42', '1', null, '1', '333', '777', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925562566083993604', null, '1c79c0355be540b0811bdcdb28601e7c', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925562566083993600', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 13:15:32', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:15:19', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925562566083993609', null, '8cd4379079c74dc69e86f7c4834d1c18', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925562566083993600', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:20:57', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925562566083993618', null, 'e98a41840e2542e58d148a995afaaf5b', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925562566083993614', '27', null, null, null, null, null, null, null, '主诉', '初步诊断', '892952030454973158', '张三丰', null, '2020-09-15 13:24:07', null, null, null, null, '2020-09-15 13:25:30', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:23:58', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 13:25:16', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925580020831100936', null, '8d2514330b2e4578ac60001a60f9285d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925580020831100932', '27', null, null, null, null, null, null, null, '病情主诉', '初步诊断', '892952030454973158', '张三丰', null, '2020-09-15 13:33:18', null, null, null, null, '2020-09-15 13:34:16', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:33:11', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 13:34:08', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925585552748986387', null, '581958f51ed54b4baaeae928add44e5d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925585552748986383', '27', null, null, null, null, null, null, null, '病情主诉', '初步诊断', '892952030454973158', '张三丰', null, '2020-09-15 13:39:09', null, null, null, null, '2020-09-15 13:40:26', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:39:00', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 13:40:16', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925585552748986407', null, '3bf6e626ee5342198e38f8c38271ef4d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925585552748986403', '27', null, null, null, null, null, null, null, '12', '77', '892952030454973158', '张三丰', null, '2020-09-15 13:45:49', null, null, null, null, '2020-09-15 13:46:49', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:45:38', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 13:46:42', '1', null, '1', '34', '33', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925585552748986427', null, '5a932a5ac0694602a473d9355066a2bc', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925585552748986420', '27', null, null, null, null, null, null, null, 'qq', 'ff', '892952030454973158', '张三丰', null, '2020-09-15 13:52:18', null, null, null, null, '2020-09-15 13:53:14', '2020-09-15 13:53:38', null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 13:52:12', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 13:53:08', '1', null, '1', 'ss', 'ss', 'Dev00002', null, '1');
INSERT INTO `treatment_record` VALUES ('925615222383083524', null, 'cebd91f45d514654b0800173c6eb2826', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925615222383083520', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 14:07:16', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 14:06:43', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925615222383083540', null, '39fbe4ad8cda457e80d57f804a96f8e9', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925615222383083536', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 14:15:43', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 14:15:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925615222383083551', null, 'a46d897da5de482d9d1d970fcfe186bc', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925615222383083547', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-15 14:23:48', null, null, null, null, '2020-09-15 14:26:30', null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 14:23:17', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 14:26:21', '1', null, '1', '现病史', '处理建议', 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('925615222383083566', null, 'db43a130365547f4b43f18aae8d29fcf', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925615222383083562', '27', null, null, null, null, null, null, null, '111', '66', '892952030454973158', '张三丰', null, '2020-09-15 14:31:11', null, null, null, null, '2020-09-15 14:32:06', '2020-09-15 14:32:22', null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 14:31:00', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 14:31:55', '1', null, '1', '333', '777', 'Dev00002', null, '1');
INSERT INTO `treatment_record` VALUES ('925682430032502839', null, 'ed69ac257d52462d8862bd0b391a8faa', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925682430032502835', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-15 15:28:46', null, null, null, null, '2020-09-15 15:30:03', '2020-09-15 15:30:11', null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 15:28:35', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 15:29:52', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '1');
INSERT INTO `treatment_record` VALUES ('925682430032502946', null, '0484ee44f051434d896f9c84fba3868d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925682430032502942', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果\n', '892952030454973158', '张三丰', null, '2020-09-15 16:17:30', null, null, null, null, '2020-09-15 16:23:08', '2020-09-15 16:23:25', null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-15 16:17:10', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-15 16:22:53', '1', null, '1', '现病史', '处理建议', 'testDev00002', null, '1');
INSERT INTO `treatment_record` VALUES ('925791161424568404', null, 'e74fb2dc93154cd198cfac2a48f18c8e', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925791161424568400', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 17:17:28', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 17:17:05', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925791161424568407', null, 'e7d1ff891dfc4483a78bcd1fd3529909', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925791161424568400', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 17:20:18', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 17:19:51', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925791161424568409', null, '0f0319ce43664d4a8b10e66aba73ca53', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925791161424568400', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 17:22:54', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 17:22:44', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('925824507550670856', null, 'ca9449de92b946da994bfd819b6aa549', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '925824507550670852', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-15 17:32:19', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-15 17:32:13', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('927204411823505417', null, '439332fadf7343548d3dd22425ca570e', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '927204411823505408', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-16 16:01:13', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-16 16:01:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('927223842255560708', null, '275e9fa3913b4c36b9ee7877f83ebee4', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '927223842255560704', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-16 16:06:55', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-16 16:06:46', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('927317352283553814', null, 'a3ecc011caa94aabbf6b21b328027695', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '927317352283553805', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-17 10:57:20', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('927317352283553815', null, 'beab3b6cb82d4165bb3614297fda4d99', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '927317352283553805', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-17 11:00:32', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('928656712065032201', null, 'a094b170865146dd82079a5c1a9ff96b', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928656712065032196', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-17 16:06:31', null, null, null, null, '2020-09-17 16:08:45', null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-17 16:06:16', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-17 16:08:00', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('928656712065032222', null, '22f2269fbe2046c19f37ed00e50a9616', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928656712065032218', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-17 16:12:07', null, null, null, null, '2020-09-17 16:32:15', null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-17 16:11:55', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-17 16:32:02', '1', null, '1', '现病', '建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('928719951163506722', null, '668794a22f674ab8955627a3febe9bc3', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928719951163506718', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 08:42:43', null, null, null, null, null, null, null, null, null, null, '0.02', '0.01', '0.02', '0.02', '0', null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-18 08:42:23', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('928719951163506736', null, '6d8f4a75564d4335b3b1c8d9159fb64d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928719951163506732', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 08:49:10', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-18 08:48:54', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('928719951163506749', null, 'db30811310b34dedb472d52f8893614a', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928719951163506745', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 08:57:25', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-18 08:57:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('928719951163506772', null, '65e46342a5d44a799326814046294ddb', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928719951163506768', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-18 10:31:02', null, null, null, null, '2020-09-18 10:32:34', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 10:30:43', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-18 10:32:02', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('928719951163506790', null, '463c36f6ec1047e18a24e31b6b0c7ec9', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '928719951163506786', '27', null, null, null, null, null, null, null, '主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-18 11:00:46', null, null, null, null, '2020-09-18 11:02:00', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 11:00:31', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-18 11:01:49', '1', null, '1', '现病', '处理建议', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('929891480802959381', null, '23865ba0d35c4fc1837c1b108a58f259', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959377', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果', '892952030454973158', '张三丰', null, '2020-09-18 13:58:35', null, null, null, null, '2020-09-18 14:00:25', null, null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 13:58:14', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-18 14:00:05', '1', null, '1', '现病', '处理建议\n', 'Dev00002', null, '0');
INSERT INTO `treatment_record` VALUES ('929891480802959404', null, '8ba924a0f50042aa8445330278710471', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959400', '27', null, null, null, null, null, null, null, '病情主诉', '诊断结果\n', '892952030454973158', '张三丰', null, '2020-09-18 14:07:35', null, null, null, null, '2020-09-18 14:10:54', '2020-09-18 14:12:51', null, null, null, null, '0.01', '0.01', '0.01', '0.01', '0', null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 14:06:29', null, null, null, '913886336712220673', '陈家杰', null, '2020-09-18 14:10:41', '1', null, '1', '现病史', '处理建议', 'Dev00002', null, '1');
INSERT INTO `treatment_record` VALUES ('929891480802959433', null, '4ee6dd5c60954c66b2b3317e6dddc6d0', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959428', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 14:47:29', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 14:46:46', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('929891480802959453', null, '6', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959448', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 15:12:16', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 15:11:04', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('929891480802959456', null, '8656ad1f17844a739ffa216725579b54', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959448', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 15:18:19', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 15:18:05', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('929891480802959459', null, '4d910a4f7f9849438536a0101ebdf037', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959448', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 15:24:53', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 15:24:43', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('929891480802959462', null, '464a565b862a47e184ae34a9691e117d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '929891480802959448', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 15:26:14', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 15:25:58', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134722', null, '43cfa7eb851d45f6986fa6b9d590577d', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930239115456356352', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 16:59:03', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 16:58:41', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134723', null, '887d05a3e35746198e6c9f19a9e4b262', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930239115456356352', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:01:10', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:01:02', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134724', null, '845ce074a9b9468b8f30ee17eead803b', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930239115456356352', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:11:43', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:03:09', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134725', null, 'd1458603c6ae444ea9e746b323051bc0', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930239115456356352', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:04:22', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:03:50', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134726', null, '349b1b8358094bbe9f6d2a15ed9c6c8f', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930239115456356352', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:11:26', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:11:17', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134733', null, '4465507812aa4c18becbdb85e69a891c', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930252361135071236', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:19:15', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:19:01', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('930211902543134734', null, '277bcd85bfcd45ab8fe65ff1c307c373', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '930252361135071236', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-18 17:20:22', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-18 17:20:13', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364551', null, '21e0d551874645749bc3b531d8f6127e', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364547', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 12:44:55', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 12:44:34', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364554', null, '257b536fda4b4287b931f8a25d4d7ed4', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364547', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 12:46:05', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 12:45:53', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364556', null, '1c5b8c10878a42e1b0c8dd8a56972fca', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364547', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 12:48:18', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 12:48:07', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364558', null, 'b12d4a780c954ab084f9f86b3a3d6d75', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364547', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 12:50:26', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 12:50:20', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364568', null, '198a4d89bc974dbf99e38a55515436f2', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364564', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 12:55:08', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 12:54:49', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364597', null, 'e529b5f3645f4ded8529026245e84781', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364593', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 13:08:25', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-19 13:08:12', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931468300736364600', null, 'fca0820f27224b41996a4f6c65ff1a05', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931468300736364593', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 13:12:20', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '917948293442658372', null, '2020-09-19 13:12:01', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'testDev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931512487359938565', null, '70c657c9bc034f4ead8d335a27f4f617', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931512487359938561', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-24 12:11:20', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 09:14:19', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931512487359938568', null, '12b828978e584ebc94a4b6b36e39532d', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931512487359938561', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-19 15:15:32', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('931512487359938581', null, '8ecd12fbbda047689883aa83a8709825', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '931512487359938577', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-19 15:50:37', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 08:58:08', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('934289080637603849', null, 'b17039ad84634c1282f8fdb655269460', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '934289080637603845', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, null, null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 10:22:31', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('934289080637603852', null, '5f77ed7f48b848f585de86761381fd7e', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '934289080637603845', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-21 10:25:45', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 10:25:34', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('934289080637603864', null, 'fe1f2af1a5424a028236eba0b778b670', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '934289080637603859', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-21 10:29:40', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 10:29:33', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('934289080637603867', null, 'a926f5b21502411ab74dc6c13d78f7dc', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '934289080637603859', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-21 10:30:27', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 10:30:13', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('934289080637603880', null, 'db4262fdd4e24f10bd864c1be00d6e5a', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '934289080637603876', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-21 10:32:36', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-21 10:32:27', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('937227662903017479', null, '01680859629745939e6452b29cb0b981', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '937227662903017475', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-23 10:51:34', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-23 10:51:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('937643054960066573', null, 'bbd1c5b27de5484da4a0325970a41043', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '937643054960066569', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-23 17:30:25', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-23 17:30:17', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943536643444113418', null, '69196e7706e546c98fd33d036f2d4e54', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943536643444113414', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 16:01:35', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 16:00:46', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943536643444113476', null, '4f5171ec32034c87bf35d4ffe7d87281', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943536643444113470', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 16:39:01', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 16:38:37', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943598284814761984', null, 'aaaf423854724202ad08287d7ec3f940', '6', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943536643444113470', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 16:52:04', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 16:51:59', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943637197218471946', null, '3f9800ce5f534ed8bee71007e394e849', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943637197218471936', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 17:32:33', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 17:31:21', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943683204908171270', null, 'c58294c4bdf24f73a978ad878a0157dc', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943683204908171264', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 18:18:49', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 18:18:35', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943683204908171301', null, '8c99ee93059948a1a8205f50202a6ca0', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943683204908171297', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 18:57:16', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 18:57:03', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943683204908171314', null, '2452c3edc3db4ea5a0a418cce44ba637', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943683204908171310', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 19:09:59', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 19:09:41', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);
INSERT INTO `treatment_record` VALUES ('943683204908171328', null, '392e7292efc44b34844f56fa0da195a8', '5', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '943683204908171323', '27', null, null, null, null, null, null, null, null, null, '892952030454973158', '张三丰', null, '2020-09-27 19:21:25', null, null, null, null, null, null, null, null, null, null, null, '0.01', null, null, null, null, '智慧问诊V1.0', '893136353271611988', null, '2020-09-27 19:21:19', null, null, null, '913886336712220673', '陈家杰', null, null, null, null, null, null, null, 'Dev00002', null, null);

-- ----------------------------
-- Table structure for treatment_record_goods
-- ----------------------------
DROP TABLE IF EXISTS `treatment_record_goods`;
CREATE TABLE `treatment_record_goods` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `service_id` varchar(32) DEFAULT NULL COMMENT 'service_id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `goods_name` varchar(60) DEFAULT NULL COMMENT '药品名称',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `buy_num` int(11) DEFAULT NULL COMMENT '购买量',
  `drug_usage` text COMMENT '药品用法',
  `goods_price` float DEFAULT NULL COMMENT '单价',
  `goods_money` float DEFAULT NULL COMMENT '金额',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `goods_size` varchar(200) DEFAULT NULL COMMENT '规格',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='诊疗记录药品明细';

-- ----------------------------
-- Records of treatment_record_goods
-- ----------------------------
INSERT INTO `treatment_record_goods` VALUES ('916518017793425659', '913886336712220678', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '4', '1天3次，一次一袋', '26', '104', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('916518017793425660', '913886336712220678', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '一天3次，一次10粒（5g）', '313', '313', '', '', null, '', null, '', '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('924199395003793429', '924199395003793425', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '26', '26', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('924328209662951563', '924328209662951554', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '使用', '26', '26', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('924328209662951564', '924328209662951554', null, '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '3', '测试', '313', '939', null, null, null, null, null, null, '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('924328209662951585', '924328209662951572', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '', '26', '26', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925415489223876618', '925415489223876612', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '26', '26', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925415489223876639', '925415489223876632', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '123', '26', '26', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925415489223876640', '925415489223876632', null, '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '测试123', '313', '313', null, null, null, null, null, null, '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925440004897210408', '925440004897210390', null, '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '1', '0.01', '0.01', null, null, null, null, null, null, '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925440004897210409', '925440004897210390', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '12', '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925460637920108585', '925460637920108582', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925460637920108599', '925460637920108596', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925460637920108633', '925460637920108628', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925460637920108656', '925460637920108643', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925562566083993624', '925562566083993618', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925580020831100942', '925580020831100936', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925585552748986393', '925585552748986387', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925585552748986413', '925585552748986407', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925585552748986433', '925585552748986427', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925615222383083559', '925615222383083551', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '123', '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925615222383083560', '925615222383083551', null, '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '122', '0.01', '0.01', null, null, null, null, null, null, '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925615222383083572', '925615222383083566', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925682430032502845', '925682430032502839', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925682430032502953', '925682430032502946', null, '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '用法', '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('925682430032502954', '925682430032502946', null, '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '用户', '0.01', '0.01', null, null, null, null, null, null, '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928656712065032208', '928656712065032201', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '12212', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928656712065032209', '928656712065032201', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '12212', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928656712065032210', '928656712065032201', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '测试', '0.01', '0.01', '', '', null, '', null, '', '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928656712065032211', '928656712065032201', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '测试', '0.01', '0.01', '', '', null, '', null, '', '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506701', '928656712065032222', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '12212', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506702', '928656712065032222', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '测试', '0.01', '0.01', '', '', null, '', null, '', '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506725', '928719951163506722', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '12212', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506726', '928719951163506722', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '测试', '0.01', '0.01', '', '', null, '', null, '', '0.5g*36粒*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506779', '928719951163506772', null, '897455234946220717&发热 头疼', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('928719951163506796', '928719951163506790', null, '897455234946220717&发热 头疼', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('929891480802959392', '929891480802959381', null, '897455234946220717&发热 头疼', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('929891480802959414', '929891480802959404', null, '897455234946220717&发热 头疼', '999三九感冒灵颗粒', '盒', '1', null, '0.01', '0.01', null, null, null, null, null, null, '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('931512487359938604', '925460637920108567', '', '897455234946220717', '999三九感冒灵颗粒', '盒', '1', '冲服，每日2次 x3 天', '0.01', '0.01', '', '', null, '', null, '', '10g*9袋*1盒');
INSERT INTO `treatment_record_goods` VALUES ('931512487359938605', '925460637920108567', '', '234535456657766346', '广药白云山复方银翘片（盒）', '盒', '1', '口服，每日2次 x3 天', '0.01', '0.01', '', '', null, '', null, '', '0.5g*36粒*1盒');

-- ----------------------------
-- Table structure for treatment_reserve
-- ----------------------------
DROP TABLE IF EXISTS `treatment_reserve`;
CREATE TABLE `treatment_reserve` (
  `reserve_id` varchar(32) NOT NULL COMMENT '主键id',
  `reserve_no` varchar(40) NOT NULL COMMENT '预约单号',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `pay_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `device_code` varchar(32) DEFAULT NULL COMMENT '设备编号',
  `device_name` varchar(80) DEFAULT NULL COMMENT '设备名称',
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `doctor_name` varchar(40) DEFAULT NULL COMMENT '医生名称',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT '医生id',
  `reserve_date` datetime DEFAULT NULL COMMENT '预约日期',
  `reserve_time` varchar(20) DEFAULT NULL COMMENT '预约时间段',
  `reserve_source` varchar(20) DEFAULT NULL COMMENT '订单来源',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `back_status` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `back_time` datetime DEFAULT NULL COMMENT '退款时间',
  `pay_type` varchar(10) DEFAULT NULL COMMENT '付款方式',
  `pay_id` varchar(40) DEFAULT NULL COMMENT '收单方id',
  `pay_money` float DEFAULT NULL COMMENT '付款金额',
  `order_money` float DEFAULT NULL COMMENT '订单金额',
  `discount_money` float DEFAULT NULL COMMENT '优惠金额',
  `member_name` varchar(40) DEFAULT NULL COMMENT '购买会员',
  `member_id` varchar(32) DEFAULT NULL COMMENT '购买会员id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '商户名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `id_card` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `social_card` varchar(40) DEFAULT NULL COMMENT '社保卡号',
  `mobile_no` varchar(40) DEFAULT NULL COMMENT '手机号',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `resourcedoctor_id` varchar(32) DEFAULT NULL COMMENT 'resourcedoctor_id',
  `resourcedevice_id` varchar(32) DEFAULT NULL COMMENT 'resourcedevice_id',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`reserve_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约订单管理';

-- ----------------------------
-- Records of treatment_reserve
-- ----------------------------
INSERT INTO `treatment_reserve` VALUES ('919433716472020995', '2', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '   ', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null, null, null, null, '2020-09-11 10:12:25', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919433716472020997', '3', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '   ', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null, null, null, null, '2020-09-11 10:14:01', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919433716472021034', '4', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-11 11:14:51', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919433716472021087', '5', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-11 11:21:43', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919707357428482048', '1', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-11 14:34:36', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919707357428482054', '6', null, '3', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-11 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-11 15:18:27', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919884688038215714', '1', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '贺六浑', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null, null, null, null, '2020-09-14 10:36:39', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919884688038215719', '2', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '林俊锋1', 'ouYzq0EAMlfXwlGtWyofuBBLxFqU', null, null, null, null, null, null, null, null, null, '2020-09-14 10:44:37', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('919884688038215721', '1', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 10:54:05', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924080664928862221', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:38:42', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924080664928862225', 'e8cdb7ea09564198b20420279555d5a0', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:45:37', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924108994532122629', '3', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:48:57', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924108994532122632', '4', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:51:13', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924108994532122636', '20200914e2c96cd89aaa401399ff174dbe39d084', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:52:06', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924108994532122638', '20200914d6372f3c84d040ca8396e7137cd0bb5f', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 13:54:00', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924125143609163776', '5', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 14:00:57', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924125143609163780', '6d1c41bd254642c6b253fd1619f2cdeb', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 14:01:25', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924166753252335616', '6', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 14:44:00', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924166753252335623', 'c5d881264d454946aa682a3809620ff4', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 15:19:25', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 14:44:32', null, '2020-09-14 15:19:25', null);
INSERT INTO `treatment_reserve` VALUES ('924199395003793408', '7', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 15:16:18', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924199395003793412', '8f42d6b0f6234959b6e7bc700da22695', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 15:17:41', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 15:17:03', null, '2020-09-14 15:17:41', null);
INSERT INTO `treatment_reserve` VALUES ('924199395003793421', '8', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 15:21:09', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924199395003793425', '6f61c7b564774eb7a3b77231f6a6129d', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 15:21:49', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 15:21:35', null, '2020-09-14 15:21:49', null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935682', '9', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 15:57:31', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935689', '10', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:04:52', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935711', '11', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:15:27', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935719', '12', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:18:56', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935727', '13', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:20:42', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935731', '2efa1bd4239240e8ae69981ac7d52b9c', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 16:22:28', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:22:14', null, '2020-09-14 16:22:28', null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935739', '14', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:35:24', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924220783940935743', '084baf16b22d4ad49fe82a8ec0437568', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 16:36:07', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 16:35:54', null, '2020-09-14 16:36:07', null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951435', '3', null, '0', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 20:48:53', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951437', '15', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 20:50:37', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951441', 'df9fbb4c53bb40518503636c8e726743', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-14 20:51:34', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 20:51:19', null, '2020-09-14 20:51:34', null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951449', '16', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 21:01:48', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951457', '17', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-14 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-14 21:05:06', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951523', '1', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '贺六浑', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null, null, null, null, '2020-09-15 09:05:04', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951547', '2', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:13:56', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951549', '3', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:16:37', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951554', '4a07427c5d9d41c4811fb9b55f9fd8e7', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 10:19:49', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:19:35', null, '2020-09-15 10:19:49', null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951567', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:33:31', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('924328209662951572', '0546888e68dc4d93b47650fc4af04799', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 10:34:40', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:34:24', null, '2020-09-15 10:34:40', null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876608', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:55:55', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876612', 'ea90eb4959e848b4b333aec475938d9d', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 10:56:37', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 10:56:23', null, '2020-09-15 10:56:37', null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876623', '3', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '张三', 'ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw', null, null, null, null, null, null, null, null, null, '2020-09-15 11:03:03', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876625', '4', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:07:51', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876628', '5', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:09:49', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925415489223876632', '21cb3bd614934746a0ee307185f89db4', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 11:11:53', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:11:42', null, '2020-09-15 11:11:53', null);
INSERT INTO `treatment_reserve` VALUES ('925440004897210370', '6', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:17:34', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925440004897210375', '92ed5477d8fc43e0b863769d5f3f6def', null, null, '0', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:18:27', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925440004897210386', '7', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:22:09', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925440004897210390', 'aa881b49c9af44fc9e7b4e81268c1560', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 11:23:15', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 11:22:40', null, '2020-09-15 11:23:15', null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108553', '8', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '林俊锋1', 'ouYzq0EAMlfXwlGtWyofuBBLxFqU', null, null, null, null, null, null, null, null, null, '2020-09-15 11:45:51', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108567', 'a7696e853da445ff86cb259b6a32ba45', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 11:48:58', null, null, 'wxPay', null, '0.01', '0.01', null, '林俊锋1', 'ouYzq0EAMlfXwlGtWyofuBBLxFqU', null, null, null, null, null, null, null, null, null, '2020-09-15 11:48:36', null, '2020-09-15 11:48:58', null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108578', '4', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:28:44', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108582', '5284e8aa81084666a4b788487c2a7287', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 12:29:32', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:29:18', null, '2020-09-15 12:29:32', null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108591', '5', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:30:48', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108596', 'f5db998edf374d4691050575e3d93f63', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 12:31:48', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:31:31', null, '2020-09-15 12:31:48', null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108624', '6', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:44:39', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108628', '7e97338ce6df45d3b9c2ba894d9e1d27', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 12:45:30', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:45:13', null, '2020-09-15 12:45:30', null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108639', '7', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:54:00', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925460637920108643', '11519bc55e7b43c5ad1a99f3f884c224', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 12:54:45', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 12:54:25', null, '2020-09-15 12:54:45', null);
INSERT INTO `treatment_reserve` VALUES ('925562566083993600', '8', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:14:49', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925562566083993604', '6f8402a55ed44933a7458ba8dc96c6d7', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:16:04', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:15:32', null, '2020-09-15 13:16:04', null);
INSERT INTO `treatment_reserve` VALUES ('925562566083993614', '9', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:23:45', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925562566083993618', 'eb50ab3fc02543d996dd3d8d7bc49fc8', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:24:22', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:24:07', null, '2020-09-15 13:24:22', null);
INSERT INTO `treatment_reserve` VALUES ('925580020831100932', '10', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:32:51', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925580020831100936', '56fa28c2a1b5402db850e3088d30813f', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:33:35', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:33:18', null, '2020-09-15 13:33:35', null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986383', '11', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:38:39', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986387', '988891a503be49f5a5689268b61592a3', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:39:30', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:39:09', null, '2020-09-15 13:39:30', null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986403', '12', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:45:23', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986407', '3bf8ad56ca8943508980dfebf85b8eb0', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:46:06', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:45:49', null, '2020-09-15 13:46:06', null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986420', '13', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:50:59', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925585552748986427', '1f0251f21792422bb89ea9d23f49524b', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 13:52:36', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 13:52:18', null, '2020-09-15 13:52:36', null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083520', '9', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:06:06', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083524', 'd4b6543de8394d30b15ba6301ddedda5', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 14:07:53', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:07:16', null, '2020-09-15 14:07:53', null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083534', '14', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:14:29', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083536', '10', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:14:38', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083540', '03f66a19ad8243ac8487e259e965897f', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 14:15:59', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:15:43', null, '2020-09-15 14:15:59', null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083547', '11', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:22:52', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083551', '6c1cdcdc203748f7be6a19e18d73dd72', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 14:23:56', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:23:48', null, '2020-09-15 14:23:56', null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083562', '15', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:30:46', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083566', '853f4f446db54c96b107123187d23e92', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 14:31:26', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:31:11', null, '2020-09-15 14:31:26', null);
INSERT INTO `treatment_reserve` VALUES ('925615222383083583', '16', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 14:42:19', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925682430032502835', '17', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 15:28:03', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925682430032502839', '8876ca4c4cd642d49daa325cad64fc45', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 15:29:01', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 15:28:46', null, '2020-09-15 15:29:01', null);
INSERT INTO `treatment_reserve` VALUES ('925682430032502942', '12', null, '3', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 16:15:52', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925682430032502946', '03e58dada4b5467c8a66910c0c86af14', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 16:18:00', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 16:17:30', null, '2020-09-15 16:18:00', null);
INSERT INTO `treatment_reserve` VALUES ('925791161424568400', '18', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:16:18', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925791161424568404', 'd4e0eb3f44ce447e8800ec628188f782', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:17:28', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925791161424568407', 'd9062ce74f0c48878765de8c6b4b1270', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:20:18', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925791161424568409', '12eb1fa851fa4ffa839f80a2105df252', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 17:23:25', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:22:54', null, '2020-09-15 17:23:25', null);
INSERT INTO `treatment_reserve` VALUES ('925824507550670852', '19', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:31:42', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('925824507550670856', '0fc8a9a9ac164d2a85d4c08c19a89ca8', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-15 17:32:33', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:32:19', null, '2020-09-15 17:32:33', null);
INSERT INTO `treatment_reserve` VALUES ('925824507550670863', '20', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-15 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-15 17:35:36', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('927204411823505408', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-16 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 15:59:29', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('927204411823505417', '963ff5c6b4734c43976de504cd0a8b5f', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-16 16:01:29', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 16:01:13', null, '2020-09-16 16:01:29', null);
INSERT INTO `treatment_reserve` VALUES ('927223842255560704', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-16 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 16:06:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('927223842255560708', 'eea4c7ed980b43b3aa33a6122c7c5823', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-16 16:07:08', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 16:06:55', null, '2020-09-16 16:07:08', null);
INSERT INTO `treatment_reserve` VALUES ('927223842255560716', '3', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-16 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 16:21:42', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('927317352283553799', '1', null, '3', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-16 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-16 18:00:35', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('927317352283553805', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-17 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-17 10:56:08', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928656712065032196', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-17 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-17 16:04:35', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928656712065032201', '0f7a69802caf45969a506a794583fcc5', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-17 16:07:07', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-17 16:06:31', null, '2020-09-17 16:07:07', null);
INSERT INTO `treatment_reserve` VALUES ('928656712065032218', '3', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-17 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-17 16:11:46', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928656712065032222', '54502060a963406d94e58cd18c6eeb73', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-17 16:12:18', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-17 16:12:07', null, '2020-09-17 16:12:18', null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506707', '1', null, '2', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:12:19', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506718', '2', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:42:09', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506722', 'aac475bf2ab645bd98681c1962379946', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 08:43:10', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:42:44', null, '2020-09-18 08:43:10', null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506732', '3', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:48:44', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506736', '388b3d6e1a9d41fab2fa233ffa7cf8c8', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 08:49:34', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:49:10', null, '2020-09-18 08:49:34', null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506745', '4', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:56:53', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506749', '986ed4da3aa847cabb1ace6f5203ef02', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 08:57:39', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 08:57:25', null, '2020-09-18 08:57:39', null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506768', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 10:30:29', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506772', '246fa31dd0634b2d9d9a517213876aa0', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 10:31:17', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 10:31:02', null, '2020-09-18 10:31:17', null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506786', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 11:00:03', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('928719951163506790', '61db7642bdff441db94cf34114b41af9', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 11:01:00', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 11:00:46', null, '2020-09-18 11:01:00', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959377', '3', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 13:57:56', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959381', '4716cfa21c4b48ecbfba96306de35cfb', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 13:58:57', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 13:58:35', null, '2020-09-18 13:58:57', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959400', '4', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 14:06:07', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959404', 'c08fba30fb074fd2b29eeef12979ea36', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 14:09:38', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 14:07:36', null, '2020-09-18 14:09:38', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959421', '5', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 14:41:36', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959428', '6', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 14:46:28', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959433', 'c9432769f5464690ad4e7cd211e41c50', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 14:51:54', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 14:47:30', null, '2020-09-18 14:51:54', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959448', '7', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 15:08:55', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959453', '047b7a3e89224f8a9771d4f1a8c8a906', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 15:12:31', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 15:12:16', null, '2020-09-18 15:12:31', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959456', '2895367f23c14023a35019af87db3ef4', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 15:18:38', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 15:18:19', null, '2020-09-18 15:18:38', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959459', '9b77025cf00a467b8dc2e0b255fb9916', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 15:25:07', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 15:24:53', null, '2020-09-18 15:25:07', null);
INSERT INTO `treatment_reserve` VALUES ('929891480802959462', 'a53d5cfb202148f3a29cd17fa44f418c', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 15:26:25', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 15:26:14', null, '2020-09-18 15:26:25', null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134722', '171c82ad1a7f437b9b4a894b82819418', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 16:59:25', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 16:59:03', null, '2020-09-18 16:59:25', null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134723', '662a10e3780f413abc664be311ec9df7', null, '4', '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:01:10', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134724', 'd2e0814de49645d8937e0a2020d6466a', null, '4', '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:11:43', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134725', '1e5e3def65264584a6972b900fe9f0f7', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 17:04:35', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:04:22', null, '2020-09-18 17:04:35', null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134726', '3f87b5705b3a4aca81754fee5b849751', null, '4', '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:11:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134733', '925569e397ec4fa8bed255dfc321072f', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-18 17:19:33', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:19:15', null, '2020-09-18 17:19:33', null);
INSERT INTO `treatment_reserve` VALUES ('930211902543134734', '5952e6f6fdb84b1e9e7bd27f330e9ebf', null, '4', '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:20:22', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930239115456356352', '8', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 16:53:38', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930252361135071236', '9', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-18 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-18 17:18:46', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930252361135071245', '1', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 10:47:47', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930252361135071249', '2', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 10:49:56', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930252361135071253', '3', null, '4', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 10:51:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('930252361135071256', '1', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 10:57:35', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931373674016858114', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 11:17:07', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364547', '3', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:44:22', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364551', 'a315c2b1307a4d86a838b68495cebcba', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-19 12:45:12', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:44:55', null, '2020-09-19 12:45:12', null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364554', '31bbd6ff74204a2eb015b439ad61975b', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:46:05', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364556', '0d566b6d7171450694edc924886839ed', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:48:18', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364558', '4f4f1764004d4d2aaa0a9c2c4956c97a', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:50:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364564', '4', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:54:38', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364568', 'b7cd75a041944a46bf9ad98c1cecf8bb', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 12:55:08', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364593', '4', null, '3', null, 'testDev00001', '智慧问诊V1.0', '917948293442658371', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 13:07:58', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364597', '149cb9cf94bf4fa6af6b0a3fbc8fe39b', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-19 13:08:39', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 13:08:25', null, '2020-09-19 13:08:39', null);
INSERT INTO `treatment_reserve` VALUES ('931468300736364600', 'cfe839aa70e8484aaefb41d0227b68c3', null, null, '1', 'testDev00002', '智慧问诊V1.0', '917948293442658372', '张三丰', '892952030454973158', null, null, 'app', '2020-09-19 13:12:36', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 13:12:20', null, '2020-09-19 13:12:36', null);
INSERT INTO `treatment_reserve` VALUES ('931512487359938561', '5', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 15:13:54', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931512487359938565', 'e5e2c6641bde42dc9ef2c65ee626b610', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 15:15:06', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931512487359938577', '6', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-19 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:19', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('931512487359938581', 'faefa40a22cd4548bd514a33e2a7631e', null, null, '0', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', null, null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-19 15:50:37', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603845', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-21 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:22:18', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603852', 'fa65519be0384dc29c0c61e5487b00e3', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-21 10:26:15', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:25:45', null, '2020-09-21 10:26:15', null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603859', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-21 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:22', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603864', 'b6cd905767294e59860cf61ad9ed5ad6', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-21 10:29:54', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:29:40', null, '2020-09-21 10:29:54', null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603867', '74eea51b27214bde8768cc98dc2b212d', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-21 10:30:39', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:30:27', null, '2020-09-21 10:30:39', null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603876', '3', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-21 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:15', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('934289080637603880', 'aa901d26914e40398d8062e943173cf9', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-21 10:32:47', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-21 10:32:36', null, '2020-09-21 10:32:47', null);
INSERT INTO `treatment_reserve` VALUES ('937227662903017473', '1', null, '2', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-23 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-23 10:46:34', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('937227662903017475', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-23 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-23 10:50:49', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('937227662903017479', 'cc54823cb6e3484ab3108e83c2abbaa8', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-23 10:51:51', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-23 10:51:35', null, '2020-09-23 10:51:51', null);
INSERT INTO `treatment_reserve` VALUES ('937643054960066569', '3', null, '3', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-23 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:06', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('937643054960066573', 'a323a9ab21494c3cb454670ee390ff72', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-23 17:30:36', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-23 17:30:26', null, '2020-09-23 17:30:36', null);
INSERT INTO `treatment_reserve` VALUES ('943536643444113408', '1', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 15:54:27', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943536643444113414', '2', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 16:00:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943536643444113418', 'f73b60f6787c41a3807b1345f3879694', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 16:02:05', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 16:01:35', null, '2020-09-27 16:02:05', null);
INSERT INTO `treatment_reserve` VALUES ('943536643444113470', '3', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 16:38:26', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943536643444113476', '2532b15eaebd452ca149474014a72212', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 16:39:13', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 16:39:01', null, '2020-09-27 16:39:13', null);
INSERT INTO `treatment_reserve` VALUES ('943598284814761984', '51b26a37bced4bcd8bd7546f69b151f8', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 16:52:20', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 16:52:04', null, '2020-09-27 16:52:20', null);
INSERT INTO `treatment_reserve` VALUES ('943637197218471936', '4', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 17:29:32', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943637197218471946', 'ccebb10a817e4fcca168243c48ff1da6', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 17:33:04', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 17:32:33', null, '2020-09-27 17:33:04', null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171264', '5', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:00', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171270', '97fe4ea536144a6bae1ed8d64b2b4a8a', null, '4', '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 18:19:26', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 18:18:50', null, '2020-09-27 18:19:26', null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171278', '6', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 18:46:41', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171297', '7', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 18:54:20', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171301', '89f4b024f23f439091301a3b63e4f55b', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 18:57:36', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 18:57:14', null, '2020-09-27 18:57:36', null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171310', '8', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 19:09:07', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171314', '2ed2e80506464da9ab8105a549ee1aa5', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 19:10:13', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 19:09:59', null, '2020-09-27 19:10:13', null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171323', '9', null, '4', null, 'Dev00001', '智慧问诊V1.0', '893136353271611979', null, null, '2020-09-27 00:00:00', null, 'app', null, null, null, null, null, null, null, null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:02', null, null, null);
INSERT INTO `treatment_reserve` VALUES ('943683204908171328', 'fa066a70ff0249fa9bda370a828f4041', null, null, '1', 'Dev00002', '智慧问诊V1.0', '893136353271611988', '张三丰', '892952030454973158', null, null, 'app', '2020-09-27 19:21:42', null, null, 'wxPay', null, '0.01', '0.01', null, '陈家杰', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null, null, null, null, '2020-09-27 19:21:25', null, '2020-09-27 19:21:42', null);

-- ----------------------------
-- Table structure for treatment_resource_device
-- ----------------------------
DROP TABLE IF EXISTS `treatment_resource_device`;
CREATE TABLE `treatment_resource_device` (
  `resourcedevice_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '发布状态',
  `device_name` varchar(80) DEFAULT NULL COMMENT '设备名称',
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `reserve_date` datetime DEFAULT NULL COMMENT '预约日期',
  `time_1` varchar(10) DEFAULT NULL COMMENT '8:00-8:30',
  `time_2` varchar(10) DEFAULT NULL COMMENT '8:30-9:00',
  `time_3` varchar(10) DEFAULT NULL COMMENT '9:00-9:30',
  `time_4` varchar(10) DEFAULT NULL COMMENT '9:30-10:00',
  `time_5` varchar(10) DEFAULT NULL COMMENT '10:00-10:30',
  `time_6` varchar(10) DEFAULT NULL COMMENT '10:30-11:00',
  `time_7` varchar(10) DEFAULT NULL COMMENT '11:00-11:30',
  `time_8` varchar(10) DEFAULT NULL COMMENT '11:30-12:00',
  `time_9` varchar(10) DEFAULT NULL COMMENT '13:30-14:00',
  `time_10` varchar(10) DEFAULT NULL COMMENT '14:00-14:30',
  `time_11` varchar(10) DEFAULT NULL COMMENT '14:30-15:00',
  `time_12` varchar(10) DEFAULT NULL COMMENT '15:00-15:30',
  `time_13` varchar(10) DEFAULT NULL COMMENT '15:30-16:00',
  `time_14` varchar(10) DEFAULT NULL COMMENT '16:00-16:30',
  `time_15` varchar(10) DEFAULT NULL COMMENT '16:30-17:00',
  `time_16` varchar(10) DEFAULT NULL COMMENT '17:00-17:30',
  `time_17` varchar(10) DEFAULT NULL COMMENT '17:30-18:00',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`resourcedevice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约资源清单-设备';

-- ----------------------------
-- Records of treatment_resource_device
-- ----------------------------

-- ----------------------------
-- Table structure for treatment_resource_doctor
-- ----------------------------
DROP TABLE IF EXISTS `treatment_resource_doctor`;
CREATE TABLE `treatment_resource_doctor` (
  `resourcedoctor_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '发布状态',
  `doctor_name` varchar(40) DEFAULT NULL COMMENT '医生名称',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT '医生id',
  `reserve_date` datetime DEFAULT NULL COMMENT '预约日期',
  `time_1` int(11) DEFAULT NULL COMMENT '8:00-8:30',
  `time_2` int(11) DEFAULT NULL COMMENT '8:30-9:00',
  `time_3` int(11) DEFAULT NULL COMMENT '9:00-9:30',
  `time_4` int(11) DEFAULT NULL COMMENT '9:30-10:00',
  `time_5` int(11) DEFAULT NULL COMMENT '10:00-10:30',
  `time_6` int(11) DEFAULT NULL COMMENT '10:30-11:00',
  `time_7` int(11) DEFAULT NULL COMMENT '11:00-11:30',
  `time_8` int(11) DEFAULT NULL COMMENT '11:30-12:00',
  `time_9` int(11) DEFAULT NULL COMMENT '13:30-14:00',
  `time_10` int(11) DEFAULT NULL COMMENT '14:00-14:30',
  `time_11` int(11) DEFAULT NULL COMMENT '14:30-15:00',
  `time_12` int(11) DEFAULT NULL COMMENT '15:00-15:30',
  `time_13` int(11) DEFAULT NULL COMMENT '15:30-16:00',
  `time_14` int(11) DEFAULT NULL COMMENT '16:00-16:30',
  `time_15` int(11) DEFAULT NULL COMMENT '16:30-17:00',
  `time_16` int(11) DEFAULT NULL COMMENT '17:00-17:30',
  `time_17` int(11) DEFAULT NULL COMMENT '17:30-18:00',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`resourcedoctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约资源清单-医生';

-- ----------------------------
-- Records of treatment_resource_doctor
-- ----------------------------

-- ----------------------------
-- Table structure for treatment_results
-- ----------------------------
DROP TABLE IF EXISTS `treatment_results`;
CREATE TABLE `treatment_results` (
  `results_id` varchar(32) NOT NULL COMMENT '诊疗结果id',
  `results_name` varchar(32) NOT NULL COMMENT '诊疗结果名称',
  `results_code` varchar(32) NOT NULL COMMENT '名称首字母拼音',
  `results_level` int(11) DEFAULT NULL COMMENT '层级',
  `results_sort` int(11) DEFAULT NULL COMMENT '排序',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`results_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='诊疗结果';

-- ----------------------------
-- Records of treatment_results
-- ----------------------------
INSERT INTO `treatment_results` VALUES ('946534083940327521', '鼻炎', 'BY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327522', '慢性鼻炎', 'MXBY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327523', '肥厚性鼻炎', 'FHXBY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327524', '溃疡性鼻炎', 'KYXBY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327525', '萎缩性鼻炎', 'WSXBY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327526', '过敏性鼻炎', 'GMXBY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327527', '急性上呼吸道感染', 'JXSHXDGR', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327528', '夜盲症', 'YMZ', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327529', '视神经炎', 'SSJY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327530', '慢性青光眼', 'MXQGY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327531', '急性青光眼', 'JXQGY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327532', '白内障', 'BNZ', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327533', '虹膜炎', 'HMY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327534', '巩膜炎', 'GMY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');
INSERT INTO `treatment_results` VALUES ('946534083940327535', '结膜炎', 'JMY', null, null, '', '', 'admin', '2020-09-29 16:45:31', null, null, '');

-- ----------------------------
-- Table structure for treatment_results_goods
-- ----------------------------
DROP TABLE IF EXISTS `treatment_results_goods`;
CREATE TABLE `treatment_results_goods` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `results_id` varchar(32) DEFAULT NULL COMMENT '诊疗结果id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '主键id',
  `goods_name` varchar(80) DEFAULT NULL COMMENT '商品名称',
  `type_name` varchar(80) DEFAULT NULL COMMENT '商品分类',
  `type_id` varchar(32) DEFAULT NULL COMMENT '商品分类id',
  `goods_size` varchar(200) DEFAULT NULL COMMENT '规格',
  `bar_code` varchar(40) DEFAULT NULL COMMENT '商品条码号',
  `machine_price` float DEFAULT NULL COMMENT '设备价',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `goods_descript` text COMMENT '商品介绍',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片',
  `expiry_date` varchar(20) DEFAULT NULL COMMENT '有效期',
  `is_free` varchar(10) DEFAULT NULL COMMENT '是否赠品',
  `is_oct` varchar(10) DEFAULT NULL COMMENT '是否处方药',
  `is_tcm` varchar(10) DEFAULT NULL COMMENT '是否中药成分',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `sick_label` varchar(200) DEFAULT NULL COMMENT '治疗疾病标签',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='诊疗结果推荐药品';

-- ----------------------------
-- Records of treatment_results_goods
-- ----------------------------
INSERT INTO `treatment_results_goods` VALUES ('946534083940327541', '946534083940327521', '234535456657766346', '广药白云山复方银翘片（盒）', '药品', '1002', '0.5g*36粒*1盒', '2424234', '0.01', '盒', '', '', '', '0', '0', '0', null, null, null, null, null, null, null, '');
INSERT INTO `treatment_results_goods` VALUES ('946534083940327630', '946534083940327522', '234535456657766346', '广药白云山复方银翘片（盒）', '药品', '1002', '0.5g*36粒*1盒', '2424234', '0.01', '盒', '', '', '', '0', '0', '0', null, null, null, null, null, null, null, '');
INSERT INTO `treatment_results_goods` VALUES ('946534083940327632', '946534083940327522', '897455234946220717', '999三九感冒灵颗粒', '药品', '1002', '10g*9袋*1盒', '0.017kg', '0.01', '盒', '', '', '', '0', '0', '0', null, null, null, null, null, null, null, '发热 头疼');
INSERT INTO `treatment_results_goods` VALUES ('947713825557225530', '946534083940327527', '947713825557225514', '感康 复方氨酚烷胺片 ', '', '', '6片/板*2板/盒', '3212222', '12.5', '', '感冒药缓解发热头痛咽喉肿痛流涕鼻塞', '', '', '0', '0', '0', null, null, null, null, null, null, null, '上呼吸道感染,鼻炎');

-- ----------------------------
-- Table structure for user_doctor
-- ----------------------------
DROP TABLE IF EXISTS `user_doctor`;
CREATE TABLE `user_doctor` (
  `doctor_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(10) DEFAULT NULL COMMENT '是否发布',
  `user_code` varchar(40) DEFAULT NULL COMMENT '系统帐号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '系统帐号id',
  `doctor_name` varchar(40) DEFAULT NULL COMMENT '专家名称',
  `hospital_name` varchar(32) DEFAULT NULL COMMENT '所在医院',
  `special_area` varchar(1000) DEFAULT NULL COMMENT '专业领域',
  `descript` text COMMENT '专家简介',
  `img_url` varchar(400) DEFAULT NULL COMMENT '照片url',
  `class_type` varchar(20) DEFAULT NULL COMMENT '值班方式',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `doctor_type` varchar(20) DEFAULT NULL COMMENT '专家类型',
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `id_card` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `qualify_no` varchar(40) DEFAULT NULL COMMENT '资格证号',
  `from_date` datetime DEFAULT NULL COMMENT '从医起始日期',
  `office_room` varchar(40) DEFAULT NULL COMMENT '出诊科室',
  `qualify_level` varchar(20) DEFAULT NULL COMMENT '资质级别',
  `mobile_no` varchar(40) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `sum_money` float DEFAULT NULL COMMENT '总收入',
  `balance_money` float DEFAULT NULL COMMENT '余额',
  `applying_money` float DEFAULT NULL COMMENT '申请提现中',
  `special_price` float DEFAULT NULL COMMENT '专家问诊价格',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期',
  `normal_price` float DEFAULT NULL COMMENT '普通问诊价格',
  `hospital_id` varchar(32) DEFAULT NULL,
  `office_room_id` varchar(32) DEFAULT NULL,
  `professional` varchar(255) DEFAULT NULL,
  `serv_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家库管理';

-- ----------------------------
-- Records of user_doctor
-- ----------------------------
INSERT INTO `user_doctor` VALUES ('832259332197646892', '', '1', 'test', 'a1001', '张医生', '广东省人民医院', '普外科', '', '/static/images/upload/e85c72250ca54c85bb971742b83eb8ec.png', '1', '', 'admin', '2020-07-14 16:51:50', 'admin', '2020-09-16 10:49:54', '0', '1', '', '', '', null, '外科', '', '', '', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user_doctor` VALUES ('892952030454973158', '', '1', 'dt1001', '892952030454973138', '张三丰', '广东省中医院', '内科', '张三丰，男，副主任医师，博士，1997年毕业于北京医科大学临床医学系，2003年获得医学博士学位。历任北京大学第三医院消化内科住院医师、主治医师、副主任医师，有丰富的临床实践经验。擅长消化系统疾病的诊治。能独立开展多种内镜诊治技术。专业方向为消化内镜下微创治疗，特别擅长内镜下逆行胰胆管造影（ERCP）相关的内镜治疗技术，如胆道结石取石，胆道支架置入术。', '/static/images/upload/50b0b47405604e40a46fa8c4e1b9cee6.jpg', '1', '', 'admin', '2020-08-24 14:17:57', 'admin', '2020-08-27 16:48:05', '0', '1', '男', '', 'D122112121212121', '2007-06-04 16:47:58', '内科', '副主任医师', '18664792611', '广东省中山市板芙中路1号', '0', '0', null, null, '2020-07-01 09:56:41', '0.01', '2452460', '1', null, '1');
INSERT INTO `user_doctor` VALUES ('897133662154752565', '', '1', 'yj1001', '894578740728807973', '陈晓敏', '广东省人民医院', '中药学 西药学', '主任药师，临床医学和药学双本科学历，省中西医结合学会临床药学分会委员，省科普作家，台州市医学会临床药学分会常委和台州市药事质控中心委员，台州市医疗事故鉴定专家组专家等。擅长医院药事管理、药理学教学和药学科普工作。2012年被省药学会授予优秀医院药师称号，2011年被温岭市政府授予食品药品安全工作先进个人。发表论文十几篇，其中一篇获得台州市自然科学学术奖。科普文章多次在省有关比赛中获奖。', '', '1', '', 'admin', '2020-08-27 11:18:53', 'admin', '2020-09-16 11:11:55', '0', '2', '女', '', '', '2020-04-01 09:57:25', '中西药科', '', '', '', null, null, null, null, '2020-06-01 09:56:48', '0.01', '2452450', null, null, '1');

-- ----------------------------
-- Table structure for user_doctor_schedual
-- ----------------------------
DROP TABLE IF EXISTS `user_doctor_schedual`;
CREATE TABLE `user_doctor_schedual` (
  `schedual_id` varchar(32) NOT NULL COMMENT '主键id',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT 'doctor_id',
  `week_day` varchar(20) DEFAULT NULL COMMENT '星期',
  `schedual_date` datetime DEFAULT NULL COMMENT '排班日期',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `time_1` varchar(10) DEFAULT NULL COMMENT '8:00-8:30',
  `time_2` varchar(10) DEFAULT NULL COMMENT '8:30-9:00',
  `time_3` varchar(10) DEFAULT NULL COMMENT '9:00-9:30',
  `time_4` varchar(10) DEFAULT NULL COMMENT '9:30-10:00',
  `time_5` varchar(10) DEFAULT NULL COMMENT '10:00-10:30',
  `time_6` varchar(10) DEFAULT NULL COMMENT '10:30-11:00',
  `time_7` varchar(10) DEFAULT NULL COMMENT '11:00-11:30',
  `time_8` varchar(10) DEFAULT NULL COMMENT '11:30-12:00',
  `time_9` varchar(10) DEFAULT NULL COMMENT '12:00-12:30',
  `time_10` varchar(10) DEFAULT NULL COMMENT '12:30-13:00',
  `time_11` varchar(10) DEFAULT NULL COMMENT '13:00-13:30',
  `time_12` varchar(10) DEFAULT NULL COMMENT '13:30-14:00',
  `time_13` varchar(10) DEFAULT NULL COMMENT '14:00-14:30',
  `time_14` varchar(10) DEFAULT NULL COMMENT '14:30-15:00',
  `time_15` varchar(10) DEFAULT NULL COMMENT '15:00-15:30',
  `time_16` varchar(10) DEFAULT NULL COMMENT '15:30-16:00',
  `time_17` varchar(10) DEFAULT NULL COMMENT '16:00-16:30',
  `time_18` varchar(10) DEFAULT NULL COMMENT '16:30-17:00',
  `time_19` varchar(10) DEFAULT NULL COMMENT '17:00-17:30',
  `time_20` varchar(10) DEFAULT NULL COMMENT '17:30-18:00',
  `time_21` varchar(10) DEFAULT NULL COMMENT '18:00-18:30',
  `time_22` varchar(10) DEFAULT NULL COMMENT '18:30-19:00',
  `time_23` varchar(10) DEFAULT NULL COMMENT '19:00-19:30',
  `time_24` varchar(10) DEFAULT NULL COMMENT '19:30-20:00',
  `time_25` varchar(10) DEFAULT NULL COMMENT '20:00-20:30',
  `time_26` varchar(10) DEFAULT NULL COMMENT '20:30-21:00',
  `time_27` varchar(10) DEFAULT NULL COMMENT '21:00-21:30',
  `time_28` varchar(10) DEFAULT NULL COMMENT '21:30-22:00',
  `time_29` varchar(10) DEFAULT NULL COMMENT '22:00-22:30',
  `time_30` varchar(10) DEFAULT NULL COMMENT '22:30-23:00',
  `time_31` varchar(10) DEFAULT NULL COMMENT '23:00-23:30',
  `time_32` varchar(10) DEFAULT NULL COMMENT '23:30-24:00',
  `time_33` varchar(10) DEFAULT NULL COMMENT '0:00-0:30',
  `time_34` varchar(10) DEFAULT NULL COMMENT '0:30-1:00',
  `time_35` varchar(10) DEFAULT NULL COMMENT '1:00-1:30',
  `time_36` varchar(10) DEFAULT NULL COMMENT '1:30-2:00',
  `time_37` varchar(10) DEFAULT NULL COMMENT '2:00-2:30',
  `time_38` varchar(10) DEFAULT NULL COMMENT '2:30-3:00',
  `time_39` varchar(10) DEFAULT NULL COMMENT '3:00-3:30',
  `time_40` varchar(10) DEFAULT NULL COMMENT '3:30-4:00',
  `time_41` varchar(10) DEFAULT NULL COMMENT '4:00-4:30',
  `time_42` varchar(10) DEFAULT NULL COMMENT '4:30-5:00',
  `time_43` varchar(10) DEFAULT NULL COMMENT '5:00-5:30',
  `time_44` varchar(10) DEFAULT NULL COMMENT '5:30-6:00',
  `time_45` varchar(10) DEFAULT NULL COMMENT '6:00-6:30',
  `time_46` varchar(10) DEFAULT NULL COMMENT '6:30-7:00',
  `time_47` varchar(10) DEFAULT NULL COMMENT '7:00-7:30',
  `time_48` varchar(10) DEFAULT NULL COMMENT '7:30-8:00',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`schedual_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家排班明细';

-- ----------------------------
-- Records of user_doctor_schedual
-- ----------------------------
INSERT INTO `user_doctor_schedual` VALUES ('892530281846260505', '832259332197646892', '3', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user_doctor_schedual` VALUES ('892530281846260508', '832259332197646892', '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `user_doctor_schedual` VALUES ('894558520022770203', '892952030454973158', null, '2020-08-26 00:00:00', '', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '2020-08-25 16:04:16', null, null, null);
INSERT INTO `user_doctor_schedual` VALUES ('897455234946220735', '892952030454973158', null, '2020-08-22 00:00:00', '', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '2020-08-27 16:43:38', null, null, null);
INSERT INTO `user_doctor_schedual` VALUES ('897455234946220753', '892952030454973158', null, '2020-08-28 00:00:00', '', '1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '2020-08-27 17:29:11', null, null, null);
INSERT INTO `user_doctor_schedual` VALUES ('898657739889869372', '892952030454973158', null, '2020-08-29 00:00:00', '', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, '2020-08-28 10:47:37', null, null, null);

-- ----------------------------
-- Table structure for user_member
-- ----------------------------
DROP TABLE IF EXISTS `user_member`;
CREATE TABLE `user_member` (
  `member_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '激活状态',
  `member_name` varchar(40) DEFAULT NULL COMMENT '会员姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `both_date` datetime DEFAULT NULL COMMENT '出生日期',
  `address` varchar(200) DEFAULT NULL COMMENT '住址',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '手机号',
  `e_mail` varchar(80) DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `social_card` varchar(40) DEFAULT NULL COMMENT '社保卡号',
  `member_level` varchar(20) DEFAULT NULL COMMENT '会员级别',
  `usable_point` int(11) DEFAULT NULL COMMENT '可用积分',
  `total_point` int(11) DEFAULT NULL COMMENT '总积分',
  `order_num` int(11) DEFAULT NULL COMMENT '累计订单数',
  `order_money` float DEFAULT NULL COMMENT '累计金额',
  `nick_name` varchar(40) DEFAULT NULL COMMENT '昵称',
  `user_code` varchar(40) DEFAULT NULL COMMENT '登录帐号',
  `user_password` varchar(40) DEFAULT NULL COMMENT '登录密码',
  `limit_date` datetime DEFAULT NULL COMMENT '会员有效期',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `img_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `rec_method` varchar(20) DEFAULT NULL COMMENT '取药方式',
  `rec_id` varchar(32) DEFAULT NULL COMMENT '默认收件地址id',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员管理';

-- ----------------------------
-- Records of user_member
-- ----------------------------
INSERT INTO `user_member` VALUES ('892513600193274424', '', '1', '宾欢', '1', '2020-08-24 00:00:00', '', '', '', '', '', '1', '0', '0', '0', '0', 'bing', '', '', null, '', 'admin', '2020-08-24 06:59:16', 'admin', '2020-09-16 11:13:39', null, '0', null, null);
INSERT INTO `user_member` VALUES ('ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, '1', '贺六浑', '1', null, null, '13922281635', null, null, null, '1', '0', '0', '0', '0', '特立独行的猪', null, null, null, null, null, '2020-09-11 10:34:00', 'admin', '2020-09-16 11:13:39', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLykuenfu4blNicVPbApEmZapia3rgGCmZmWaR9lUaV4WEMKibmYdGeY8iafnibBLZ5m2ib6ib5Q5DlWupPg/132', null, '1', '919433716472021032');
INSERT INTO `user_member` VALUES ('ouYzq0EAMlfXwlGtWyofuBBLxFqU', null, '1', '张宏量', '1', null, null, '18148623218', null, null, null, '1', '0', '0', '0', '0', 'Ling', null, null, null, null, null, '2020-09-01 11:17:37', 'admin', '2020-09-16 11:13:39', 'https://thirdwx.qlogo.cn/mmopen/vi_32/ibESj1qh4iaKHIibxR29bSlicxYQKibO0wH9joALq4rz5FicJxUSTES9foaFbPrxIp33ibHpM1RMCpsxqRictrQywfkhFA/132', null, '0', null);
INSERT INTO `user_member` VALUES ('ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw', null, '1', '张三', '1', null, null, '13217552233', null, null, null, '1', '0', '0', '0', '0', '上弦月', null, null, null, null, null, '2020-09-15 11:02:42', 'admin', '2020-09-16 11:13:39', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erhq1bO0XtqvJibZRk8UUaliciaUbVcDpxGoQof2ZpnJN5vrx2QtDp3Jubia0qEYm0EiaNlSLgq3icl1oQA/132', null, '0', null);
INSERT INTO `user_member` VALUES ('ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw1', null, '1', '张四', '1', null, null, '13217552233', null, null, null, '1', '0', '0', '0', '0', '上弦月', null, null, null, null, null, '2020-09-11 15:29:32', 'admin', '2020-09-16 11:13:39', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erhq1bO0XtqvJibZRk8UUaliciaUbVcDpxGoQof2ZpnJN5vrx2QtDp3Jubia0qEYm0EiaNlSLgq3icl1oQA/132', null, '0', null);
INSERT INTO `user_member` VALUES ('ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, '1', '陈家杰', '1', null, null, '13610189059', null, null, null, '1', '0', '0', '0', '0', '  Chen 、', null, null, null, null, null, '2020-09-10 11:17:43', 'admin', '2020-09-16 11:13:39', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK8f72OTxKnCY31Picic2kF6Ilm522G37pSvbdo97GicIOasXYwL2J8VsNheNEbGCENx1nDOibib1Umt8A/132', null, '0', '927223842255560715');

-- ----------------------------
-- Table structure for user_member_address
-- ----------------------------
DROP TABLE IF EXISTS `user_member_address`;
CREATE TABLE `user_member_address` (
  `rec_id` varchar(32) NOT NULL COMMENT '配送地址id',
  `rec_user` varchar(40) DEFAULT NULL COMMENT '收件人姓名',
  `rec_phone` varchar(20) DEFAULT NULL COMMENT '收件人手机号',
  `rec_address` varchar(200) DEFAULT NULL COMMENT '收件地址',
  `rec_postcode` varchar(20) DEFAULT NULL COMMENT '邮编',
  `rec_default` varchar(10) DEFAULT NULL COMMENT '是否默认',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `member_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递收件地址';

-- ----------------------------
-- Records of user_member_address
-- ----------------------------
INSERT INTO `user_member_address` VALUES ('919433716472021004', '高澄-01', '13922280056', '河北邺城', '456896你好', '0', '2020-09-11 10:35:45', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, '2020-09-18 14:06:26', null);
INSERT INTO `user_member_address` VALUES ('919433716472021005', '高洋-02', '13922285029', '邺城第一胡同', '110563', '0', '2020-09-11 10:36:49', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null);
INSERT INTO `user_member_address` VALUES ('919433716472021006', '高浚-03', '13922287673', '山西平城', '4532651', '0', '2020-09-11 10:38:54', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null);
INSERT INTO `user_member_address` VALUES ('919433716472021007', '高淹-04', '13922287095', '河南洛阳', '4523265', '0', '2020-09-11 10:39:59', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, null, null);
INSERT INTO `user_member_address` VALUES ('919433716472021032', '忽律靳-05', '13822284536', '丹阳郡扬州路', '45236587', '0', '2020-09-11 10:48:38', 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', null, null, null, null, '2020-09-18 14:06:11', null);
INSERT INTO `user_member_address` VALUES ('927223842255560715', '陈家杰', '13610189056', '番禺节能科技园', '516100', '1', '2020-09-16 16:19:28', 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for user_member_man
-- ----------------------------
DROP TABLE IF EXISTS `user_member_man`;
CREATE TABLE `user_member_man` (
  `man_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `member_id` varchar(32) DEFAULT NULL COMMENT 'member_id',
  `man_name` varchar(40) DEFAULT NULL COMMENT '会员姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `both_date` datetime DEFAULT NULL COMMENT '出生日期',
  `address` varchar(200) DEFAULT NULL COMMENT '住址',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '手机号',
  `e_mail` varchar(80) DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(40) DEFAULT NULL COMMENT '身份证号',
  `social_card` varchar(40) DEFAULT NULL COMMENT '社保卡号',
  `past_history` text COMMENT '既往病史',
  `drug_allergy` text COMMENT '药物过敏史',
  `genetic_disease` text COMMENT '遗传病史',
  `height` varchar(40) DEFAULT NULL COMMENT '身高',
  `weight` varchar(40) DEFAULT NULL COMMENT '体重',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `nation` varchar(40) DEFAULT NULL COMMENT '民族',
  `marriage` varchar(20) DEFAULT NULL COMMENT '婚姻',
  `u_default` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`man_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='诊疗人明细';

-- ----------------------------
-- Records of user_member_man
-- ----------------------------
INSERT INTO `user_member_man` VALUES ('892530281846260592', '', '892513600193274424', '11', '1', '2020-08-20 00:00:00', '', '222333', '', '', '', '', '', '', '', '', '', '2020-08-24 08:30:24', 'admin', '2020-08-24 08:30:43', '0', null, null, null);
INSERT INTO `user_member_man` VALUES ('913886336712220673', null, 'ouYzq0Kt_N8YXmcQxs-p_CnINAA4', '陈家杰', '1', '1993-03-27 00:00:00', '广东省-广州市-番禺区', '13610189056', null, '441322199303275510', null, '既往史', '过敏史', '遗传病史', '180', '90', null, null, null, null, null, '汉', '0', '1');
INSERT INTO `user_member_man` VALUES ('916925902247632965', null, 'ouYzq0EAMlfXwlGtWyofuBBLxFqU', '张宏量', '1', '1984-01-01 00:00:00', '广东省-广州市-天河区', '18148623218', null, '440106198510080410', null, null, null, null, null, null, null, null, null, null, null, '汉族', '1', '1');
INSERT INTO `user_member_man` VALUES ('919433716472021003', null, 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', '01高澄-布宜诺斯艾利斯-阿根廷', '1', '2020-09-10 00:00:00', '广东省-广州市-番禺区', '13922281693', null, '440104200007316737', null, null, null, null, null, null, null, '2020-09-11 10:34:00', null, null, null, '鲜卑族鲜卑族鲜卑族鲜卑族', '1', '0');
INSERT INTO `user_member_man` VALUES ('919433716472021008', null, 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', '02高湛', '0', null, '广东省-广州市-番禺区', '13922293204', null, '440106198109309098', null, null, null, null, null, null, null, null, null, null, null, '鲜卑族', '1', '0');
INSERT INTO `user_member_man` VALUES ('919433716472021009', null, 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', '03高长恭', '0', null, '广东省-广州市-番禺区', '13922296310', null, '440103198508314995', null, null, null, null, null, null, null, null, null, null, null, '哈萨克族', '0', '0');
INSERT INTO `user_member_man` VALUES ('919707357428482064', null, 'ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw1', '张四', '1', '2020-09-11 00:00:00', '广东省-广州市-天河区', '13217552233', null, '440506200001012233', null, null, null, null, null, null, null, '2020-09-11 15:29:32', null, null, null, '汉族', '1', '1');
INSERT INTO `user_member_man` VALUES ('919884688038215708', null, 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', '04石勒', '1', null, '广东省-广州市-天河区', '13922295718', null, '220105200106056534', null, null, null, null, null, null, null, null, null, null, null, '羯族', '1', '0');
INSERT INTO `user_member_man` VALUES ('919884688038215709', null, 'ouYzq0AOMlEVvs4FTV3IWLqh76jM', '05石虎', '0', null, '广东省-广州市-天河区', '13922293201', null, '440101197506089999', null, null, null, null, null, null, null, null, null, null, null, '羯族', '0', '0');
INSERT INTO `user_member_man` VALUES ('925415489223876622', null, 'ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw', '张三', '1', '2020-09-15 00:00:00', '广东省-广州市-天河区', '13217552233', null, '550505200001012233', null, null, null, null, null, null, null, '2020-09-15 11:02:42', null, null, null, '汉族', '1', '1');

-- ----------------------------
-- Table structure for user_member_point
-- ----------------------------
DROP TABLE IF EXISTS `user_member_point`;
CREATE TABLE `user_member_point` (
  `vary_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `vary_type` varchar(20) DEFAULT NULL COMMENT '变动类型',
  `pre_point` int(11) DEFAULT NULL COMMENT '原积分',
  `vary_point` int(11) DEFAULT NULL COMMENT '变动积分',
  `post_point` int(11) DEFAULT NULL COMMENT '变动后积分',
  `vary_source` varchar(20) DEFAULT NULL COMMENT '变动来源',
  `source_id` varchar(200) DEFAULT NULL COMMENT '变动来源id',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`vary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分变动明细';

-- ----------------------------
-- Records of user_member_point
-- ----------------------------

-- ----------------------------
-- View structure for view_tool_data
-- ----------------------------
DROP VIEW IF EXISTS `view_tool_data`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_tool_data` AS (select `duo`.`tool_data`.`data_id` AS `data_id`,`duo`.`tool_data`.`add_date` AS `add_date`,`duo`.`tool_data`.`add_userid` AS `add_userid`,`duo`.`tool_data`.`data_color` AS `data_color`,`duo`.`tool_data`.`data_level` AS `data_level`,`duo`.`tool_data`.`data_text` AS `data_text`,`duo`.`tool_data`.`data_value` AS `data_value`,`duo`.`tool_data`.`memo` AS `memo`,`duo`.`tool_data`.`modify_date` AS `modify_date`,`duo`.`tool_data`.`modify_userid` AS `modify_userid`,`duo`.`tool_data`.`order_index` AS `order_index`,`duo`.`tool_data`.`project_id` AS `project_id`,`duo`.`tool_data`.`record_flag` AS `record_flag` from `duo`.`tool_data`) ;

-- ----------------------------
-- View structure for view_user_doctor_schedual
-- ----------------------------
DROP VIEW IF EXISTS `view_user_doctor_schedual`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_user_doctor_schedual` AS select `u`.`doctor_id` AS `doctor_id`,`d`.`data_id` AS `data_id`,`d`.`data_text` AS `data_text`,`d`.`data_value` AS `data_value`,`s`.`time_1` AS `time_1`,`s`.`time_2` AS `time_2`,`s`.`time_3` AS `time_3`,`s`.`time_4` AS `time_4`,`s`.`time_5` AS `time_5`,`s`.`time_6` AS `time_6`,`s`.`time_7` AS `time_7`,`s`.`time_8` AS `time_8`,`s`.`time_9` AS `time_9`,`s`.`time_10` AS `time_10`,`s`.`time_11` AS `time_11`,`s`.`time_12` AS `time_12`,`s`.`time_13` AS `time_13`,`s`.`time_14` AS `time_14`,`s`.`time_15` AS `time_15`,`s`.`time_16` AS `time_16`,`s`.`time_17` AS `time_17`,`s`.`memo` AS `memo`,`s`.`schedual_id` AS `schedual_id`,`s`.`record_flag` AS `record_flag` from ((`health`.`view_tool_data` `d` join `health`.`user_doctor` `u`) left join `health`.`user_doctor_schedual` `s` on(((`d`.`data_value` = `s`.`week_day`) and (`u`.`doctor_id` = `s`.`doctor_id`)))) where (`d`.`data_id` like '10041039____') ;
