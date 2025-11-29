/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.cpit.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author mawad
 */
public class ReportServiceTest {
   
   
    // ____ Employee Report Tests _____

    @Test
    public void testGenerateEmployeeReport() {
        ReportService s = new ReportService();

        // test for empty list
        assertTrue(s.generateEmployeeReport(new ArrayList<>()).contains("No employees"));

        // with data
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("E1","Mawadda","IT","2025-01-01"));
        list.add(new Employee("E2","Amal","HR","2025-02-01"));

        String r = s.generateEmployeeReport(list);
        assertTrue(r.contains("E1"));
        assertTrue(r.contains("E2"));
    }

     // ____ Contract Report Tests ____

    @Test
    public void testGenerateContractReport() {
        ReportService s = new ReportService();

        ArrayList<Contract> list = new ArrayList<>();
        list.add(new Contract(1,"E1","2025-01-01","2025-04-10",60,"c1.pdf"));
        list.add(new Contract(2,"E2","2025-01-01","2025-11-10",200,"c2.pdf"));

        // in range
        String r1 = s.generateContractExpiryReport(list,"2025-03-01","2025-06-01");
        assertTrue(r1.contains("contractId=1"));
        assertFalse(r1.contains("contractId=2"));

        // no matches
        String r2 = s.generateContractExpiryReport(list,"2026-01-01","2026-02-01");
        assertTrue(r2.contains("No contracts"));
    }
    
     // ____ Leave Report Tests ____


    @Test
    public void testGenerateLeaveReport() {
        ReportService s = new ReportService();

        ArrayList<LeaveRequest> req = new ArrayList<>();
        req.add(new LeaveRequest(1,"E1","Annual","2025-02-05","2025-02-06",2,"trip"));
        req.add(new LeaveRequest(2,"E2","Sick","2025-06-10","2025-06-11",1,"flu"));

        // in range
        String r1 = s.generateLeaveReport(req,"2025-02-01","2025-03-01");
        assertTrue(r1.contains("requestId=1"));
        assertFalse(r1.contains("requestId=2"));

        // no matches
        String r2 = s.generateLeaveReport(req,"2026-01-01","2026-02-01");
        assertTrue(r2.contains("No leave requests"));
    }

     // ____ Export File Tests ____

    @Test
    public void testExportFiles() throws Exception {
        ReportService s = new ReportService();

        // CSV
        File csv = new File("t.csv");
        s.exportToCsv("hi", csv.getName());
        assertTrue(csv.exists());

        // PDF
        File pdf = new File("t.pdf");
        s.exportToPdf("hi", pdf.getName());
        assertTrue(pdf.exists());
    }

}

