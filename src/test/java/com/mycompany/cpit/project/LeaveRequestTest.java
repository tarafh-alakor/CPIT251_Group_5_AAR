/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.cpit.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaveRequestTest {

    private LeaveRequest createValidRequest() {
        return new LeaveRequest(
                1,
                "E001",
                "Annual",
                "2024-01-01",
                "2024-01-05",
                5,
                "Vacation"
        );
    }

    @Test
    public void testConstructorValid() {
        LeaveRequest req = createValidRequest();

        assertEquals(1, req.getRequestId());
        assertEquals("E001", req.getEmployeeId());
        assertEquals("Annual", req.getLeaveType());
        assertEquals("2024-01-01", req.getFromDate());
        assertEquals("2024-01-05", req.getToDate());
        assertEquals(5, req.getDaysRequested());
        assertEquals("Vacation", req.getReason());
        assertEquals("PENDING", req.getStatus());
    }

    @Test
    public void testConstructorInvalidEmptyEmployeeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveRequest(
                    1,
                    "",
                    "Annual",
                    "2024-01-01",
                    "2024-01-05",
                    5,
                    "Vacation"
            );
        });
    }

    @Test
    public void testConstructorInvalidEmptyLeaveType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveRequest(
                    1,
                    "E001",
                    "",
                    "2024-01-01",
                    "2024-01-05",
                    5,
                    "Vacation"
            );
        });
    }

    @Test
    public void testConstructorInvalidZeroDays() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveRequest(
                    1,
                    "E001",
                    "Annual",
                    "2024-01-01",
                    "2024-01-05",
                    0,
                    "Vacation"
            );
        });
    }

    @Test
    public void testConstructorInvalidEmptyReason() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveRequest(
                    1,
                    "E001",
                    "Annual",
                    "2024-01-01",
                    "2024-01-05",
                    5,
                    ""
            );
        });
    }

    @Test
    public void testApproveChangesStatus() {
        LeaveRequest req = createValidRequest();
        req.approve();
        assertEquals("APPROVED", req.getStatus());
    }

    @Test
    public void testRejectChangesStatus() {
        LeaveRequest req = createValidRequest();
        req.reject();
        assertEquals("REJECTED", req.getStatus());
    }

    @Test
    public void testToStringNotNull() {
        LeaveRequest req = createValidRequest();
        assertNotNull(req.toString());
    }
}
