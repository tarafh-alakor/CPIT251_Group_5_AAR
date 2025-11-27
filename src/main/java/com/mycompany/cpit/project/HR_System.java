/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpit.project;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

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

    public ArrayList<Contract> getContractsNearExpiry() {
        ArrayList<Contract> result = new ArrayList<>();
        for (Contract c : contracts) {
            if (c.isNearExpiry()) {
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
}
