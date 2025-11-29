/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.cpit.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeaveBalanceTest {

    @Test
    public void testConstructorValid() {
        LeaveBalance lb = new LeaveBalance("E001", 30);
        assertEquals("E001", lb.getEmployeeId());
        assertEquals(30, lb.getAnnualEntitlement());
        assertEquals(0, lb.getTakenDays());
        assertEquals(30, lb.getAvailable());
    }

    @Test
    public void testConstructorInvalidEmployeeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveBalance("", 30);
        });
    }

    @Test
    public void testConstructorInvalidEntitlement() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LeaveBalance("E001", -5);
        });
    }

    @Test
    public void testGetAvailable() {
        LeaveBalance lb = new LeaveBalance("E001", 20);
        assertEquals(20, lb.getAvailable());
        lb.deduct(5);
        assertEquals(15, lb.getAvailable());
    }

    @Test
    public void testDeductValid() {
        LeaveBalance lb = new LeaveBalance("E001", 15);
        lb.deduct(5);
        assertEquals(5, lb.getTakenDays());
        assertEquals(10, lb.getAvailable());
    }

    @Test
    public void testDeductInvalidNegative() {
        LeaveBalance lb = new LeaveBalance("E001", 10);
        assertThrows(IllegalArgumentException.class, () -> {
            lb.deduct(-2);
        });
    }

    @Test
    public void testDeductInvalidTooLarge() {
        LeaveBalance lb = new LeaveBalance("E001", 10);
        assertThrows(IllegalArgumentException.class, () -> {
            lb.deduct(50);
        });
    }

    @Test
    public void testToStringNotNull() {
        LeaveBalance lb = new LeaveBalance("E001", 12);
        assertNotNull(lb.toString());
    }
}
