package org.example.service.impl;

import org.example.mapper.MonthlyReportMapper;
import org.example.pojo.MonthlyReport;
import org.example.service.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthlyReportServiceImpl implements MonthlyReportService {

    @Autowired
    private MonthlyReportMapper monthlyReportMapper;

    @Override
    public List<MonthlyReport> getAllMonthlyReports() {
        return monthlyReportMapper.getAllMonthlyReports();
    }

    @Override
    public MonthlyReport getMonthlyReportById(Integer id) {
        return monthlyReportMapper.getMonthlyReportById(id);
    }

    @Override
    public MonthlyReport getMonthlyReportByMonthAndYear(Integer month, Integer year) {
        return monthlyReportMapper.getMonthlyReportByMonthAndYear(month, year);
    }

    @Override
    public void addMonthlyReport(MonthlyReport monthlyReport) {
        monthlyReportMapper.insertMonthlyReport(monthlyReport);
    }

    @Override
    public void updateMonthlyReport(MonthlyReport monthlyReport) {
        monthlyReportMapper.updateMonthlyReport(monthlyReport);
    }

    @Override
    public void deleteMonthlyReport(Integer id) {
        monthlyReportMapper.deleteMonthlyReport(id);
    }
}

