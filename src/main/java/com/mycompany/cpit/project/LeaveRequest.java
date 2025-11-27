/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;

public class LeaveRequest {

    private int requestId;
    private String employeeId;
    private String leaveType;
    private String fromDate;
    private String toDate;
    private int daysRequested;
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED

    public LeaveRequest(int requestId,
                        String employeeId,
                        String leaveType,
                        String fromDate,
                        String toDate,
                        int daysRequested,
                        String reason) {

        if (employeeId == null || employeeId.isEmpty()
                || leaveType == null || leaveType.isEmpty()
                || fromDate == null || fromDate.isEmpty()
                || toDate == null || toDate.isEmpty()
                || daysRequested <= 0
                || reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("All leave request fields are mandatory");
        }

        this.requestId = requestId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.daysRequested = daysRequested;
        this.reason = reason;
        this.status = "PENDING";
    }

    public int getRequestId() {
        return requestId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getDaysRequested() {
        return daysRequested;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        this.status = "APPROVED";
    }

    public void reject() {
        this.status = "REJECTED";
    }

    @Override
    public String toString() {
        return "LeaveRequest{"
                + "requestId=" + requestId
                + ", employeeId='" + employeeId + '\''
                + ", leaveType='" + leaveType + '\''
                + ", fromDate='" + fromDate + '\''
                + ", toDate='" + toDate + '\''
                + ", daysRequested=" + daysRequested
                + ", reason='" + reason + '\''
                + ", status='" + status + '\''
                + '}';
    }
}

