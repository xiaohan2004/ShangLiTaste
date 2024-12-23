package org.example.service;

import org.example.pojo.DailyReport;
import java.time.LocalDate;
import java.util.List;

public interface DailyReportService {
    List<DailyReport> getAllDailyReports();
    DailyReport getDailyReportById(Integer id);
    DailyReport getDailyReportByDate(LocalDate date);
    void addDailyReport(DailyReport dailyReport);
    void updateDailyReport(DailyReport dailyReport);
    void deleteDailyReport(Integer id);
}

