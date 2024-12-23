package org.example.controller;

import org.example.pojo.AnnualReport;
import org.example.pojo.Result;
import org.example.service.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/annual-reports")
public class AnnualReportController {

    @Autowired
    private AnnualReportService annualReportService;

    @GetMapping
    public Result getAllAnnualReports() {
        return Result.success(annualReportService.getAllAnnualReports());
    }

    @GetMapping("/{id}")
    public Result getAnnualReportById(@PathVariable Integer id) {
        AnnualReport annualReport = annualReportService.getAnnualReportById(id);
        if (annualReport != null) {
            return Result.success(annualReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @GetMapping("/year/{year}")
    public Result getAnnualReportByYear(@PathVariable Integer year) {
        AnnualReport annualReport = annualReportService.getAnnualReportByYear(year);
        if (annualReport != null) {
            return Result.success(annualReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createAnnualReport(@RequestBody AnnualReport annualReport) {
        annualReportService.addAnnualReport(annualReport);
        return Result.success(annualReport);
    }

    @PutMapping("/{id}")
    public Result updateAnnualReport(@PathVariable Integer id, @RequestBody AnnualReport annualReport) {
        AnnualReport existingAnnualReport = annualReportService.getAnnualReportById(id);
        if (existingAnnualReport != null) {
            annualReport.setReportId(id);
            annualReportService.updateAnnualReport(annualReport);
            return Result.success(annualReport);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteAnnualReport(@PathVariable Integer id) {
        AnnualReport existingAnnualReport = annualReportService.getAnnualReportById(id);
        if (existingAnnualReport != null) {
            annualReportService.deleteAnnualReport(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

