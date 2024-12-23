package org.example.controller;

import org.example.pojo.MonthlyReport;
import org.example.pojo.Result;
import org.example.service.MonthlyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monthly-reports")
public class MonthlyReportController {

    @Autowired
    private MonthlyReportService monthlyReportService;

    @GetMapping
    public Result getAllMonthlyReports() {
        return Result.success(monthlyReportService.getAllMonthlyReports());
    }

    @GetMapping("/{id}")
    public Result getMonthlyReportById(@PathVariable Integer id) {
        MonthlyReport monthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (monthlyReport != null) {
            return Result.success(monthlyReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @GetMapping("/month/{month}/year/{year}")
    public Result getMonthlyReportByMonthAndYear(@PathVariable Integer month, @PathVariable Integer year) {
        MonthlyReport monthlyReport = monthlyReportService.getMonthlyReportByMonthAndYear(month, year);
        if (monthlyReport != null) {
            return Result.success(monthlyReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createMonthlyReport(@RequestBody MonthlyReport monthlyReport) {
        monthlyReportService.addMonthlyReport(monthlyReport);
        return Result.success(monthlyReport);
    }

    @PutMapping("/{id}")
    public Result updateMonthlyReport(@PathVariable Integer id, @RequestBody MonthlyReport monthlyReport) {
        MonthlyReport existingMonthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (existingMonthlyReport != null) {
            monthlyReport.setReportId(id);
            monthlyReportService.updateMonthlyReport(monthlyReport);
            return Result.success(monthlyReport);
        } else {
            return Result.success(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteMonthlyReport(@PathVariable Integer id) {
        MonthlyReport existingMonthlyReport = monthlyReportService.getMonthlyReportById(id);
        if (existingMonthlyReport != null) {
            monthlyReportService.deleteMonthlyReport(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

