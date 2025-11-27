/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;

/**
 *
 * @author mawad
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReportService {
    
  public String generateEmployeeReport(ArrayList<Employee> employees) {
        String report = "Employee Report\n-----------------\n";

        if (employees.isEmpty()) {
            report += "No employees found.\n";
            return report;
        }

        for (Employee e : employees) {
            report += e.getId() + ", "+ e.getName() + ", " + e.getDepartment() + ", " + e.getJoiningDate() + "\n";
        }

        return report;
    }

    public String generateContractExpiryReport(ArrayList<Contract> list, String from, String to) {
        String report = "Contract Expiry Report\n-----------------\n";
        boolean found = false;

        for (Contract c : list) {
            if (c.getEndDate().compareTo(from) >= 0 && c.getEndDate().compareTo(to) <= 0) {

                report += c.toString() + "\n";
                found = true;
            }
        }

        if (!found) {
            report += "No contracts found in this date range.\n";
        }

        return report;
    }

    public String generateLeaveReport(ArrayList<LeaveRequest> reqs, String from, String to) {
        String report = "Leave Report\n-----------------\n";
        boolean found = false;

        for (LeaveRequest r : reqs) {
            if (r.getFromDate().compareTo(from) >= 0 && r.getFromDate().compareTo(to) <= 0) {

                report += r.toString() + "\n";
                found = true;
            }
        }

        if (!found) {
            report += "No leave requests found in this date range.\n";
        }

        return report;
    }

    public void exportToCsv(String content, String file) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    public void exportToPdf(String content, String file) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }  
}
