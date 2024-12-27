package org.example.service.impl;

import org.example.mapper.OrderMapper;
import org.example.mapper.ReservationMapper;
import org.example.mapper.TableMapper;
import org.example.pojo.Order;
import org.example.pojo.Table;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderMapper.getOrderById(id);
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        orderMapper.insertOrder(order);
        reservationMapper.updateReservationf0t1(order.getTableId());
        tableMapper.updateTable(new Table(order.getTableId(), (short) 2, null));
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
        tableMapper.updateTable(new Table(order.getTableId(), (short) 0, null));
    }

    @Override
    public void deleteOrder(Integer id) {
        orderMapper.deleteOrder(id);
    }

    @Override
    public Order getActiveOrdersByCustomerId(Integer customerId) {
        return orderMapper.getActiveOrdersByCustomerId(customerId);
    }
}

