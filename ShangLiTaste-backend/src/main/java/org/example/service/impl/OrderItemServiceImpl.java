package org.example.service.impl;

import org.example.mapper.OrderItemMapper;
import org.example.pojo.OrderItem;
import org.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemMapper.getAllOrderItems();
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        return orderItemMapper.getOrderItemsByOrderId(orderId);
    }

    @Override
    public OrderItem getOrderItemById(Integer id) {
        return orderItemMapper.getOrderItemById(id);
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemMapper.insertOrderItem(orderItem);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        orderItemMapper.updateOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer id) {
        orderItemMapper.deleteOrderItem(id);
    }
}

