package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {
    private Integer reportId;
    private LocalDate date;
    private BigDecimal totalSales;
    private Integer totalOrders;
    private Integer totalBills;
}

