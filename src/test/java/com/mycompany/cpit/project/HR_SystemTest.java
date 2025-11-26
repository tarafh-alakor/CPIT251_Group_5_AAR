/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.cpit.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class HR_SystemTest {

    private Employee createEmployee(String id, String name) {
        return new Employee(id, name, "IT", "2024-01-01");
    }

    // ---------- add_Employee ----------

    @Test
    public void testAddEmployeeCreatesLeaveBalance() {
        HR_System system = new HR_System();
        Employee emp = createEmployee("E001", "Tarfa");

        system.add_Employee(emp, 30);

        Employee fetched = system.viewEmployeeById("E001");
        assertNotNull(fetched);
        assertEquals("Tarfa", fetched.getName());
        assertEquals(30, system.getAvailableLeave("E001"));
    }

    @Test
    public void testAddEmployeeNullThrows() {
        HR_System system = new HR_System();
        assertThrows(IllegalArgumentException.class, () -> {
            system.add_Employee(null, 30);
        });
    }

    @Test
    public void testAddEmployeeNegativeEntitlementThrows() {
        HR_System system = new HR_System();
        Employee emp = createEmployee("E001", "Tarfa");
        assertThrows(IllegalArgumentException.class, () -> {
            system.add_Employee(emp, -5);
        });
    }

    // ---------- update_Employee ----------

    @Test
    public void testUpdateEmployeeChangesFields() {
        HR_System system = new HR_System();
        Employee emp = createEmployee("E001", "OldName");
        system.add_Employee(emp, 20);

        system.update_Employee("E001", "NewName", "HR", "2025-01-01");

        Employee updated = system.viewEmployeeById("E001");
        assertEquals("NewName", updated.getName());
        assertEquals("HR", updated.getDepartment());
        assertEquals("2025-01-01", updated.getJoiningDate());
    }

    @Test
    public void testUpdateEmployeeNotFoundThrows() {
        HR_System system = new HR_System();
        assertThrows(IllegalArgumentException.class, () -> {
            system.update_Employee("XXX", "Name", "Dept", "2025-01-01");
        });
    }

    // ---------- searchEmployeesByName ----------

    @Test
    public void testSearchEmployeesByNameReturnsMatches() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);
        system.add_Employee(createEmployee("E002", "Tareq"), 20);
        system.add_Employee(createEmployee("E003", "Sara"), 20);

        ArrayList<Employee> result = system.searchEmployeesByName("Tar");

        assertEquals(2, result.size());
    }

    @Test
    public void testSearchEmployeesByNameEmptyReturnsEmptyList() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        ArrayList<Employee> result = system.searchEmployeesByName("");

        assertTrue(result.isEmpty());
    }

    // ---------- getAvailableLeave ----------

    @Test
    public void testGetAvailableLeaveNormal() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 15);

        int available = system.getAvailableLeave("E001");
        assertEquals(15, available);
    }

    @Test
    public void testGetAvailableLeaveNoBalanceThrows() {
        HR_System system = new HR_System();
        assertThrows(IllegalArgumentException.class, () -> {
            system.getAvailableLeave("UNKNOWN");
        });
    }

    // ---------- createLeaveRequestForDemo + getPendingLeaveRequests + getLeaveHistory ----------

    @Test
    public void testCreateLeaveRequestAndPendingList() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        system.createLeaveRequestForDemo("E001", "Annual", "2024-02-01", "2024-02-05", 5, "Vacation");

        ArrayList<LeaveRequest> pending = system.getPendingLeaveRequests();
        assertEquals(1, pending.size());
        assertEquals("E001", pending.get(0).getEmployeeId());
        assertEquals("PENDING", pending.get(0).getStatus());

        ArrayList<LeaveRequest> history = system.getLeaveHistory("E001");
        assertEquals(1, history.size());
    }

    // ---------- approveLeave ----------

    @Test
    public void testApproveLeaveDeductsBalanceAndChangesStatus() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        system.createLeaveRequestForDemo("E001", "Annual", "2024-02-01", "2024-02-03", 3, "Trip");

        LeaveRequest req = system.getPendingLeaveRequests().get(0);
        int requestId = req.getRequestId();

        system.approveLeave(requestId);

        assertEquals(17, system.getAvailableLeave("E001"));

        ArrayList<LeaveRequest> history = system.getLeaveHistory("E001");
        assertEquals("APPROVED", history.get(0).getStatus());
    }

    @Test
    public void testApproveLeaveRequestNotFoundThrows() {
        HR_System system = new HR_System();
        assertThrows(IllegalArgumentException.class, () -> {
            system.approveLeave(999);
        });
    }

    // ---------- rejectLeave ----------

    @Test
    public void testRejectLeaveChangesStatusOnly() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 10);

        system.createLeaveRequestForDemo("E001", "Annual", "2024-03-01", "2024-03-02", 2, "Reason");

        LeaveRequest req = system.getPendingLeaveRequests().get(0);
        int requestId = req.getRequestId();

        system.rejectLeave(requestId);

        assertEquals(10, system.getAvailableLeave("E001"));

        ArrayList<LeaveRequest> history = system.getLeaveHistory("E001");
        assertEquals("REJECTED", history.get(0).getStatus());
    }

    @Test
    public void testRejectLeaveRequestNotFoundThrows() {
        HR_System system = new HR_System();
        assertThrows(IllegalArgumentException.class, () -> {
            system.rejectLeave(999);
        });
    }
}
