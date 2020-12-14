/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.1.46-community-log : Database - health
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`health` /*!40100 DEFAULT CHARACTER SET gb2312 */;

USE `health`;

/*Table structure for table `advert_content` */

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

/*Data for the table `advert_content` */

/*Table structure for table `advert_place` */

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

/*Data for the table `advert_place` */

/*Table structure for table `base_field` */

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

/*Data for the table `base_field` */

insert  into `base_field`(`field_id`,`field_code`,`field_name`,`field_fullname`,`order_index`,`field_level`,`project_id`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('1001','GD','广东省','',1,1,'','','admin','2020-06-19 11:03:55','admin','2020-06-19 13:17:32','0'),('10011001','GZ','广州市','',5,2,'','','admin','2020-06-19 13:17:45',NULL,NULL,'0'),('100110011001','233','4444','',5,3,'','','admin','2020-06-24 16:12:55',NULL,NULL,'0'),('10011002','SZ','深圳市','',15,2,'','','admin','2020-06-19 13:17:47','admin','2020-06-19 13:18:27','0'),('10011003','FS','佛山市','',25,2,'','','admin','2020-06-19 13:17:50','admin','2020-06-19 13:18:27','0'),('10011004','ZS','中山市','',35,2,'','','admin','2020-06-19 13:17:50','admin','2020-06-19 13:18:27','0'),('10011005','ZH','珠海市','',45,2,'','','admin','2020-06-19 13:18:52',NULL,NULL,'0'),('10011006','JM','江门市','',55,2,'','','admin','2020-06-19 13:18:52','admin','2020-06-19 13:19:02','0');

/*Table structure for table `base_position` */

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

/*Data for the table `base_position` */

insert  into `base_position`(`position_id`,`project_id`,`auditing`,`position_code`,`field_name`,`field_id`,`address`,`longitude`,`latitude`,`position_property`,`is_indoor`,`provider_name`,`provider_id`,`contract_expire`,`usable_area`,`advantage`,`video_id`,`owner_username`,`owner_userid`,`dept_name`,`dept_id`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798371129697608196','',NULL,'2','深圳市','10011002','guangzhou','','','factory','1','2','798180862646370973','2020-06-21 20:48:25','','','','','','DUO信息科技有限公司','1001','','admin','2020-06-21 20:48:33',NULL,NULL,'0');

/*Table structure for table `comment_message` */

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

/*Data for the table `comment_message` */

/*Table structure for table `company_info` */

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

/*Data for the table `company_info` */

insert  into `company_info`(`company_id`,`project_id`,`auditing`,`company_type`,`company_name`,`company_code`,`company_sortname`,`field_id`,`field_name`,`address`,`contract_user`,`contract_phone`,`lose_memo`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798180862646370973','','0','drugstore','2','1','','10011001','广州市','','','','','','admin','2020-06-21 19:42:36',NULL,NULL,'0'),('800678575107721407','','0','drugstore','4665','的鬼地方个','','','','','','','','','admin','2020-06-24 16:14:04',NULL,NULL,'0');

/*Table structure for table `contract_info` */

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

/*Data for the table `contract_info` */

/*Table structure for table `coupon_activities` */

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

/*Data for the table `coupon_activities` */

/*Table structure for table `coupon_list` */

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

/*Data for the table `coupon_list` */

/*Table structure for table `device_connect` */

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

/*Data for the table `device_connect` */

/*Table structure for table `device_connect_message` */

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

/*Data for the table `device_connect_message` */

/*Table structure for table `device_control` */

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

/*Data for the table `device_control` */

/*Table structure for table `device_control_message` */

DROP TABLE IF EXISTS `device_control_message`;

CREATE TABLE `device_control_message` (
  `message_id` varchar(32) NOT NULL COMMENT '主键id',
  `control_id` varchar(32) DEFAULT NULL COMMENT 'control_id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `message_type` varchar(20) DEFAULT NULL COMMENT '指令类型',
  `plan_time` datetime DEFAULT NULL COMMENT '计划执行时间',
  `auditing` varchar(20) DEFAULT NULL COMMENT '执行状态',
  `file_name` varchar(80) DEFAULT NULL COMMENT '升级包版本',
  `file_id` varchar(32) DEFAULT NULL COMMENT 'file_id',
  `connect_message` text COMMENT '指令信息',
  `is_success` varchar(80) DEFAULT NULL COMMENT '是否执行成功',
  `call_backmessage` text COMMENT '反馈信息',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备控制指令明细';

/*Data for the table `device_control_message` */

/*Table structure for table `device_fault` */

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

/*Data for the table `device_fault` */

/*Table structure for table `device_group` */

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

/*Data for the table `device_group` */

insert  into `device_group`(`group_id`,`project_id`,`group_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('null1001','','zu1','','admin','2020-06-22 06:56:22',NULL,NULL,'0');

/*Table structure for table `device_group_device` */

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
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备分组明细';

/*Data for the table `device_group_device` */

/*Table structure for table `device_info` */

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
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备卡片';

/*Data for the table `device_info` */

insert  into `device_info`(`device_id`,`project_id`,`type_id`,`position_id`,`position_code`,`group_id`,`group_name`,`auditing`,`sell_ststue`,`online_ststue`,`temperature_value`,`humidity_value`,`soft_version`,`advert_version`,`dbm_value`,`device_kind`,`device_name`,`device_code`,`device_factory`,`device_type`,`device_size`,`device_price`,`out_date`,`install_date`,`use_date`,`use_age`,`device_weight`,`device_energy`,`cargo_num`,`has_stock`,`company_name`,`company_id`,`fix_ip`,`dept_name`,`dept_id`,`temperature_status`,`temperature_type`,`temperature_set`,`humidity_set`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798408908229969478',NULL,'798134099042443865',NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'medicine','售药1号',NULL,'维蓝信息','室外用','2*1*1米',NULL,NULL,NULL,NULL,3,200,1,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,'cool',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `device_info_cargo` */

DROP TABLE IF EXISTS `device_info_cargo`;

CREATE TABLE `device_info_cargo` (
  `cargo_id` varchar(32) NOT NULL COMMENT '主键id',
  `device_id` varchar(32) DEFAULT NULL COMMENT 'device_id',
  `group_name` varchar(20) DEFAULT NULL COMMENT '组柜',
  `cargo_code` varchar(60) DEFAULT NULL COMMENT '货道编号',
  `cargo_level` int(11) DEFAULT NULL COMMENT '层号',
  `cago_column` int(11) DEFAULT NULL COMMENT '列号',
  `merge_column` varchar(50) DEFAULT NULL COMMENT '合并列',
  `saft_num` int(11) DEFAULT NULL COMMENT '安全库存',
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

/*Data for the table `device_info_cargo` */

/*Table structure for table `device_info_cargo_store` */

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

/*Data for the table `device_info_cargo_store` */

/*Table structure for table `device_rent` */

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

/*Data for the table `device_rent` */

insert  into `device_rent`(`rent_id`,`project_id`,`auditing`,`rent_no`,`rent_date`,`begin_time`,`end_time`,`apply_username`,`apply_userid`,`company_name`,`company_id`,`check_username`,`check_userid`,`settle_type`,`per_price`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798393154289910305','',NULL,'','2020-06-21 21:08:17','2020-06-21 21:08:10','2020-06-21 21:08:12','','','','','','','free',0,'','admin','2020-06-21 21:08:25',NULL,NULL,'0');

/*Table structure for table `device_rent_det` */

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

/*Data for the table `device_rent_det` */

/*Table structure for table `device_rent_vary` */

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

/*Data for the table `device_rent_vary` */

/*Table structure for table `device_stop` */

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

/*Data for the table `device_stop` */

/*Table structure for table `device_type` */

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

/*Data for the table `device_type` */

insert  into `device_type`(`type_id`,`project_id`,`device_kind`,`device_name`,`device_factory`,`device_type`,`device_size`,`period_make`,`use_age`,`device_weight`,`device_energy`,`cargo_num`,`has_stock`,`temperature_status`,`temperature_type`,`temperature_set`,`humidity_set`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798134099042443865','','medicine','售药1号','维蓝信息','室外用','2*1*1米',22,3,200,1,120,'1','','cool',NULL,NULL,'','admin','2020-06-21 17:13:48','admin','2020-06-29 10:29:16','0');

/*Table structure for table `device_type_cargo` */

DROP TABLE IF EXISTS `device_type_cargo`;

CREATE TABLE `device_type_cargo` (
  `cargo_id` varchar(32) NOT NULL COMMENT '主键id',
  `type_id` varchar(32) DEFAULT NULL COMMENT 'type_id',
  `group_name` varchar(10) DEFAULT NULL COMMENT '组',
  `cargo_code` varchar(60) DEFAULT NULL COMMENT '货道编号',
  `cargo_level` int(11) DEFAULT NULL COMMENT '层号',
  `cago_column` int(11) DEFAULT NULL COMMENT '列号',
  `merge_column` varchar(50) DEFAULT NULL COMMENT '合并列',
  `saft_num` int(11) DEFAULT NULL COMMENT '安全库存',
  `max_num` int(11) DEFAULT NULL COMMENT '最大容量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`cargo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备标准货道';

/*Data for the table `device_type_cargo` */

/*Table structure for table `goods_list` */

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

/*Data for the table `goods_list` */

insert  into `goods_list`(`goods_id`,`project_id`,`auditing`,`bar_code`,`goods_name`,`type_name`,`type_id`,`goods_size`,`retail_price`,`machine_price`,`eshop_price`,`trade_price`,`unit`,`goods_descript`,`img_url`,`expiry_date`,`is_free`,`is_oct`,`is_tcm`,`sick_label`,`factory`,`is_certification`,`temperature_demand`,`humidity_demand`,`company_name`,`company_id`,`add_username`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798408908229970399','','0','2424234','h\'h\'h\'h红红火火','饮料','1001','',500,313,122,100,'','','','','0','0','0','','大世界','0','','','2','798180862646370973','','','admin','2020-06-22 06:44:42',NULL,NULL,'0');

/*Table structure for table `goods_type` */

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

/*Data for the table `goods_type` */

insert  into `goods_type`(`type_id`,`project_id`,`type_level`,`type_name`,`full_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('1001','',1,'饮料','','','admin','2020-06-21 21:37:18',NULL,NULL,'0'),('1002','',1,'药品','','','admin','2020-06-21 21:37:26',NULL,NULL,'0'),('1003','',1,'卫计用品','','','admin','2020-06-21 21:37:39',NULL,NULL,'0'),('1004','',1,'保健品','','','admin','2020-06-21 21:37:59',NULL,NULL,'0'),('1005','',1,'零食','','','admin','2020-06-21 21:38:06',NULL,NULL,'0');

/*Table structure for table `order_record` */

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

/*Data for the table `order_record` */

/*Table structure for table `store_check` */

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

/*Data for the table `store_check` */

/*Table structure for table `store_check_det` */

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

/*Data for the table `store_check_det` */

/*Table structure for table `store_in` */

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

/*Data for the table `store_in` */

insert  into `store_in`(`in_id`,`project_id`,`auditing`,`in_type`,`in_date`,`user_nane`,`user_id`,`stock_name`,`stock_id`,`in_num`,`in_money`,`company_name`,`company_id`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`,`in_no`) values ('798408908229971632','',NULL,'1','2020-06-22 08:53:33','系统管理员','admin','','',22,33,'2','798180862646370973','','admin','2020-06-22 08:53:45','admin','2020-06-22 08:53:52','0',NULL);

/*Table structure for table `store_in_det` */

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

/*Data for the table `store_in_det` */

/*Table structure for table `store_out` */

DROP TABLE IF EXISTS `store_out`;

CREATE TABLE `store_out` (
  `out_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(40) DEFAULT NULL COMMENT '出库单号',
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
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单';

/*Data for the table `store_out` */

/*Table structure for table `store_out_det` */

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

/*Data for the table `store_out_det` */

/*Table structure for table `store_stock` */

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

/*Data for the table `store_stock` */

/*Table structure for table `system_faultrule` */

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

/*Data for the table `system_faultrule` */

/*Table structure for table `system_paychannel` */

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

/*Data for the table `system_paychannel` */

insert  into `system_paychannel`(`paychannel_id`,`project_id`,`auditing`,`pay_type`,`pay_descript`,`pay_usercode`,`pay_password`,`company_name`,`company_id`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('798408908229970546','1001',NULL,'weixinpay',NULL,'112','3',NULL,NULL,NULL,'admin','2020-06-22 06:50:58','admin','2020-06-22 06:56:02','0'),('798408908229970677','1001',NULL,'alipay',NULL,'2','3',NULL,NULL,NULL,'admin','2020-06-22 06:56:02',NULL,NULL,'0');

/*Table structure for table `total_device` */

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

/*Data for the table `total_device` */

/*Table structure for table `treatment_reserve` */

DROP TABLE IF EXISTS `treatment_reserve`;

CREATE TABLE `treatment_reserve` (
  `reserve_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `pay_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
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

/*Data for the table `treatment_reserve` */

/*Table structure for table `treatment_resource_device` */

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

/*Data for the table `treatment_resource_device` */

/*Table structure for table `treatment_resource_doctor` */

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

/*Data for the table `treatment_resource_doctor` */

/*Table structure for table `user_doctor` */

DROP TABLE IF EXISTS `user_doctor`;

CREATE TABLE `user_doctor` (
  `doctor_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(10) DEFAULT NULL COMMENT '是否发布',
  `user_code` varchar(40) DEFAULT NULL COMMENT '系统帐号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '系统帐号id',
  `doctor_name` varchar(20) DEFAULT NULL COMMENT '专家名称',
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
  PRIMARY KEY (`doctor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家库管理';

/*Data for the table `user_doctor` */

/*Table structure for table `user_doctor_schedual` */

DROP TABLE IF EXISTS `user_doctor_schedual`;

CREATE TABLE `user_doctor_schedual` (
  `schedual_id` varchar(32) NOT NULL COMMENT '主键id',
  `doctor_id` varchar(32) DEFAULT NULL COMMENT 'doctor_id',
  `week_day` varchar(20) DEFAULT NULL COMMENT '星期',
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
  PRIMARY KEY (`schedual_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家排班明细';

/*Data for the table `user_doctor_schedual` */

/*Table structure for table `user_member` */

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
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员管理';

/*Data for the table `user_member` */

/*Table structure for table `user_member_point` */

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

/*Data for the table `user_member_point` */

/*Table structure for table `view_tool_data` */

DROP TABLE IF EXISTS `view_tool_data`;

/*!50001 DROP VIEW IF EXISTS `view_tool_data` */;
/*!50001 DROP TABLE IF EXISTS `view_tool_data` */;

/*!50001 CREATE TABLE  `view_tool_data`(
 `data_id` varchar(32) ,
 `add_date` datetime ,
 `add_userid` varchar(32) ,
 `data_color` varchar(10) ,
 `data_level` int(11) ,
 `data_text` varchar(80) ,
 `data_value` varchar(60) ,
 `memo` varchar(200) ,
 `modify_date` datetime ,
 `modify_userid` varchar(32) ,
 `order_index` int(11) ,
 `project_id` varchar(32) ,
 `record_flag` varchar(10) 
)*/;

/*View structure for view view_tool_data */

/*!50001 DROP TABLE IF EXISTS `view_tool_data` */;
/*!50001 DROP VIEW IF EXISTS `view_tool_data` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_tool_data` AS (select `duo`.`tool_data`.`data_id` AS `data_id`,`duo`.`tool_data`.`add_date` AS `add_date`,`duo`.`tool_data`.`add_userid` AS `add_userid`,`duo`.`tool_data`.`data_color` AS `data_color`,`duo`.`tool_data`.`data_level` AS `data_level`,`duo`.`tool_data`.`data_text` AS `data_text`,`duo`.`tool_data`.`data_value` AS `data_value`,`duo`.`tool_data`.`memo` AS `memo`,`duo`.`tool_data`.`modify_date` AS `modify_date`,`duo`.`tool_data`.`modify_userid` AS `modify_userid`,`duo`.`tool_data`.`order_index` AS `order_index`,`duo`.`tool_data`.`project_id` AS `project_id`,`duo`.`tool_data`.`record_flag` AS `record_flag` from `duo`.`tool_data`) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
