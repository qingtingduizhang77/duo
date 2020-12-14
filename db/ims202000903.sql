/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.1.46-community-log : Database - ims
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ims` /*!40100 DEFAULT CHARACTER SET gb2312 */;

USE `ims`;

/*Table structure for table `ims_store` */

DROP TABLE IF EXISTS `ims_store`;

CREATE TABLE `ims_store` (
  `store_id` varchar(32) NOT NULL COMMENT '主键id',
  `in_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `in_type` varchar(20) DEFAULT NULL COMMENT '入库类型',
  `in_date` datetime DEFAULT NULL COMMENT '入库时间',
  `user_name` varchar(40) DEFAULT NULL COMMENT '入库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '入库人id',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_user_nane` varchar(40) DEFAULT NULL COMMENT '审核人',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `house_code` varchar(40) DEFAULT NULL COMMENT '仓库号',
  `house_name` varchar(80) DEFAULT NULL COMMENT '仓库名称',
  `house_id` varchar(32) DEFAULT NULL COMMENT '仓库id',
  `provider_name` varchar(60) DEFAULT NULL COMMENT '供应商名称',
  `provider_id` varchar(32) DEFAULT NULL COMMENT '供应商id',
  `delivery_man` varchar(40) DEFAULT NULL COMMENT '送货人',
  `delivery_mobile` varchar(40) DEFAULT NULL COMMENT '送货人电话',
  `collect_no` varchar(60) DEFAULT NULL COMMENT '采购单号',
  `collect_id` varchar(32) DEFAULT NULL COMMENT '采购单id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `in_price` float DEFAULT NULL COMMENT '单价',
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
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存明细';

/*Data for the table `ims_store` */

