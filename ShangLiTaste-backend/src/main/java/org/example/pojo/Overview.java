package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Overview {
    private Integer totalDishes;
    private Integer activeDishes;
    private Integer totalCategories;
    private Integer totalOrders;
    private Integer ongoingOrders;
    private Integer completedOrders;
    private Integer paidOrders;
    private Integer totalBills;
    private BigDecimal totalRevenue;
    private BigDecimal averageBillAmount;
    private Integer totalUsers;
    private Integer activeUsers;
    private Integer totalCustomers;
    private BigDecimal averageCustomerSpent;
    private Integer totalTables;
    private Integer availableTables;
    private Integer reservedTables;
    private Integer occupiedTables;
    private Integer totalReservations;
    private Integer confirmedReservations;
    private Integer todayOrders;
    private BigDecimal todayRevenue;
    private Integer thisMonthOrders;
    private BigDecimal thisMonthRevenue;
    private BigDecimal averageOperationTime;
}
