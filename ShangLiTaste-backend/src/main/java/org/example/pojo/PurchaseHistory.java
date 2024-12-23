package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {
    private Integer purchaseId;
    private Integer customerId;
    private Integer orderId;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    private Short paymentMethod;
}

