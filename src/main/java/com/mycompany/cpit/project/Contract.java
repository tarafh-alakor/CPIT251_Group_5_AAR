/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;

import java.time.LocalDate;

/**
 *
 * @author mawad
 */
public class Contract {
    
    private int contractId;
    private String employeeId;
    private String startDate;    
    private String endDate;      
    private int remainingDays;   
    private String documentPath; 

    public Contract(int contractId, String employeeId, String startDate, String endDate, int remainingDays, String documentPath) {

        if (employeeId == null || employeeId.isEmpty()) {
            throw new IllegalArgumentException("employeeId cannot be empty");
        }
        if (startDate == null || startDate.isEmpty()) {
            throw new IllegalArgumentException("startDate cannot be empty");
        }
        if (endDate == null || endDate.isEmpty()) {
            throw new IllegalArgumentException("endDate cannot be empty");
        }

        this.contractId = contractId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingDays = remainingDays;
        this.documentPath = documentPath;
    }

    public int getContractId() {
        return contractId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || startDate.isEmpty()) {
            throw new IllegalArgumentException("startDate cannot be empty");
        }
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        if (endDate == null || endDate.isEmpty()) {
            throw new IllegalArgumentException("endDate cannot be empty");
        }
        this.endDate = endDate;
    }

    public void setRemainingDays(int remainingDays) {
        
        this.remainingDays = remainingDays;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    //(هنا في غلط)
    public boolean isNearExpiry() {
    LocalDate today = LocalDate.now();  // Get today's date
    LocalDate expiryDate = LocalDate.parse(this.endDate);  // Convert expiry date string to LocalDate

    // Print debug information to see today's date and the expiry date
    System.out.println("Today's date: " + today);
    System.out.println("Contract expiry date: " + expiryDate);

    // Check if the expiry date is within the next 90 days from today, including today
    return !expiryDate.isBefore(today) && !expiryDate.isAfter(today.plusDays(90));
}


    @Override
    public String toString() {
        return "Contract{" + "contractId=" + contractId + ", employeeId='" + employeeId + '\'' + ", startDate='" + startDate + '\''
                + ", endDate='" + endDate + '\'' + ", remainingDays=" + remainingDays + ", documentPath='" + documentPath + '\'' + '}';
    }
}
