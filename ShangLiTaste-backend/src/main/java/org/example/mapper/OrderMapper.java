package org.example.mapper;

import org.example.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("SELECT * FROM orders")
    List<Order> getAllOrders();

    @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
    Order getOrderById(Integer orderId);

    @Insert("INSERT INTO orders(table_id, order_time, status) VALUES(#{tableId}, #{orderTime}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    void insertOrder(Order order);

    void updateOrder(Order order);

    @Delete("DELETE FROM orders WHERE order_id = #{orderId}")
    void deleteOrder(Integer orderId);

    @Select("SELECT * FROM orders WHERE status = 0 AND table_id = (SELECT table_id FROM reservations WHERE customer_id = #{customerId} AND status = 1)")
    Order getActiveOrdersByCustomerId(Integer customerId);
}

