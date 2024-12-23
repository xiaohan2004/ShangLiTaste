package org.example.controller;

import org.example.pojo.DailyReport;
import org.example.pojo.Result;
import org.example.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    @GetMapping
    public Result getAllDailyReports() {
        return Result.success(dailyReportService.getAllDailyReports());
    }

    @GetMapping("/{id}")
    public Result getDailyReportById(@PathVariable Integer id) {
        DailyReport dailyReport = dailyReportService.getDailyReportById(id);
        if (dailyReport != null) {
            return Result.success(dailyReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @GetMapping("/date/{date}")
    public Result getDailyReportByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DailyReport dailyReport = dailyReportService.getDailyReportByDate(date);
        if (dailyReport != null) {
            return Result.success(dailyReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createDailyReport(@RequestBody DailyReport dailyReport) {
        dailyReportService.addDailyReport(dailyReport);
        return Result.success(dailyReport);
    }

    @PutMapping("/{id}")
    public Result updateDailyReport(@PathVariable Integer id, @RequestBody DailyReport dailyReport) {
        DailyReport existingDailyReport = dailyReportService.getDailyReportById(id);
        if (existingDailyReport != null) {
            dailyReport.setReportId(id);
            dailyReportService.updateDailyReport(dailyReport);
            return Result.success(dailyReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteDailyReport(@PathVariable Integer id) {
        DailyReport existingDailyReport = dailyReportService.getDailyReportById(id);
        if (existingDailyReport != null) {
            dailyReportService.deleteDailyReport(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

