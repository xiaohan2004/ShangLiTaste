package org.example.mapper;

import org.example.pojo.DailyReport;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyReportMapper {
    @Select("SELECT * FROM daily_reports")
    List<DailyReport> getAllDailyReports();

    @Select("SELECT * FROM daily_reports WHERE report_id = #{reportId}")
    DailyReport getDailyReportById(Integer reportId);

    @Select("SELECT * FROM daily_reports WHERE date = #{date}")
    DailyReport getDailyReportByDate(LocalDate date);

    @Insert("INSERT INTO daily_reports(date, total_sales, total_orders, total_bills) VALUES(#{date}, #{totalSales}, #{totalOrders}, #{totalBills})")
    @Options(useGeneratedKeys = true, keyProperty = "reportId")
    void insertDailyReport(DailyReport dailyReport);

    @Update("UPDATE daily_reports SET date = #{date}, total_sales = #{totalSales}, total_orders = #{totalOrders}, total_bills = #{totalBills} WHERE report_id = #{reportId}")
    void updateDailyReport(DailyReport dailyReport);

    @Delete("DELETE FROM daily_reports WHERE report_id = #{reportId}")
    void deleteDailyReport(Integer reportId);
}

