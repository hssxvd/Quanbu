-- 订单评价表
CREATE TABLE IF NOT EXISTS tb_order_comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评价ID',
    purchase_id INT NOT NULL COMMENT '订单ID',
    order_id INT NOT NULL COMMENT '商品ID',
    buyer_name VARCHAR(50) NOT NULL COMMENT '买家用户名',
    seller_name VARCHAR(50) NOT NULL COMMENT '卖家用户名',
    rating INT NOT NULL COMMENT '评分(1-5)',
    content VARCHAR(500) NOT NULL COMMENT '评价内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_purchase_id (purchase_id),
    INDEX idx_order_id (order_id),
    INDEX idx_seller_name (seller_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单评价表';