# Host: 127.0.0.1  (Version: 5.5.47)
# Date: 2023-09-12 23:45:41
# Generator: MySQL-Front 5.3  (Build 4.234)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "chat_ban"
#

DROP TABLE IF EXISTS `chat_ban`;
CREATE TABLE `chat_ban` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(500) DEFAULT '' COMMENT 'IP地址',
  `created` int(10) unsigned DEFAULT '0' COMMENT '封禁时间',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='黑名单表';

#
# Data for table "chat_ban"
#

/*!40000 ALTER TABLE `chat_ban` DISABLE KEYS */;
INSERT INTO `chat_ban` VALUES (4,'192.168.0.1',0);
/*!40000 ALTER TABLE `chat_ban` ENABLE KEYS */;

#
# Structure for table "chat_chat"
#

DROP TABLE IF EXISTS `chat_chat`;
CREATE TABLE `chat_chat` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '未命名聊天室' COMMENT '聊天室名称',
  `intro` varchar(500) DEFAULT '' COMMENT '聊天室简介',
  `pic` varchar(400) DEFAULT NULL COMMENT '聊天室图标',
  `status` int(1) unsigned DEFAULT '1' COMMENT '聊天室状态（0关闭，1开启）',
  `postTime` int(10) DEFAULT NULL COMMENT '最新消息时间',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='聊天室列表';

#
# Data for table "chat_chat"
#

/*!40000 ALTER TABLE `chat_chat` DISABLE KEYS */;
INSERT INTO `chat_chat` VALUES (1,'闲聊群','这里主要提供闲聊灌水，没有话题指向，所以可以尽情聊天。','5',1,1694531200),(2,'征文聊天室','本届征文','1',1,1694531207);
/*!40000 ALTER TABLE `chat_chat` ENABLE KEYS */;

#
# Structure for table "chat_configs"
#

DROP TABLE IF EXISTS `chat_configs`;
CREATE TABLE `chat_configs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `banText` text COMMENT '违禁词列表，英文逗号分割',
  `rootName` varchar(255) DEFAULT '0gsf' COMMENT '管理员名称',
  `logo` varchar(400) DEFAULT NULL COMMENT '管理员or站点logo',
  `website` varchar(400) DEFAULT NULL COMMENT '官方地址',
  `token` varchar(255) DEFAULT '0gsf2023vbnm' COMMENT '验证密钥',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='聊天室配置';

#
# Data for table "chat_configs"
#

/*!40000 ALTER TABLE `chat_configs` DISABLE KEYS */;
INSERT INTO `chat_configs` VALUES (1,'法轮,习近平,中共','0gsf',NULL,'https://www.0gsf.com/','0gsf2023vbnm');
/*!40000 ALTER TABLE `chat_configs` ENABLE KEYS */;

#
# Structure for table "chat_msg"
#

DROP TABLE IF EXISTS `chat_msg`;
CREATE TABLE `chat_msg` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT '匿名' COMMENT '用户名称',
  `ip` varchar(400) DEFAULT '' COMMENT 'IP地址',
  `type` int(1) unsigned DEFAULT '0' COMMENT '类型，0图文消息，1加入通知，2系统消息，3卡片消息',
  `text` text COMMENT '消息内容',
  `url` varchar(400) DEFAULT '' COMMENT '网址',
  `created` int(10) DEFAULT '0' COMMENT '创建时间',
  `chatid` int(11) DEFAULT '0' COMMENT '所属聊天室',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像（目前为编号）',
  `userKey` varchar(255) DEFAULT NULL COMMENT '用户唯一标识',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='消息列表';

#
# Data for table "chat_msg"
#

/*!40000 ALTER TABLE `chat_msg` DISABLE KEYS */;
INSERT INTO `chat_msg` VALUES (1,'伞兵一号卢本伟','127.0.0.1',0,'我觉得还可以吧，真的！','',1694513911,1,'15','zHf2e88bzP'),(2,'伞兵一号卢本伟','127.0.0.1',0,'先断一下','',1694513928,1,'15','zHf2e88bzP'),(3,'伞兵一号卢本伟','127.0.0.1',0,'你好','',1694513933,1,'15','zHf2e88bzP'),(4,'伞兵一号卢本伟','127.0.0.1',0,'这是\n一个，换行的\n神奇效果','',1694514474,1,'15','zHf2e88bzP'),(5,'伞兵一号卢本伟','127.0.0.1',0,'全体目光向我看齐','',1694515888,1,'15','zHf2e88bzP'),(6,'伞兵一号卢本伟','127.0.0.1',0,'我宣布个事','',1694515896,1,'15','zHf2e88bzP'),(7,'伞兵一号卢本伟','127.0.0.1',0,'可以吗？','',1694516550,1,'15','zHf2e88bzP'),(8,'伞兵一号卢本伟','127.0.0.1',0,'天空的尽头啊','',1694516560,1,'15','zHf2e88bzP'),(9,'伞兵一号卢本伟','127.0.0.1',0,'我觉得还行','',1694516802,1,'15','zHf2e88bzP'),(10,'伞兵一号卢本伟','127.0.0.1',0,'是这样吧','',1694516808,1,'15','zHf2e88bzP'),(11,'伞兵一号卢本伟','127.0.0.1',0,'是吗？','',1694519378,1,'15','zHf2e88bzP'),(12,'渣渣灰','127.0.0.1',0,'我觉得还行啊','',1694520601,1,'69','B34VZwWMe7'),(13,'渣渣灰','127.0.0.1',0,'没什么毛病','',1694520608,1,'69','B34VZwWMe7'),(14,'渣渣灰','127.0.0.1',3,'百度一下，你就知道','https://www.baidu.com/',1694520947,1,'69','B34VZwWMe7'),(15,'系统通知','127.0.0.1',2,'用户已被封禁','',1694523947,1,'3','zHf2e88bzP'),(16,'系统消息','127.0.0.1',2,'发送系统通知','',1694529306,1,'',NULL),(18,'渣渣灰','127.0.0.1',0,'我不明白','',1694530014,1,'69','B34VZwWMe7'),(19,'系统消息','127.0.0.1',2,'管理员封禁了IP[127.0.0.1]的聊天权限','',1694530395,1,'',NULL),(20,'渣渣灰','192.168.0.183',0,'这样可以吗？','',1694531200,1,'74','h3Y8sJzjtO'),(21,'渣渣灰','192.168.0.183',0,'好嘛','',1694531207,2,'74','h3Y8sJzjtO');
/*!40000 ALTER TABLE `chat_msg` ENABLE KEYS */;
