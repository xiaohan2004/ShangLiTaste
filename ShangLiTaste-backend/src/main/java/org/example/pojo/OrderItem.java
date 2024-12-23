package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Integer orderItemId;
    private Integer orderId;
    private Integer dishId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

