-- 商品规格表
CREATE TABLE IF NOT EXISTS tb_order_spec (
    spec_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '规格ID',
    order_id INT NOT NULL COMMENT '商品ID',
    spec_name VARCHAR(100) NOT NULL COMMENT '规格名称（如：3斤装、5斤装）',
    spec_price DECIMAL(10,2) NOT NULL COMMENT '规格价格',
    spec_stock INT NOT NULL DEFAULT 0 COMMENT '规格库存',
    spec_sales INT NOT NULL DEFAULT 0 COMMENT '已售数量',
    spec_sort INT NOT NULL DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';

-- 为已有商品添加默认规格
INSERT INTO tb_order_spec (order_id, spec_name, spec_price, spec_stock, spec_sales, spec_sort)
SELECT order_id, '默认规格', price, 999, 0, 0
FROM tb_order
WHERE order_id NOT IN (SELECT order_id FROM tb_order_spec WHERE order_id IS NOT NULL)
AND type = 'goods';