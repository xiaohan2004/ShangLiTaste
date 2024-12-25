-- 1. 检查并删除已存在的数据库
DROP DATABASE IF EXISTS shangli_taste;

-- 2. 创建数据库
CREATE DATABASE shangli_taste;

-- 3. 使用数据库
USE shangli_taste;

-- 4. 创建菜品类别表 (categories)
CREATE TABLE categories
(
    category_id   INT PRIMARY KEY AUTO_INCREMENT COMMENT '菜品类别编号，主键，自增',
    category_name VARCHAR(100) COMMENT '菜品类别名称'
);

-- 5. 创建餐桌表 (tables)
CREATE TABLE tables
(
    table_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '餐桌编号，主键，自增',
    status   TINYINT UNSIGNED COMMENT '餐桌状态（0: 空闲, 1: 已预定, 2: 占用）',
    location TINYINT UNSIGNED COMMENT '餐桌位置（0: 大厅, 1: 包间, 2: 私密房间）'
);

-- 6. 创建菜品表 (dishes)
CREATE TABLE dishes
(
    dish_id     INT PRIMARY KEY AUTO_INCREMENT COMMENT '菜品编号，主键，自增',
    name        VARCHAR(255) COMMENT '菜品名称',
    img         VARCHAR(255) COMMENT '菜品图片',
    category_id INT COMMENT '菜品类别编号，外键，引用categories表',
    price       DECIMAL(10, 2) COMMENT '菜品价格',
    description TEXT COMMENT '菜品描述',
    is_active   TINYINT UNSIGNED COMMENT '是否启用（0: 禁用, 1: 启用）',
    FOREIGN KEY (category_id) REFERENCES categories (category_id) -- 外键，关联菜品类别表
);

-- 7. 创建订单表 (orders)
CREATE TABLE orders
(
    order_id   INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单编号，主键，自增',
    table_id   INT COMMENT '餐桌编号，外键，引用tables表',
    order_time DATETIME COMMENT '下单时间',
    status     TINYINT UNSIGNED COMMENT '订单状态（0: 进行中, 1: 已完成, 2: 已结账）',
    FOREIGN KEY (table_id) REFERENCES tables (table_id) -- 外键，关联餐桌表
);

-- 8. 创建订单明细表 (order_items)
CREATE TABLE order_items
(
    order_item_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单明细编号，主键，自增',
    order_id      INT COMMENT '订单编号，外键，引用orders表',
    dish_id       INT COMMENT '菜品编号，外键，引用dishes表',
    quantity      INT COMMENT '菜品数量',
    price         DECIMAL(10, 2) COMMENT '菜品单价',
    total_price   DECIMAL(10, 2) COMMENT '菜品总价',
    FOREIGN KEY (order_id) REFERENCES orders (order_id), -- 外键，关联订单表
    FOREIGN KEY (dish_id) REFERENCES dishes (dish_id)    -- 外键，关联菜品表
);

-- 9. 创建账单表 (bills)
CREATE TABLE bills
(
    bill_id        INT PRIMARY KEY AUTO_INCREMENT COMMENT '账单编号，主键，自增',
    order_id       INT COMMENT '订单编号，外键，引用orders表',
    total_amount   DECIMAL(10, 2) COMMENT '总金额',
    paid_amount    DECIMAL(10, 2) COMMENT '已付金额',
    change_amount  DECIMAL(10, 2) COMMENT '找零金额',
    payment_status TINYINT UNSIGNED COMMENT '支付状态（0: 待支付, 1: 已支付）',
    payment_method TINYINT UNSIGNED COMMENT '支付方式（0: 现金, 1: 信用卡, 2: 微信支付）',
    FOREIGN KEY (order_id) REFERENCES orders (order_id) -- 外键，关联订单表
);

-- 10. 创建用户表 (users)
CREATE TABLE users
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户编号，主键，自增',
    username VARCHAR(100) UNIQUE COMMENT '用户名，唯一',
    password VARCHAR(255) COMMENT '密码，存储加密形式',
    role     TINYINT UNSIGNED COMMENT '用户角色（0: 管理员, 1: 服务员, 2: 财务）',
    status   TINYINT UNSIGNED COMMENT '用户状态（0: 禁用, 1: 启用）'
);

