/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class HR_System {

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<LeaveBalance> leaveBalances = new ArrayList<>();
    private ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();
    private int nextRequestId = 1;
    private ArrayList<Contract> contracts = new ArrayList<>();
    private int nextContractId = 1;

    // Add employee and create initial leave balance
    public void add_Employee(Employee employee, int annualEntitlement) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (annualEntitlement < 0) {
            throw new IllegalArgumentException("Entitlement must be >= 0");
        }
        employees.add(employee);
        leaveBalances.add(new LeaveBalance(employee.getId(), annualEntitlement));
    }

    // Update basic employee info (empty values mean: do not change)
    public void update_Employee(String id, String newName, String newDept, String newJoinDate) {
        Employee emp = findEmployeeById(id);
        if (emp == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        if (newName != null && !newName.isEmpty()) {
            emp.setName(newName);
        }
        if (newDept != null && !newDept.isEmpty()) {
            emp.setDepartment(newDept);
        }
        if (newJoinDate != null && !newJoinDate.isEmpty()) {
            emp.setJoiningDate(newJoinDate);
        }
    }

    // View employee by ID
    public Employee viewEmployeeById(String id) {
        return findEmployeeById(id);
    }

    // Search employees by name (contains, case-insensitive)
    public ArrayList<Employee> searchEmployeesByName(String namePart) {
        ArrayList<Employee> result = new ArrayList<>();
        if (namePart == null || namePart.isEmpty()) {
            return result;
        }

        String lower = namePart.toLowerCase();
        for (Employee emp : employees) {
            if (emp.getName().toLowerCase().contains(lower)) {
                result.add(emp);
            }
        }
        return result;
    }
    public ArrayList<Employee> getEmployees() {
    return employees;
}
public ArrayList<LeaveRequest> getLeaveRequests() {
    return leaveRequests;
}


    // Get available leave days for an employee
    public int getAvailableLeave(String employeeId) {
        LeaveBalance balance = findLeaveBalance(employeeId);
        if (balance == null) {
            throw new IllegalArgumentException("Leave balance not found for employee " + employeeId);
        }
        return balance.getAvailable();
    }

    // Create a demo leave request (used by seeding in main)
    public void createLeaveRequestForDemo(String employeeId,
            String leaveType,
            String fromDate,
            String toDate,
            int days,
            String reason) {
        LeaveRequest req = new LeaveRequest(nextRequestId++, employeeId, leaveType, fromDate, toDate, days, reason);
        leaveRequests.add(req);
    }

    // Approve a leave request: deduct from balance and set status
    public void approveLeave(int requestId) {
        LeaveRequest req = findLeaveRequest(requestId);
        if (req == null) {
            throw new IllegalArgumentException("Request not found");
        }

        if ("APPROVED".equals(req.getStatus()) || "REJECTED".equals(req.getStatus())) {
            System.out.println("This request has already been processed.");
            return;
        }

        LeaveBalance balance = findLeaveBalance(req.getEmployeeId());
        if (balance == null) {
            throw new IllegalStateException("Leave balance not found for employee");
        }

        balance.deduct(req.getDaysRequested());
        req.approve();
    }

    // Reject a leave request: change status only
    public void rejectLeave(int requestId) {
        LeaveRequest req = findLeaveRequest(requestId);
        if (req == null) {
            throw new IllegalArgumentException("Request not found");
        }

        if ("APPROVED".equals(req.getStatus()) || "REJECTED".equals(req.getStatus())) {
            System.out.println("This request has already been processed.");
            return;
        }

        req.reject();
    }

    // Return all pending leave requests
    public ArrayList<LeaveRequest> getPendingLeaveRequests() {
        ArrayList<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest r : leaveRequests) {
            if ("PENDING".equals(r.getStatus())) {
                result.add(r);
            }
        }
        return result;
    }

    // Return all leave requests for a specific employee
    public ArrayList<LeaveRequest> getLeaveHistory(String employeeId) {
        ArrayList<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest r : leaveRequests) {
            if (r.getEmployeeId().equals(employeeId)) {
                result.add(r);
            }
        }
        return result;
    }

    // --------- Helper methods ---------
    private Employee findEmployeeById(String id) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    private LeaveBalance findLeaveBalance(String employeeId) {
        for (LeaveBalance lb : leaveBalances) {
            if (lb.getEmployeeId().equals(employeeId)) {
                return lb;
            }
        }
        return null;
    }

    private LeaveRequest findLeaveRequest(int requestId) {
        for (LeaveRequest r : leaveRequests) {
            if (r.getRequestId() == requestId) {
                return r;
            }
        }
        return null;
    }

    public Contract addOrUpdateContract(String employeeId,
            String startDate,
            String endDate,
            int remainingDays,
            String documentPath) {

        Employee emp = findEmployeeById(employeeId);
        if (emp == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        Contract existing = findContractByEmployee(employeeId);
        if (existing == null) {
            Contract c = new Contract(nextContractId,
                    employeeId,
                    startDate,
                    endDate,
                    remainingDays,
                    documentPath);
            contracts.add(c);
            nextContractId++;
            return c;
        } else {
            existing.setStartDate(startDate);
            existing.setEndDate(endDate);
            existing.setRemainingDays(remainingDays);
            existing.setDocumentPath(documentPath);
            return existing;
        }
    }

    public ArrayList<Contract> getAllContracts() {
        return contracts;
    }

    //(هنا في غلط)
    public ArrayList<Contract> getContractsNearExpiry() {
        ArrayList<Contract> result = new ArrayList<>();
        LocalDate today = LocalDate.now();  // Get today's date
        for (Contract c : contracts) {
            LocalDate expiryDate = LocalDate.parse(c.getEndDate());  // Convert expiry date to LocalDate
            // Ensure the contract is within 90 days of expiry from today
            if (!expiryDate.isBefore(today) && expiryDate.isBefore(today.plusDays(90))) {
                result.add(c);
            }
        }
        return result;
    }

    private Contract findContractByEmployee(String employeeId) {
        for (Contract c : contracts) {
            if (c.getEmployeeId().equals(employeeId)) {
                return c;
            }
        }
        return null;
    }

    //Generates a report of employee information including their ID, name, department, and joining date.
    public String generateEmployeeReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Employee Information Report ===\n");
        for (Employee e : employees) {
            sb.append("ID: ").append(e.getId())
                    .append(", Name: ").append(e.getName())
                    .append(", Department: ").append(e.getDepartment())
                    .append(", Joining Date: ").append(e.getJoiningDate())
                    .append("\n");
        }
        return sb.toString();
    }

    // Generates a report of contracts that are expiring, which includes the contract ID, employee ID, end date, and remaining days.
    public String generateContractExpiryReport(String fromDate, String toDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Contract Expiry Report (")
                .append(fromDate).append(" to ").append(toDate).append(") ===\n");

        for (Contract c : contracts) {
            String end = c.getEndDate();
            if (end.compareTo(fromDate) >= 0 && end.compareTo(toDate) <= 0) {
                sb.append("Contract ID: ").append(c.getContractId())
                        .append(", Employee ID: ").append(c.getEmployeeId())
                        .append(", End Date: ").append(end)
                        .append(", Remaining Days: ").append(c.getRemainingDays())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    // Generates a report of leave requests, which includes the request ID, employee ID, leave type, dates, days requested, and request status.
    public String generateLeaveReport(String fromDate, String toDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Leave Report (")
                .append(fromDate).append(" to ").append(toDate).append(") ===\n");

        for (LeaveRequest r : leaveRequests) {
            String from = r.getFromDate();
            if (from.compareTo(fromDate) >= 0 && from.compareTo(toDate) <= 0) {
                sb.append("ReqID: ").append(r.getRequestId())
                        .append(", EmpID: ").append(r.getEmployeeId())
                        .append(", Type: ").append(r.getLeaveType())
                        .append(", From: ").append(r.getFromDate())
                        .append(", To: ").append(r.getToDate())
                        .append(", Days: ").append(r.getDaysRequested())
                        .append(", Status: ").append(r.getStatus())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    // Exports the provided report content to a CSV file.
    public void exportReportToCsv(String content, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(content);
        writer.close();
    }

    // Exports the provided report content to a PDF file.
    public void exportReportToPdf(String content, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(content);
        writer.close();
    }
}
