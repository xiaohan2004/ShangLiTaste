package org.example.mapper;

import org.example.pojo.MonthlyReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MonthlyReportMapper {
    @Select("SELECT * FROM monthly_reports")
    List<MonthlyReport> getAllMonthlyReports();

    @Select("SELECT * FROM monthly_reports WHERE report_id = #{reportId}")
    MonthlyReport getMonthlyReportById(Integer reportId);

    @Select("SELECT * FROM monthly_reports WHERE month = #{month} AND year = #{year}")
    MonthlyReport getMonthlyReportByMonthAndYear(Integer month, Integer year);

    @Insert("INSERT INTO monthly_reports(month, year, total_sales, total_orders, total_bills) VALUES(#{month}, #{year}, #{totalSales}, #{totalOrders}, #{totalBills})")
    @Options(useGeneratedKeys = true, keyProperty = "reportId")
    void insertMonthlyReport(MonthlyReport monthlyReport);

    @Update("UPDATE monthly_reports SET month = #{month}, year = #{year}, total_sales = #{totalSales}, total_orders = #{totalOrders}, total_bills = #{totalBills} WHERE report_id = #{reportId}")
    void updateMonthlyReport(MonthlyReport monthlyReport);

    @Delete("DELETE FROM monthly_reports WHERE report_id = #{reportId}")
    void deleteMonthlyReport(Integer reportId);
}

