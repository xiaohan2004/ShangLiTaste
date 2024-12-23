package org.example.mapper;

import org.example.pojo.AnnualReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnualReportMapper {
    @Select("SELECT * FROM annual_reports")
    List<AnnualReport> getAllAnnualReports();

    @Select("SELECT * FROM annual_reports WHERE report_id = #{reportId}")
    AnnualReport getAnnualReportById(Integer reportId);

    @Select("SELECT * FROM annual_reports WHERE year = #{year}")
    AnnualReport getAnnualReportByYear(Integer year);

    @Insert("INSERT INTO annual_reports(year, total_sales, total_orders, total_bills) VALUES(#{year}, #{totalSales}, #{totalOrders}, #{totalBills})")
    @Options(useGeneratedKeys = true, keyProperty = "reportId")
    void insertAnnualReport(AnnualReport annualReport);

    @Update("UPDATE annual_reports SET year = #{year}, total_sales = #{totalSales}, total_orders = #{totalOrders}, total_bills = #{totalBills} WHERE report_id = #{reportId}")
    void updateAnnualReport(AnnualReport annualReport);

    @Delete("DELETE FROM annual_reports WHERE report_id = #{reportId}")
    void deleteAnnualReport(Integer reportId);
}

