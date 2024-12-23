package org.example.service;

import org.example.pojo.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    void addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Integer id);
}

