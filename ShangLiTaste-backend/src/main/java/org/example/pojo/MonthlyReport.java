package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {
    private Integer reportId;
    private Integer month;
    private Integer year;
    private BigDecimal totalSales;
    private Integer totalOrders;
    private Integer totalBills;
}

