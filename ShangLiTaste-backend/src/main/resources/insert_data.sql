USE shangli_taste;
-- 插入菜品类别数据
INSERT INTO categories (category_name)
VALUES ('凉菜'),
       ('热菜'),
       ('主食'),
       ('汤类'),
       ('饮品'),
       ('小吃'),
       ('海鲜'),
       ('甜点');

-- 插入餐桌数据
INSERT INTO tables (status, location)
VALUES (0, 0), -- 空闲, 大厅
       (0, 1), -- 空闲, 包间
       (1, 0), -- 已预定, 大厅
       (2, 2), -- 占用, 私密房间
       (0, 0), -- 空闲, 大厅
       (1, 1), -- 已预定, 包间
       (2, 0), -- 占用, 大厅
       (0, 2);
-- 空闲, 私密房间

-- 插入菜品数据
INSERT INTO dishes (name, img, category_id, price, description, is_active)
VALUES ('凉拌黄瓜',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 1, 18.00, '清脆爽口的开胃凉菜', 1),
       ('麻婆豆腐',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 2, 28.00, '麻辣鲜香的川菜代表', 1),
       ('蛋炒饭',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 3, 22.00, '香喷喷的美味主食', 1),
       ('酸辣汤',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 4, 20.00, '开胃解腻的美味汤品', 1),
       ('柠檬茶',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 5, 15.00, '清爽解暑的饮品', 1),
       ('口水鸡',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 1, 38.00, '香辣可口的四川名菜', 1),
       ('宫保鸡丁',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 2, 32.00, '鲜香麻辣的经典菜式', 1),
       ('扬州炒饭',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 3, 25.00, '色香味俱全的炒饭', 1),
       ('西湖牛肉羹',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 4, 30.00, '浓郁鲜美的传统汤品', 1),
       ('龙井茶',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 5, 18.00, '清香淡雅的绿茶', 1),
       ('小笼包',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 6, 28.00, '鲜美多汁的江南小吃', 1),
       ('清蒸鲈鱼',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 7, 68.00, '鲜嫩可口的海鲜佳肴', 1),
       ('芒果布丁',"https://shanglixiaohan.oss-cn-shanghai.aliyuncs.com/shanglitaste/test.png", 8, 22.00, '香甜细腻的水果甜点', 1);

-- 插入用户数据
INSERT INTO users (username, password, role, status)
VALUES ('xiaohan', 'xiaohan', 0, 1),
       ('admin', 'hashed_password', 0, 1),
       ('waiter1', 'hashed_password', 1, 1),
       ('waiter2', 'hashed_password', 1, 1),
       ('finance1', 'hashed_password', 2, 1),
       ('manager1', 'hashed_password', 0, 1);

-- 插入客户数据
INSERT INTO customers (name, phone, email, address, registration_date, birthday, total_spent)
VALUES ('张三', '13800138000', 'zhangsan@example.com', '北京市朝阳区', '2023-01-01 10:00:00', '1990-05-15', 500.00),
       ('李四', '13900139000', 'lisi@example.com', '上海市浦东新区', '2023-02-01 11:00:00', '1985-08-20', 800.00),
       ('王五', '13700137000', 'wangwu@example.com', '广州市天河区', '2023-03-01 09:00:00', '1988-12-10', 650.00),
       ('赵六', '13600136000', 'zhaoliu@example.com', '深圳市南山区', '2023-04-01 14:00:00', '1992-03-25', 450.00),
       ('钱七', '13500135000', 'qianqi@example.com', '杭州市西湖区', '2023-05-01 16:00:00', '1995-07-30', 300.00);

-- 插入订单数据
INSERT INTO orders (table_id, order_time, status)
VALUES (1, '2023-05-20 12:30:00', 0),
       (2, '2023-05-20 13:00:00', 1),
       (3, '2023-05-20 13:30:00', 2),
       (4, '2023-05-20 18:00:00', 0),
       (5, '2023-05-20 19:00:00', 1),
       (6, '2023-05-21 12:00:00', 2),
       (7, '2023-05-21 13:30:00', 0);

-- 插入订单明细数据
INSERT INTO order_items (order_id, dish_id, quantity, price, total_price)
VALUES (1, 1, 1, 18.00, 18.00),
       (1, 2, 1, 28.00, 28.00),
       (2, 3, 2, 22.00, 44.00),
       (2, 4, 1, 20.00, 20.00),
       (3, 5, 3, 15.00, 45.00),
       (3, 6, 1, 38.00, 38.00),
       (4, 7, 1, 32.00, 32.00),
       (4, 8, 2, 25.00, 50.00),
       (5, 9, 1, 30.00, 30.00),
       (5, 10, 2, 18.00, 36.00),
       (6, 11, 3, 28.00, 84.00),
       (6, 12, 1, 68.00, 68.00),
       (7, 13, 2, 22.00, 44.00),
       (7, 1, 1, 18.00, 18.00);

-- 插入账单数据
INSERT INTO bills (order_id, total_amount, paid_amount, change_amount, payment_status, payment_method)
VALUES (1, 46.00, 50.00, 4.00, 1, 0),
       (2, 64.00, 64.00, 0.00, 1, 1),
       (3, 83.00, 83.00, 0.00, 1, 2),
       (4, 82.00, 90.00, 8.00, 1, 0),
       (5, 66.00, 66.00, 0.00, 1, 1),
       (6, 152.00, 152.00, 0.00, 1, 2),
       (7, 62.00, 62.00, 0.00, 1, 1);

-- 插入预定数据
INSERT INTO reservations (customer_id, table_id, reservation_time, status, special_requests)
VALUES (1, 2, '2023-05-21 18:00:00', 1, '需要一个靠窗的位置'),
       (2, 3, '2023-05-22 19:00:00', 0, '需要准备一个生日蛋糕'),
       (3, 4, '2023-05-23 12:30:00', 1, '需要儿童座椅'),
       (4, 5, '2023-05-24 20:00:00', 0, '希望有一个安静的位置'),
       (5, 6, '2023-05-25 18:30:00', 1, '需要准备素食菜单');

-- 插入消费历史数据
INSERT INTO purchase_history (customer_id, order_id, purchase_date, total_amount, payment_method)
VALUES (1, 1, '2023-05-20 12:30:00', 46.00, 0),
       (2, 2, '2023-05-20 13:00:00', 64.00, 1),
       (3, 3, '2023-05-20 13:30:00', 83.00, 2),
       (4, 4, '2023-05-20 18:00:00', 82.00, 0),
       (5, 5, '2023-05-20 19:00:00', 66.00, 1);

-- 插入日结账数据
INSERT INTO daily_reports (date, total_sales, total_orders, total_bills)
VALUES ('2023-05-20', 341.00, 5, 5),
       ('2023-05-21', 214.00, 2, 2);

-- 插入月结账数据
INSERT INTO monthly_reports (month, year, total_sales, total_orders, total_bills)
VALUES (5, 2023, 3500.00, 80, 75),
       (4, 2023, 3200.00, 75, 70);

-- 插入年结账数据
INSERT INTO annual_reports (year, total_sales, total_orders, total_bills)
VALUES (2023, 42000.00, 960, 900),
       (2022, 38000.00, 920, 880);