/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cpit.project;

import java.util.ArrayList;
import java.util.Scanner;

public class EntitleLeave_System {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HR_System system = new HR_System();   // Use HR_System class

        // Load demo employees and demo leave requests
        seedDemoData(system);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== HR System - Sprint 1 =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee Information");
            System.out.println("3. Search / View Employee");
            System.out.println("4. View Employee Leave Entitlement");
            System.out.println("5. Approve / Reject Leave Request");
            System.out.println("6. View Leave History for Employee");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice;

            if (in.hasNextInt()) {
                choice = in.nextInt();
                in.nextLine(); // clear leftover newline
            } else {
                System.out.println("Invalid choice.");
                in.nextLine(); // clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    handleAddEmployee(system, in);
                    break;
                case 2:
                    handleUpdateEmployee(system, in);
                    break;
                case 3:
                    handleSearchOrViewEmployee(system, in);
                    break;
                case 4:
                    handleViewLeaveEntitlement(system, in);
                    break;
                case 5:
                    handleApproveOrRejectLeave(system, in);
                    break;
                case 6:
                    handleViewLeaveHistory(system, in);
                    break;
                case 0:
                    exit = true;
                    System.out.println("Exiting Sprint 1...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        in.close();
    }

    // Load demo employees and demo leave requests
    private static void seedDemoData(HR_System system) {
        Employee e1 = new Employee("E1", "Mawadda", "IT", "2023-09-01");
        Employee e2 = new Employee("E2", "Amal", "HR", "2022-03-10");

        system.add_Employee(e1, 20);
        system.add_Employee(e2, 25);

        system.createLeaveRequestForDemo(
                "E1", "Annual", "2025-01-10", "2025-01-12", 3, "Family trip");
        system.createLeaveRequestForDemo(
                "E2", "Sick", "2025-02-05", "2025-02-06", 2, "Flu");
    }

    // Add employee with validation
    private static void handleAddEmployee(HR_System system, Scanner in) {
        try {
            System.out.print("Enter Employee ID: ");
            String id = in.nextLine();

            System.out.print("Enter Employee Name: ");
            String name = in.nextLine();
            
            // ____ Validate name letters only ____
        if (!isValidName(name)) {
              System.out.println("Invalid name, Only letters are allowed.");
              return; 
          }


            System.out.print("Enter Department: ");
            String dept = in.nextLine();

            System.out.print("Enter Joining Date (YYYY-MM-DD): ");
            String join = in.nextLine();

            System.out.print("Enter Annual Leave Entitlement (days): ");
            String entStr = in.nextLine();   // Read entitlement as string

            // Check empty fields (including entitlement)
            if (id.isEmpty() || name.isEmpty() || dept.isEmpty()
                    || join.isEmpty() || entStr.isEmpty()) {
                throw new IllegalArgumentException("All employee fields are mandatory");
            }

            // Validate date format
            if (!isValidDateFormat(join)) {
                System.out.println("Invalid date format. Please use (YYYY-MM-DD).");
                return;
            }

            // Convert entitlement to integer
            int entitlement;
            try {
                entitlement = Integer.parseInt(entStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number for entitlement.");
                return;
            }

            Employee emp = new Employee(id, name, dept, join);

            system.add_Employee(emp, entitlement);

            System.out.println("Employee added successfully.");
        } catch (Exception e) {
            System.out.println("Error while adding employee: " + e.getMessage());
        }
    }

    // Date format validation (YYYY-MM-DD)
    private static boolean isValidDateFormat(String date) {
        if (date == null) return false;
        if (date.length() != 10) return false;

        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            return false;
        }

        for (int i = 0; i < 10; i++) {
            if (i == 4 || i == 7) continue; // Skip '-'
            char c = date.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    // Update employee info (empty fields = no update)
    private static void handleUpdateEmployee(HR_System system, Scanner in) {
        try {
            System.out.print("Enter Employee ID to update: ");
            String id = in.nextLine();

            System.out.print("Enter new name (or leave empty): ");
            String name = in.nextLine();

            System.out.print("Enter new department (or leave empty): ");
            String dept = in.nextLine();

            System.out.print("Enter new joining date (or leave empty): ");
            String join = in.nextLine();
            
            if ((name == null || name.isEmpty()) && (dept == null || dept.isEmpty()) && (join == null || join.isEmpty())) {

             System.out.println("There are no updates. Employee information was not changed.");
              return;
            }

             // ____ Validate date format ___
        if (!join.isEmpty() && !isValidDateFormat(join)) {
            System.out.println("Invalid date format. Please use (YYYY-MM-DD).");
            return;
        }

        // ____ Validate name letters ____
        if (!name.isEmpty() && !isValidName(name)) {
            System.out.println("Invalid name, Only letters are allowed.");
            return;
        }

            system.update_Employee(id, name, dept, join);
            System.out.println("Employee updated successfully.");
        } catch (Exception e) {
            System.out.println("Error while updating employee: " + e.getMessage());
        }
    }

    // Search by ID or name
    private static void handleSearchOrViewEmployee(HR_System system, Scanner in) {
        System.out.println("1. View by ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter choice: ");

        int ch;
        try {
            ch = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        if (ch == 1) {
            System.out.print("Enter Employee ID: ");
            String id = in.nextLine();
            Employee emp = system.viewEmployeeById(id);
            if (emp == null) {
                System.out.println("Employee not found.");
            } else {
                System.out.println(emp.toString());
            }
        } else if (ch == 2) {
            System.out.print("Enter part of name: ");
            String namePart = in.nextLine();
            ArrayList<Employee> list = system.searchEmployeesByName(namePart);
            if (list.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                System.out.println("Employees found:");
                for (Employee e : list) {
                    System.out.println(e.toString());
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Show employee leave entitlement
    private static void handleViewLeaveEntitlement(HR_System system, Scanner in) {
        try {
            System.out.print("Enter Employee ID: ");
            String id = in.nextLine();
            int available = system.getAvailableLeave(id);
            System.out.println("Available leave days for employee " + id + ": " + available);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Approve or reject pending leave
    private static void handleApproveOrRejectLeave(HR_System system, Scanner in) {
        try {
            ArrayList<LeaveRequest> pending = system.getPendingLeaveRequests();
            if (pending.isEmpty()) {
                System.out.println("No pending leave requests.");
                return;
            }

            System.out.println("\n===== Pending Leave Requests =====");
            for (LeaveRequest r : pending) {
                Employee emp = system.viewEmployeeById(r.getEmployeeId());
                String empName = (emp != null) ? emp.getName() : "Unknown";

                System.out.println("--------------------------------------");
                System.out.println("Request ID : " + r.getRequestId());
                System.out.println("Employee   : " + empName + " (ID: " + r.getEmployeeId() + ")");
                System.out.println("Leave Type : " + r.getLeaveType());
                System.out.println("Days       : " + r.getDaysRequested());
                System.out.println("Reason     : " + r.getReason());
                System.out.println("Status     : " + r.getStatus());
            }
            System.out.println("--------------------------------------");

            System.out.print("Enter Request ID to process: ");
            int reqId = Integer.parseInt(in.nextLine());

            System.out.print("Approve (A) or Reject (R)? ");
            String ans = in.nextLine().trim().toUpperCase();

            if ("A".equals(ans)) {
                system.approveLeave(reqId);
                System.out.println("Leave request approved.");
            } else if ("R".equals(ans)) {
                system.rejectLeave(reqId);
                System.out.println("Leave request rejected.");
            } else {
                System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error while processing leave: " + e.getMessage());
        }
    }

    // Show employee leave history
    private static void handleViewLeaveHistory(HR_System system, Scanner in) {
        System.out.print("Enter Employee ID: ");
        String id = in.nextLine();
        ArrayList<LeaveRequest> history = system.getLeaveHistory(id);
        if (history.isEmpty()) {
            System.out.println("No leave history for this employee.");
        } else {
            System.out.println("Leave history for " + id + ":");
            for (LeaveRequest r : history) {
                System.out.println(
                        "ReqID=" + r.getRequestId()
                                + ", Type=" + r.getLeaveType()
                                + ", Days=" + r.getDaysRequested()
                                + ", Reason=" + r.getReason()
                                + ", Status=" + r.getStatus()
                );
            }
        }
    }
    
     //  method to validate the name contains only letters and spaces
    private static boolean isValidName(String name) {

        if (name == null || name.isEmpty()) {
            return false;
        }

        // check every character
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (!Character.isLetter(c) && c != ' ') {
                return false;   
            }
        }

        return true; 
    }
}
