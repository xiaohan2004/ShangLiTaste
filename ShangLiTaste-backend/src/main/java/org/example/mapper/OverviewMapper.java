package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.Overview;

@Mapper
public interface OverviewMapper {
    @Select({
            "SELECT"+
            "  (SELECT COUNT(*) FROM dishes) AS totalDishes,"+
            "  (SELECT COUNT(*) FROM dishes WHERE is_active = 1) AS activeDishes,"+
            "  (SELECT COUNT(*) FROM categories) AS totalCategories,"+
            "  (SELECT COUNT(*) FROM orders) AS totalOrders,"+
            "  (SELECT COUNT(*) FROM orders WHERE status = 0) AS ongoingOrders,"+
            "  (SELECT COUNT(*) FROM orders WHERE status = 1) AS completedOrders,"+
            "  (SELECT COUNT(*) FROM orders WHERE status = 2) AS paidOrders,"+
            "  (SELECT COUNT(*) FROM bills) AS totalBills,"+
            "  (SELECT COALESCE(SUM(total_amount), 0) FROM bills) AS totalRevenue,"+
            "  (SELECT COALESCE(AVG(total_amount), 0) FROM bills) AS averageBillAmount,"+
            "  (SELECT COUNT(*) FROM users) AS totalUsers,"+
            "  (SELECT COUNT(*) FROM users WHERE status = 1) AS activeUsers,"+
            "  (SELECT COUNT(*) FROM customers) AS totalCustomers,"+
            "  (SELECT COALESCE(AVG(total_spent), 0) FROM customers) AS averageCustomerSpent,"+
            "  (SELECT COUNT(*) FROM tables) AS totalTables,"+
            "  (SELECT COUNT(*) FROM tables WHERE status = 0) AS availableTables,"+
            "  (SELECT COUNT(*) FROM tables WHERE status = 1) AS reservedTables,"+
            "  (SELECT COUNT(*) FROM tables WHERE status = 2) AS occupiedTables,"+
            "  (SELECT COUNT(*) FROM reservations) AS totalReservations,"+
            "  (SELECT COUNT(*) FROM reservations WHERE status = 1) AS confirmedReservations,"+
            "  (SELECT COUNT(*) FROM orders WHERE DATE(order_time) = CURDATE()) AS todayOrders,"+
            "  (SELECT COALESCE(SUM(b.total_amount), 0) FROM bills b"+
            "   JOIN orders o ON b.order_id = o.order_id"+
            "   WHERE DATE(o.order_time) = CURDATE()) AS todayRevenue,"+
            "  (SELECT COUNT(*) FROM orders"+
            "   WHERE MONTH(order_time) = MONTH(CURDATE()) AND YEAR(order_time) = YEAR(CURDATE())) AS thisMonthOrders,"+
            "  (SELECT COALESCE(SUM(b.total_amount), 0) FROM bills b"+
            "   JOIN orders o ON b.order_id = o.order_id"+
            "   WHERE MONTH(o.order_time) = MONTH(CURDATE()) AND YEAR(o.order_time) = YEAR(CURDATE())) AS thisMonthRevenue,"+
            "  (SELECT COALESCE(AVG(cost_time), 0) FROM operate_log) AS averageOperationTime"
    })
    Overview getOverview();
}
