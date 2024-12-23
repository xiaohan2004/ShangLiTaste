package org.example.controller;

import org.example.pojo.Order;
import org.example.pojo.Result;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result getAllOrders() {
        return Result.success(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public Result getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        return Result.success(order);
    }

    @PutMapping("/{id}")
    public Result updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            order.setOrderId(id);
            orderService.updateOrder(order);
            return Result.success(order);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteOrder(@PathVariable Integer id) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            orderService.deleteOrder(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

