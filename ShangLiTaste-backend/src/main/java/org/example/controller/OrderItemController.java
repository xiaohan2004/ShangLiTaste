package org.example.controller;

import org.example.pojo.OrderItem;
import org.example.pojo.Result;
import org.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public Result getAllOrderItems() {
        return Result.success(orderItemService.getAllOrderItems());
    }

    @GetMapping("/order/{orderId}")
    public Result getOrderItemsByOrderId(@PathVariable Integer orderId) {
        return Result.success(orderItemService.getOrderItemsByOrderId(orderId));
    }

    @GetMapping("/{id}")
    public Result getOrderItemById(@PathVariable Integer id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        if (orderItem != null) {
            return Result.success(orderItem);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createOrderItem(@RequestBody OrderItem orderItem) {
        orderItemService.addOrderItem(orderItem);
        return Result.success(orderItem);
    }

    @PutMapping("/{id}")
    public Result updateOrderItem(@PathVariable Integer id, @RequestBody OrderItem orderItem) {
        OrderItem existingOrderItem = orderItemService.getOrderItemById(id);
        if (existingOrderItem != null) {
            orderItem.setOrderItemId(id);
            orderItemService.updateOrderItem(orderItem);
            return Result.success(orderItem);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteOrderItem(@PathVariable Integer id) {
        OrderItem existingOrderItem = orderItemService.getOrderItemById(id);
        if (existingOrderItem != null) {
            orderItemService.deleteOrderItem(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

