package org.example.service;

import org.example.pojo.MonthlyReport;
import java.util.List;

public interface MonthlyReportService {
    List<MonthlyReport> getAllMonthlyReports();
    MonthlyReport getMonthlyReportById(Integer id);
    MonthlyReport getMonthlyReportByMonthAndYear(Integer month, Integer year);
    void addMonthlyReport(MonthlyReport monthlyReport);
    void updateMonthlyReport(MonthlyReport monthlyReport);
    void deleteMonthlyReport(Integer id);
}

