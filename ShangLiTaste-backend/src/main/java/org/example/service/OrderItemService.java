package org.example.service;

import org.example.pojo.OrderItem;
import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    OrderItem getOrderItemById(Integer id);
    void addOrderItem(OrderItem orderItem);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(Integer id);
}

