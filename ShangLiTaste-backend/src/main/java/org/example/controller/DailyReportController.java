package org.example.controller;

import org.example.pojo.DailyReport;
import org.example.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    @GetMapping
    public ResponseEntity<List<DailyReport>> getAllDailyReports() {
        return new ResponseEntity<>(dailyReportService.getAllDailyReports(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyReport> getDailyReportById(@PathVariable Integer id) {
        DailyReport dailyReport = dailyReportService.getDailyReportById(id);
        if (dailyReport != null) {
            return new ResponseEntity<>(dailyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<DailyReport> getDailyReportByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DailyReport dailyReport = dailyReportService.getDailyReportByDate(date);
        if (dailyReport != null) {
            return new ResponseEntity<>(dailyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DailyReport> createDailyReport(@RequestBody DailyReport dailyReport) {
        dailyReportService.addDailyReport(dailyReport);
        return new ResponseEntity<>(dailyReport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyReport> updateDailyReport(@PathVariable Integer id, @RequestBody DailyReport dailyReport) {
        DailyReport existingDailyReport = dailyReportService.getDailyReportById(id);
        if (existingDailyReport != null) {
            dailyReport.setReportId(id);
            dailyReportService.updateDailyReport(dailyReport);
            return new ResponseEntity<>(dailyReport, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyReport(@PathVariable Integer id) {
        DailyReport existingDailyReport = dailyReportService.getDailyReportById(id);
        if (existingDailyReport != null) {
            dailyReportService.deleteDailyReport(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

