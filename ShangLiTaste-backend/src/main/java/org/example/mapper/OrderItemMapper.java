package org.example.mapper;

import org.example.pojo.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Select("SELECT * FROM order_items")
    List<OrderItem> getAllOrderItems();

    @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    @Select("SELECT * FROM order_items WHERE order_item_id = #{orderItemId}")
    OrderItem getOrderItemById(Integer orderItemId);

    @Insert("INSERT INTO order_items(order_id, dish_id, quantity, price, total_price) VALUES(#{orderId}, #{dishId}, #{quantity}, #{price}, #{totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId")
    void insertOrderItem(OrderItem orderItem);

    @Update("UPDATE order_items SET order_id = #{orderId}, dish_id = #{dishId}, quantity = #{quantity}, price = #{price}, total_price = #{totalPrice} WHERE order_item_id = #{orderItemId}")
    void updateOrderItem(OrderItem orderItem);

    @Delete("DELETE FROM order_items WHERE order_item_id = #{orderItemId}")
    void deleteOrderItem(Integer orderItemId);
}

