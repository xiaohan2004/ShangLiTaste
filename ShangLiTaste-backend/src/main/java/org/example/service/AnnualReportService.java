package org.example.service;

import org.example.pojo.AnnualReport;
import java.util.List;

public interface AnnualReportService {
    List<AnnualReport> getAllAnnualReports();
    AnnualReport getAnnualReportById(Integer id);
    AnnualReport getAnnualReportByYear(Integer year);
    void addAnnualReport(AnnualReport annualReport);
    void updateAnnualReport(AnnualReport annualReport);
    void deleteAnnualReport(Integer id);
}

