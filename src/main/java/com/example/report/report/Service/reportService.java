package com.example.report.report.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class reportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] generateReport() throws IOException {
        // Write SQL query to retrieve data from the database
        String sqlQuery = "SELECT * FROM mcq";

        // Execute the query and retrieve data
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);

        // Generate Excel report
        byte[] reportBytes = generateExcelReport(rows);

        return reportBytes;
    }

    public byte[] generateBatchWiseReport(String batch) throws IOException {
        // Write SQL query to retrieve data for the specified batch from the database
        String sqlQuery = "SELECT * FROM mcq WHERE batch = ?";

        // Execute the query and retrieve data
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery, batch);

        // Generate Excel report for the specified batch
        byte[] reportBytes = generateExcelReport(rows);

        return reportBytes;
    }

    
    public byte[] generateCourseWiseReport(String course) throws IOException {
        // Write SQL query to retrieve data for the specified course from the database
        String sqlQuery = "SELECT * FROM mcq WHERE course = ?";

        // Execute the query and retrieve data
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery, course);

        // Generate Excel report for the specified course
        byte[] reportBytes = generateExcelReport(rows);

        return reportBytes;
    }


    public byte[] generateBatchCourseWiseReport(String course, String batch) throws IOException {
        // Write SQL query to retrieve data for the specified course and batch from the database
        String sqlQuery = "SELECT * FROM mcq WHERE course = ? AND batch = ?";
    
        // Execute the query and retrieve data
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery, course, batch);
    
        // Generate Excel report for the specified course and batch
        byte[] reportBytes = generateExcelReport(rows);
    
        return reportBytes;
    }
    

   
    
    


    private byte[] generateExcelReport(List<Map<String, Object>> data) throws IOException {
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MCQ Report");

        // Create header row
        Row headerRow = sheet.createRow(0);
        int columnCount = 0;
        for (String key : data.get(0).keySet()) {
            Cell cell = headerRow.createCell(columnCount++);
            cell.setCellValue(key);
        }

        // Create data rows
        int rowCount = 1;
        for (Map<String, Object> row : data) {
            Row dataRow = sheet.createRow(rowCount++);
            int cellCount = 0;
            for (Object value : row.values()) {
                Cell cell = dataRow.createCell(cellCount++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } // Add additional checks for other data types as needed
            }
        }

        // Write workbook to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Convert ByteArrayOutputStream to byte array
        byte[] reportBytes = outputStream.toByteArray();
        outputStream.close();

        return reportBytes;
    }
}
