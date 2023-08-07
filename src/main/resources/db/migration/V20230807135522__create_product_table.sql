CREATE TABLE IF NOT EXISTS `product`
(
    `id`                   INT            NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                 VARCHAR(32)    NOT NULL                COMMENT '商品名称',
    `price`                DECIMAL(19,2)                          COMMENT '价格',
    `status`               VARCHAR(32)    NOT NULL                COMMENT '状态',
PRIMARY KEY(`id`)
USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