-- 11. 创建日结账表 (daily_reports)
CREATE TABLE daily_reports
(
    report_id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '报表编号，主键，自增',
    date         DATE COMMENT '日期',
    total_sales  DECIMAL(10, 2) COMMENT '总销售额',
    total_orders INT COMMENT '总订单数',
    total_bills  INT COMMENT '总账单数'
);

-- 12. 创建月结账表 (monthly_reports)
CREATE TABLE monthly_reports
(
    report_id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '报表编号，主键，自增',
    month        INT COMMENT '月份',
    year         INT COMMENT '年份',
    total_sales  DECIMAL(10, 2) COMMENT '总销售额',
    total_orders INT COMMENT '总订单数',
    total_bills  INT COMMENT '总账单数'
);

-- 13. 创建年结账表 (annual_reports)
CREATE TABLE annual_reports
(
    report_id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '报表编号，主键，自增',
    year         INT COMMENT '年份',
    total_sales  DECIMAL(10, 2) COMMENT '总销售额',
    total_orders INT COMMENT '总订单数',
    total_bills  INT COMMENT '总账单数'
);

-- 14. 创建客户表 (customers)
CREATE TABLE customers
(
    customer_id       INT PRIMARY KEY AUTO_INCREMENT COMMENT '客户编号，主键，自增',
    name              VARCHAR(100) COMMENT '客户用户名',
    password          VARCHAR(20) COMMENT '客户密码',
    phone             VARCHAR(15) COMMENT '客户电话',
    email             VARCHAR(255) COMMENT '客户电子邮件',
    address           VARCHAR(255) COMMENT '客户地址',
    registration_date DATETIME COMMENT '注册日期',
    birthday          DATE COMMENT '客户生日',
    total_spent       DECIMAL(10, 2) COMMENT '累计消费金额'
);

-- 15. 创建预定表 (reservations)
CREATE TABLE reservations
(
    reservation_id   INT PRIMARY KEY AUTO_INCREMENT COMMENT '预定编号，主键，自增',
    customer_id      INT COMMENT '客户编号，外键，引用customers表',
    table_id         INT COMMENT '餐桌编号，外键，引用tables表',
    reservation_time DATETIME COMMENT '预定时间',
    status           TINYINT UNSIGNED COMMENT '预定状态（0: 待确认, 1: 已确认, 2: 已取消）',
    special_requests TEXT COMMENT '特殊需求',
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id), -- 外键，关联客户表
    FOREIGN KEY (table_id) REFERENCES tables (table_id)           -- 外键，关联餐桌表
);

-- 16. 创建消费历史表 (purchase_history)
CREATE TABLE purchase_history
(
    purchase_id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '消费记录编号，主键，自增',
    customer_id    INT COMMENT '客户编号，外键，引用customers表',
    order_id       INT COMMENT '订单编号，外键，引用orders表',
    purchase_date  DATETIME COMMENT '消费日期',
    total_amount   DECIMAL(10, 2) COMMENT '总金额',
    payment_method TINYINT UNSIGNED COMMENT '支付方式（0: 现金, 1: 信用卡, 2: 微信支付）',
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id), -- 外键，关联客户表
    FOREIGN KEY (order_id) REFERENCES orders (order_id)           -- 外键，关联订单表
);

CREATE TABLE operate_log
(
    id            INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，自增主键',
    operate_user  VARCHAR(255) COMMENT '操作人，关联到用户表',
    operate_time  DATETIME COMMENT '操作时间，记录操作发生的具体时间',
    class_name    VARCHAR(255) COMMENT '操作类名，记录被操作的类名',
    method_name   VARCHAR(255) COMMENT '操作方法名，记录被调用的方法名',
    method_params TEXT COMMENT '操作方法参数，JSON格式存储方法的参数',
    return_value  TEXT COMMENT '操作方法返回值，JSON格式存储方法的返回值',
    cost_time     BIGINT COMMENT '操作耗时，单位为毫秒'
) COMMENT '操作日志表';
