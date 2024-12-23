package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualReport {
    private Integer reportId;
    private Integer year;
    private BigDecimal totalSales;
    private Integer totalOrders;
    private Integer totalBills;
}

