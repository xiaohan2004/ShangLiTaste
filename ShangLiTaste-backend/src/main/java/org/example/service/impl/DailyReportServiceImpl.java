package org.example.service.impl;

import org.example.mapper.DailyReportMapper;
import org.example.pojo.DailyReport;
import org.example.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Autowired
    private DailyReportMapper dailyReportMapper;

    @Override
    public List<DailyReport> getAllDailyReports() {
        return dailyReportMapper.getAllDailyReports();
    }

    @Override
    public DailyReport getDailyReportById(Integer id) {
        return dailyReportMapper.getDailyReportById(id);
    }

    @Override
    public DailyReport getDailyReportByDate(LocalDate date) {
        return dailyReportMapper.getDailyReportByDate(date);
    }

    @Override
    public void addDailyReport(DailyReport dailyReport) {
        dailyReportMapper.insertDailyReport(dailyReport);
    }

    @Override
    public void updateDailyReport(DailyReport dailyReport) {
        dailyReportMapper.updateDailyReport(dailyReport);
    }

    @Override
    public void deleteDailyReport(Integer id) {
        dailyReportMapper.deleteDailyReport(id);
    }
}

