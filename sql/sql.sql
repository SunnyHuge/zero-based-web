
-- 客户表
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`contact` varchar(255) DEFAULT NULL,
`telephone` varchar(255) DEFAULT NULL,
`email` varchar(255) DEFAULT NULL,
`remark` text DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据
INSERT INTO `customer` VALUES (1, '客户1', 'Jack', '13512345678', 'jack@gmail.com', NULL);
INSERT INTO `customer` VALUES (2, '客户2', 'Rose', '13612345678', 'rose@gmail.com', NULL);