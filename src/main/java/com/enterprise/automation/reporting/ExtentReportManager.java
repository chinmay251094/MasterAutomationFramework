package com.enterprise.automation.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Path;

public class ExtentReportManager {
    private final ExtentReports reports;

    public ExtentReportManager(Path output) {
        this.reports = new ExtentReports();
        this.reports.attachReporter(new ExtentSparkReporter(output.toString()));
    }

    public ExtentReports reports() {
        return reports;
    }
}