insert  into `ims_store`(`store_id`,`in_id`,`goods_id`,`in_type`,`in_date`,`user_name`,`user_id`,`audit_date`,`audit_user_nane`,`audit_user_id`,`house_code`,`house_name`,`house_id`,`provider_name`,`provider_id`,`delivery_man`,`delivery_mobile`,`collect_no`,`collect_id`,`company_name`,`company_id`,`dept_id`,`dept_name`,`stock_id`,`stock_code`,`stock_name`,`in_price`,`in_num`,`batch_no`,`create_date`,`expiry_date`,`useable_num`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('904624737954185810',NULL,'894323207354180401',NULL,NULL,'系统管理员','admin',NULL,'','','G','广州仓','1003','','','eee','','','','','1004','1004','合资公司','10021001','X01','X01货架',23,1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `ims_store_check` */

DROP TABLE IF EXISTS `ims_store_check`;

CREATE TABLE `ims_store_check` (
  `check_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '盘点状态',
  `check_no` varchar(40) DEFAULT NULL COMMENT '盘点单号',
  `check_date` datetime DEFAULT NULL COMMENT '盘点日期',
  `user_name` varchar(40) DEFAULT NULL COMMENT '盘点人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '盘点人id',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_user_nane` varchar(40) DEFAULT NULL COMMENT '审核人',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `house_code` varchar(40) DEFAULT NULL COMMENT '仓库号',
  `house_name` varchar(60) DEFAULT NULL COMMENT '仓库名称',
  `house_id` varchar(32) DEFAULT NULL COMMENT '仓库id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`check_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存盘点管理';

/*Data for the table `ims_store_check` */

insert  into `ims_store_check`(`check_id`,`project_id`,`auditing`,`check_no`,`check_date`,`user_name`,`user_id`,`audit_date`,`audit_user_nane`,`audit_user_id`,`house_code`,`house_name`,`house_id`,`company_name`,`company_id`,`dept_id`,`dept_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('904654338868806212','','0','PD202009000001','2020-09-01 11:14:21','','',NULL,'','','G','广州仓','1003','','1004','','','','admin','2020-09-01 11:14:25',NULL,NULL,'');

/*Table structure for table `ims_store_check_det` */

DROP TABLE IF EXISTS `ims_store_check_det`;

CREATE TABLE `ims_store_check_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `check_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `store_money` float DEFAULT NULL COMMENT '金额',
  `store_price` float DEFAULT NULL COMMENT '单价',
  `store_num` float DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `check_result` varchar(20) DEFAULT NULL COMMENT '盘点结果',
  `fact_num` float DEFAULT NULL COMMENT '实际数量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `store_id` varchar(32) DEFAULT NULL COMMENT 'store_id',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点明细';

/*Data for the table `ims_store_check_det` */

insert  into `ims_store_check_det`(`det_id`,`check_id`,`goods_id`,`stock_id`,`stock_code`,`stock_name`,`store_money`,`store_price`,`store_num`,`batch_no`,`create_date`,`expiry_date`,`check_result`,`fact_num`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`,`store_id`) values ('904654338868806306','904654338868806212','894323207354180401','10021001','X01','X01货架',23,23,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'904624737954185810');

/*Table structure for table `ims_store_goods` */

DROP TABLE IF EXISTS `ims_store_goods`;

CREATE TABLE `ims_store_goods` (
  `goods_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '发布状态',
  `bar_code` varchar(40) DEFAULT NULL COMMENT '物品条码号',
  `goods_code` varchar(40) DEFAULT NULL COMMENT '物品号',
  `goods_name` varchar(80) DEFAULT NULL COMMENT '物品名称',
  `type_name` varchar(80) DEFAULT NULL COMMENT '物品分类',
  `type_id` varchar(32) DEFAULT NULL COMMENT '物品分类id',
  `goods_size` varchar(200) DEFAULT NULL COMMENT '规格',
  `goods_price` float DEFAULT NULL COMMENT '零售价',
  `last_price` float DEFAULT NULL COMMENT '最近进货价',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `goods_descript` text COMMENT '物品介绍',
  `img_url` varchar(200) DEFAULT NULL COMMENT '图片',
  `expiry_date` varchar(20) DEFAULT NULL COMMENT '有效期',
  `is_free` varchar(10) DEFAULT NULL COMMENT '是否赠品',
  `min_num` float DEFAULT NULL COMMENT '最小库存量',
  `max_num` float DEFAULT NULL COMMENT '最大库存量',
  `purchase_period` varchar(20) DEFAULT NULL COMMENT '采购周期',
  `brand` varchar(80) DEFAULT NULL COMMENT '品牌',
  `factory` varchar(80) DEFAULT NULL COMMENT '生产厂家',
  `param_name1` varchar(80) DEFAULT NULL COMMENT '参数1名称',
  `param_value1` varchar(200) DEFAULT NULL COMMENT '参数1值',
  `param_name2` varchar(80) DEFAULT NULL COMMENT '参数2名称',
  `param_value2` varchar(200) DEFAULT NULL COMMENT '参数2值',
  `param_name3` varchar(80) DEFAULT NULL COMMENT '参数3名称',
  `param_value3` varchar(200) DEFAULT NULL COMMENT '参数3值',
  `param_name4` varchar(80) DEFAULT NULL COMMENT '参数4名称',
  `param_value4` varchar(200) DEFAULT NULL COMMENT '参数4值',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `add_username` varchar(40) DEFAULT NULL COMMENT '创建人',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品目录';

/*Data for the table `ims_store_goods` */

insert  into `ims_store_goods`(`goods_id`,`project_id`,`auditing`,`bar_code`,`goods_code`,`goods_name`,`type_name`,`type_id`,`goods_size`,`goods_price`,`last_price`,`unit`,`goods_descript`,`img_url`,`expiry_date`,`is_free`,`min_num`,`max_num`,`purchase_period`,`brand`,`factory`,`param_name1`,`param_value1`,`param_name2`,`param_value2`,`param_name3`,`param_value3`,`param_name4`,`param_value4`,`company_name`,`company_id`,`dept_id`,`dept_name`,`add_username`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('894323207354180401','','1','111','1','搭棚','成品','1001','',0,0,'jian','','','','0',0,999,'','','','','','','','','','','','{CURRENTCOMPANYNAME}','1004','1004','合资公司','系统管理员','','admin','2020-08-25 15:05:06','admin','2020-08-27 14:52:43','0'),('906363357895606929','','1','1111','1111','渔网','成品','1001','',0,0,'jian','','','','0',0,999,'','','','','','','','','','','','{CURRENTCOMPANYNAME}','1004','1004','合资公司','系统管理员','','admin','2020-09-03 11:09:04','admin','2020-09-03 11:09:08','');

/*Table structure for table `ims_store_goodstype` */

DROP TABLE IF EXISTS `ims_store_goodstype`;

CREATE TABLE `ims_store_goodstype` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品分类';

/*Data for the table `ims_store_goodstype` */

insert  into `ims_store_goodstype`(`type_id`,`project_id`,`type_level`,`type_name`,`full_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('1001','',1,'成品','','','admin','2020-08-24 15:45:23',NULL,NULL,'0'),('1002','',1,'设备','','','admin','2020-08-24 15:45:29',NULL,NULL,'0'),('1003','',1,'材料','','','admin','2020-08-24 15:45:34',NULL,NULL,'0');

/*Table structure for table `ims_store_in` */

DROP TABLE IF EXISTS `ims_store_in`;

CREATE TABLE `ims_store_in` (
  `in_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `in_no` varchar(40) DEFAULT NULL COMMENT '入库单号',
  `in_type` varchar(20) DEFAULT NULL COMMENT '入库类型',
  `in_date` datetime DEFAULT NULL COMMENT '入库时间',
  `user_name` varchar(40) DEFAULT NULL COMMENT '入库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '入库人id',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_user_nane` varchar(40) DEFAULT NULL COMMENT '审核人',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `house_code` varchar(40) DEFAULT NULL COMMENT '入库仓库号',
  `house_name` varchar(80) DEFAULT NULL COMMENT '入库仓库',
  `house_id` varchar(32) DEFAULT NULL COMMENT '入库仓库id',
  `in_num` int(11) DEFAULT NULL COMMENT '入库数量',
  `in_money` float DEFAULT NULL COMMENT '入库金额',
  `provider_name` varchar(60) DEFAULT NULL COMMENT '供应商名称',
  `provider_id` varchar(32) DEFAULT NULL COMMENT '供应商id',
  `delivery_man` varchar(40) DEFAULT NULL COMMENT '送货人',
  `delivery_mobile` varchar(40) DEFAULT NULL COMMENT '送货人电话',
  `purchase_no` varchar(60) DEFAULT NULL COMMENT '采购单号',
  `purchase_id` varchar(32) DEFAULT NULL COMMENT '采购单id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单管理';

/*Data for the table `ims_store_in` */

insert  into `ims_store_in`(`in_id`,`project_id`,`auditing`,`in_no`,`in_type`,`in_date`,`user_name`,`user_id`,`audit_date`,`audit_user_nane`,`audit_user_id`,`house_code`,`house_name`,`house_id`,`in_num`,`in_money`,`provider_name`,`provider_id`,`delivery_man`,`delivery_mobile`,`purchase_no`,`purchase_id`,`company_name`,`company_id`,`dept_id`,`dept_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('897430942610629188','','1','RK202008000002','buy','2020-08-27 14:24:40','系统管理员','admin',NULL,'','','G','广州仓','1003',0,0,'','','eee','','','','','1004','1004','合资公司','','admin','2020-08-27 14:24:48','admin','2020-09-01 10:44:52','0');

/*Table structure for table `ims_store_in_det` */

DROP TABLE IF EXISTS `ims_store_in_det`;

CREATE TABLE `ims_store_in_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `in_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `in_price` float DEFAULT NULL COMMENT '单价',
  `in_num` float DEFAULT NULL COMMENT '数量',
  `in_money` float DEFAULT NULL COMMENT '金额',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `useable_num` float DEFAULT NULL COMMENT '剩余数量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库单明细';

/*Data for the table `ims_store_in_det` */

insert  into `ims_store_in_det`(`det_id`,`in_id`,`goods_id`,`stock_id`,`stock_code`,`stock_name`,`in_price`,`in_num`,`in_money`,`batch_no`,`create_date`,`expiry_date`,`useable_num`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('903094630084960840','897430942610629188','894323207354180401','10021001','X01','X01货架',23,1,23,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'admin','2020-08-31 14:29:11',NULL);

/*Table structure for table `ims_store_move` */

DROP TABLE IF EXISTS `ims_store_move`;

CREATE TABLE `ims_store_move` (
  `move_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `move_no` varchar(40) DEFAULT NULL COMMENT '移库单号',
  `move_type` varchar(20) DEFAULT NULL COMMENT '移库类型',
  `move_date` datetime DEFAULT NULL COMMENT '移库时间',
  `user_name` varchar(40) DEFAULT NULL COMMENT '移库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '移库人id',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_user_nane` varchar(40) DEFAULT NULL COMMENT '审核人',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `pre_house_code` varchar(40) DEFAULT NULL COMMENT '移出库仓库号',
  `pre_house_name` varchar(80) DEFAULT NULL COMMENT '移出库仓库',
  `pre_house_id` varchar(32) DEFAULT NULL COMMENT '移出库仓库id',
  `move_num` int(11) DEFAULT NULL COMMENT '移出库数量',
  `move_money` float DEFAULT NULL COMMENT '移出库金额',
  `house_code` varchar(40) DEFAULT NULL COMMENT '移入库仓库号',
  `house_name` varchar(80) DEFAULT NULL COMMENT '移入库仓库',
  `house_id` varchar(32) DEFAULT NULL COMMENT '移入库仓库id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`move_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='移库单管理';

/*Data for the table `ims_store_move` */

insert  into `ims_store_move`(`move_id`,`project_id`,`auditing`,`move_no`,`move_type`,`move_date`,`user_name`,`user_id`,`audit_date`,`audit_user_nane`,`audit_user_id`,`pre_house_code`,`pre_house_name`,`pre_house_id`,`move_num`,`move_money`,`house_code`,`house_name`,`house_id`,`company_name`,`company_id`,`dept_id`,`dept_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('897564825331270241','','0','YK202008000001','','2020-08-27 16:41:55','系统管理员','admin',NULL,'','','G','广州仓','1003',NULL,NULL,'S','韶山仓','1001','','1004','','','','admin','2020-08-27 16:42:03',NULL,NULL,'0');

/*Table structure for table `ims_store_move_det` */

DROP TABLE IF EXISTS `ims_store_move_det`;

CREATE TABLE `ims_store_move_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `move_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `move_money` float DEFAULT NULL COMMENT '金额',
  `move_price` float DEFAULT NULL COMMENT '单价',
  `move_num` float DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `pre_stock_id` varchar(32) DEFAULT NULL COMMENT '原stock_id',
  `pre_stock_code` varchar(40) DEFAULT NULL COMMENT '原仓库货架号',
  `pre_stock_name` varchar(80) DEFAULT NULL COMMENT '原仓库货架名',
  `stock_id` varchar(32) DEFAULT NULL COMMENT '新stock_id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '新仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '新仓库货架名',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `store_id` varchar(32) DEFAULT NULL COMMENT 'store_id',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='移库单明细';

/*Data for the table `ims_store_move_det` */

insert  into `ims_store_move_det`(`det_id`,`move_id`,`goods_id`,`move_money`,`move_price`,`move_num`,`batch_no`,`create_date`,`expiry_date`,`pre_stock_id`,`pre_stock_code`,`pre_stock_name`,`stock_id`,`stock_code`,`stock_name`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`,`store_id`) values ('904654338868806278','897564825331270241','894323207354180401',23,23,1,NULL,NULL,NULL,'10021001','X01','X01货架',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'904624737954185810');

/*Table structure for table `ims_store_out` */

DROP TABLE IF EXISTS `ims_store_out`;

CREATE TABLE `ims_store_out` (
  `out_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `auditing` varchar(20) DEFAULT NULL COMMENT '状态',
  `out_no` varchar(40) DEFAULT NULL COMMENT '出库单号',
  `out_type` varchar(20) DEFAULT NULL COMMENT '出库类型',
  `out_date` datetime DEFAULT NULL COMMENT '出库时间',
  `user_name` varchar(40) DEFAULT NULL COMMENT '出库人',
  `user_id` varchar(32) DEFAULT NULL COMMENT '出库人id',
  `audit_date` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_user_nane` varchar(40) DEFAULT NULL COMMENT '审核人',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `house_code` varchar(40) DEFAULT NULL COMMENT '出库仓库号',
  `house_name` varchar(80) DEFAULT NULL COMMENT '出库仓库',
  `house_id` varchar(32) DEFAULT NULL COMMENT '出库仓库id',
  `out_num` int(11) DEFAULT NULL COMMENT '出库数量',
  `out_money` float DEFAULT NULL COMMENT '出库金额',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `apply_user_nane` varchar(40) DEFAULT NULL COMMENT '领用人',
  `apply_user_id` varchar(32) DEFAULT NULL COMMENT '领用人id',
  `apply_season` varchar(200) DEFAULT NULL COMMENT '领用用途',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单管理';

/*Data for the table `ims_store_out` */

insert  into `ims_store_out`(`out_id`,`project_id`,`auditing`,`out_no`,`out_type`,`out_date`,`user_name`,`user_id`,`audit_date`,`audit_user_nane`,`audit_user_id`,`house_code`,`house_name`,`house_id`,`out_num`,`out_money`,`company_name`,`company_id`,`dept_id`,`dept_name`,`apply_user_nane`,`apply_user_id`,`apply_season`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('897564825331270254','','0','CK202008000001','use','2020-08-27 16:42:24','系统管理员','admin',NULL,'','','G','广州仓','1003',0,0,'','1004','1004','合资公司','xx','','','','admin','2020-08-27 16:42:41',NULL,NULL,'0');

/*Table structure for table `ims_store_out_det` */

DROP TABLE IF EXISTS `ims_store_out_det`;

CREATE TABLE `ims_store_out_det` (
  `det_id` varchar(32) NOT NULL COMMENT '主键id',
  `out_id` varchar(32) DEFAULT NULL COMMENT 'in_id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `stock_id` varchar(32) DEFAULT NULL COMMENT 'stock_id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `out_money` float DEFAULT NULL COMMENT '金额',
  `out_price` float DEFAULT NULL COMMENT '单价',
  `store_num` float DEFAULT NULL COMMENT '库存数量',
  `out_num` float DEFAULT NULL COMMENT '数量',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `create_date` datetime DEFAULT NULL COMMENT '生产日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '有效期至',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  `store_id` varchar(32) DEFAULT NULL COMMENT 'store_id',
  PRIMARY KEY (`det_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库单明细';

/*Data for the table `ims_store_out_det` */

insert  into `ims_store_out_det`(`det_id`,`out_id`,`goods_id`,`stock_id`,`stock_code`,`stock_name`,`out_money`,`out_price`,`store_num`,`out_num`,`batch_no`,`create_date`,`expiry_date`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`,`store_id`) values ('904654338868806291','897564825331270254','894323207354180401','10021001','X01','X01货架',69,23,1,3,NULL,NULL,NULL,NULL,NULL,NULL,'admin','2020-09-01 14:34:25',NULL,'904624737954185810');

/*Table structure for table `ims_store_stock` */

DROP TABLE IF EXISTS `ims_store_stock`;

CREATE TABLE `ims_store_stock` (
  `stock_id` varchar(32) NOT NULL COMMENT '主键id',
  `project_id` varchar(32) DEFAULT NULL COMMENT '项目id',
  `stock_code` varchar(40) DEFAULT NULL COMMENT '仓库货架号',
  `stock_name` varchar(80) DEFAULT NULL COMMENT '仓库货架名',
  `bar_code` varchar(40) DEFAULT NULL COMMENT '条码号',
  `inplace_descript` varchar(200) DEFAULT NULL COMMENT '位置描述',
  `stock_level` int(11) DEFAULT NULL COMMENT '级别',
  `user_name` varchar(40) DEFAULT NULL COMMENT '保管员',
  `user_id` varchar(32) DEFAULT NULL COMMENT '保管员id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `min_num` float DEFAULT NULL COMMENT '最小库存量',
  `max_num` float DEFAULT NULL COMMENT '最大库存量',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库货架管理';

/*Data for the table `ims_store_stock` */

insert  into `ims_store_stock`(`stock_id`,`project_id`,`stock_code`,`stock_name`,`bar_code`,`inplace_descript`,`stock_level`,`user_name`,`user_id`,`company_name`,`company_id`,`dept_id`,`dept_name`,`min_num`,`max_num`,`memo`,`add_userid`,`add_date`,`modify_userid`,`modify_date`,`record_flag`) values ('1001','','S','韶山仓','','',1,'系统管理员','admin','','','','',0,999,'','admin','2020-08-24 14:52:03','admin','2020-08-24 15:24:40','0'),('1002','','X','湘潭仓','','',1,'系统管理员','admin','','','','',0,999,'','admin','2020-08-24 15:10:24','admin','2020-08-24 15:10:39','0'),('10021001','','X01','X01货架','','',2,'','','','','','',0,999,'','admin','2020-08-24 15:28:52',NULL,NULL,'0'),('1003','','G','广州仓','','',1,'系统管理员','admin','','','','',0,999,'','admin','2020-08-24 15:14:15',NULL,NULL,'0');

/*Table structure for table `ims_store_total` */

DROP TABLE IF EXISTS `ims_store_total`;

CREATE TABLE `ims_store_total` (
  `total_id` varchar(32) NOT NULL COMMENT '主键id',
  `goods_id` varchar(32) DEFAULT NULL COMMENT 'goods_id',
  `total_month` varchar(40) DEFAULT NULL COMMENT '统计月份',
  `auditing` varchar(10) DEFAULT NULL COMMENT '状态',
  `house_code` varchar(40) DEFAULT NULL COMMENT '仓库号',
  `house_name` varchar(80) DEFAULT NULL COMMENT '仓库名称',
  `house_id` varchar(32) DEFAULT NULL COMMENT '仓库id',
  `company_name` varchar(60) DEFAULT NULL COMMENT '公司名称',
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司id',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(60) DEFAULT NULL COMMENT '部门名称',
  `store_price` float DEFAULT NULL COMMENT '库存单价',
  `pre_num` float DEFAULT NULL COMMENT '期初数量',
  `pre_money` float DEFAULT NULL COMMENT '期初金额',
  `in_num` float DEFAULT NULL COMMENT '入库数量',
  `in_money` float DEFAULT NULL COMMENT '入库金额',
  `out_num` float DEFAULT NULL COMMENT '出库数量',
  `out_money` float DEFAULT NULL COMMENT '出库金额',
  `post_num` float DEFAULT NULL COMMENT '期末数量',
  `post_money` float DEFAULT NULL COMMENT '期末金额',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `add_userid` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `add_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_userid` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `record_flag` varchar(10) DEFAULT NULL COMMENT '数据标识',
  PRIMARY KEY (`total_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存变动统计';

/*Data for the table `ims_store_total` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
