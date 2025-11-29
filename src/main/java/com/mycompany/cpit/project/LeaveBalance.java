/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;
public class LeaveBalance {

    private String employeeId;
    private int annualEntitlement; // total leave days per year
    private int takenDays;         // leave days already taken

    public LeaveBalance(String employeeId, int annualEntitlement) {
        if (employeeId == null || employeeId.isEmpty() || annualEntitlement < 0) {
            throw new IllegalArgumentException("Invalid leave balance data");
        }
        this.employeeId = employeeId;
        this.annualEntitlement = annualEntitlement;
        this.takenDays = 0;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getAnnualEntitlement() {
        return annualEntitlement;
    }

    public int getTakenDays() {
        return takenDays;
    }

    // Available leave days
    public int getAvailable() {
        return annualEntitlement - takenDays;
    }

    // Deduct days from balance (used when leave approved)
    public void deduct(int days) {
        if (days <= 0 || days > getAvailable()) {
            throw new IllegalArgumentException("Invalid deduction");
        }
        this.takenDays += days;
    }

    @Override
    public String toString() {
        return "LeaveBalance{"
                + "employeeId='" + employeeId + '\''
                + ", annualEntitlement=" + annualEntitlement
                + ", takenDays=" + takenDays
                + ", available=" + getAvailable()
                + '}';
    }
}
