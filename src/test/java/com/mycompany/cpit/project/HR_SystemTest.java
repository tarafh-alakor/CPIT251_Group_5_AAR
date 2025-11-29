/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.cpit.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
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

    // ---------- testAddOrUpdateContractCreatesNewContract ----------
    @Test
    public void testAddOrUpdateContractCreatesNewContract() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        Contract contract = system.addOrUpdateContract("E001", "2024-01-01", "2024-12-31", "path/to/contract");

        // Check that the contract was added
        
        assertNotNull(contract);
        assertEquals(1, contract.getContractId());
        assertEquals("E001", contract.getEmployeeId());
        assertEquals("2024-01-01", contract.getStartDate());
        assertEquals("2024-12-31", contract.getEndDate());
        assertEquals("path/to/contract", contract.getDocumentPath());
    }
       

// ---------- testAddOrUpdateContractUpdatesExistingContract ----------
    @Test
    public void testAddOrUpdateContractUpdatesExistingContract() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        // Add contract first
        system.addOrUpdateContract("E001", "2024-01-01", "2024-06-30", "path/to/contract");

        // Update contract
        Contract updatedContract = system.addOrUpdateContract("E001", "2024-07-01", "2024-12-31", "path/to/updatedContract");

        // Verify the contract was updated
        assertEquals("2024-12-31", updatedContract.getEndDate());
        assertEquals("path/to/updatedContract", updatedContract.getDocumentPath());
    }
   
// ---------- testGetAllContracts ----------
    @Test
    public void testGetAllContracts() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);
        system.add_Employee(createEmployee("E002", "Tareq"), 20);

        // Add contracts for both employees
        system.addOrUpdateContract("E001", "2024-01-01", "2024-06-30", "path/to/contract");
        system.addOrUpdateContract("E002", "2024-07-01", "2024-12-31", "path/to/contract");

        // Get all contracts and verify the size
        ArrayList<Contract> contracts = system.getAllContracts();
        assertEquals(2, contracts.size());  // Ensure both contracts are added
    }

// ---------- testGetContractsNearExpiry ---------- //(هنا في غلط)
    @Test
    public void testGetContractsNearExpiry() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);
        system.add_Employee(createEmployee("E002", "Amal"), 20);
        system.add_Employee(createEmployee("E003", "Mawadda"), 20);
        LocalDate today = LocalDate.now();
        
        // Add contracts
        system.addOrUpdateContract("E001", today.toString(),today.plusDays(90).toString(), "path/to/near_expiry_contract");  // Expected near expiry
        system.addOrUpdateContract("E002", today.toString(),today.plusDays(100).toString(), "path/to/contract");  // Not near expiry
        system.addOrUpdateContract("E003", today.toString(),today.minusDays(5).toString(), "path/to/expired_contract"); // Already expired

        ArrayList<Contract> contractsNearExpiry = system.getContractsNearExpiry();
        
        assertEquals(1, contractsNearExpiry.size());
        Contract c = contractsNearExpiry.get(0);
        assertEquals("E001", c.getEmployeeId());
        assertEquals(today.plusDays(90).toString(), c.getEndDate());
    
    }

    // ---------- generateEmployeeReport ----------
    @Test
    public void testGenerateEmployeeReport() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);
        system.add_Employee(createEmployee("E002", "Tareq"), 15);

        String report = system.generateEmployeeReport();

        assertTrue(report.contains("Tarfa"));
        assertTrue(report.contains("Tareq"));
        assertTrue(report.contains("=== Employee Information Report ==="));
    }

    // ---------- generateContractExpiryReport ----------
    @Test
    public void testGenerateContractExpiryReport() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        system.addOrUpdateContract("E001","2024-01-01","2024-06-01","path/to/contract");

        String report = system.generateContractExpiryReport("2024-01-01", "2024-06-30");

        assertTrue(report.contains("Contract Expiry Report"));
        assertTrue(report.contains("2024-06-01"));
    }
    // ---------- generateLeaveReport ----------

    @Test
    public void testGenerateLeaveReport() {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        system.createLeaveRequestForDemo("E001", "Annual", "2024-02-01", "2024-02-05", 5, "Vacation");

        String report = system.generateLeaveReport("2024-02-01", "2024-02-28");

        assertTrue(report.contains("Leave Report"));
        assertTrue(report.contains("Annual"));
        assertTrue(report.contains("2024-02-05"));
    }
// ---------- exportReportToCsv ----------

    @Test
    public void testExportReportToCsv() throws IOException {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        String report = system.generateEmployeeReport();

        String fileName = "employee_report.csv";
        system.exportReportToCsv(report, fileName);

        // Verify that the file exists and contains the correct content (simplified check for now)
        File file = new File(fileName);
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("Tarfa"));
    }

    // ---------- exportReportToPdf ----------
    @Test
    public void testExportReportToPdf() throws IOException {
        HR_System system = new HR_System();
        system.add_Employee(createEmployee("E001", "Tarfa"), 20);

        String report = system.generateEmployeeReport();

        String fileName = "employee_report.pdf";
        system.exportReportToPdf(report, fileName);

        // Verify that the file exists (simplified check for now)
        File file = new File(fileName);
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("Tarfa"));
    }

}
