package com.example.report.report.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.report.report.Service.reportService;

@RestController
@RequestMapping("/reports")
public class reportController {

    @Autowired
    private reportService reportservice;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReport() throws IOException {
        byte[] reportBytes = reportservice.generateReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.xlsx");

        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }

        @GetMapping("/download/batch/{batch}")
    public ResponseEntity<byte[]> downloadBatchWiseReport(@PathVariable String batch) {
        try {
            byte[] reportBytes = reportservice.generateBatchWiseReport(batch);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "batch_report_" + batch + ".xlsx");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/course/{course}")
    public ResponseEntity<byte[]> downloadCourseWiseReport(@PathVariable String course) {
        try {
            byte[] reportBytes = reportservice.generateCourseWiseReport(course);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "course_report_" + course + ".xlsx");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{course}/{batch}")
    public ResponseEntity<byte[]> downloadBatchCourseWiseReport(@PathVariable String course, @PathVariable String batch) {
        try {
            byte[] reportBytes = reportservice.generateBatchCourseWiseReport(course, batch);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "batch_course_report_" + course + "_" + batch + ".xlsx");

            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    


