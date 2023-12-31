CREATE TABLE IF NOT EXISTS `customer`
(
  `id`                   VARCHAR(64)    NOT NULL COMMENT '主键',
  `name`                 VARCHAR(32)    NOT NULL COMMENT '客户名称',
  `create_time`          TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`          TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY(`id`)
USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

INSERT INTO customer values ('1','第一个客户','2022-12-11 21:31:14','2022-12-11 21:31:14');