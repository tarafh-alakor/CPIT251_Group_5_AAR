/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.cpit.project;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mawad
 */
public class ContractTest {
    @Test
    public void testConstructor_validData() {
        Contract c = new Contract(1,"E1","2025-01-01","2025-12-01",120,"contract1.pdf");

        assertEquals(1, c.getContractId());
        assertEquals("E1", c.getEmployeeId());
        assertEquals("2025-01-01", c.getStartDate());
        assertEquals("2025-12-01", c.getEndDate());
        assertEquals(120, c.getRemainingDays());
        assertEquals("contract1.pdf", c.getDocumentPath());
    }

    @Test
    public void testConstructor_emptyEmployeeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contract(1, "", "2025-01-01", "2025-12-01", 120, "doc.pdf");
        });
    }

    @Test
    public void testConstructor_emptyStartDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contract(1, "E1", "", "2025-12-01", 120, "doc.pdf");
        });
    }

    @Test
    public void testConstructor_emptyEndDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contract(1, "E1", "2025-01-01", "", 120, "doc.pdf");
        });
    }

    @Test
    public void testIsNearExpiry_true() {
        Contract c = new Contract(1, "E1", "2025-01-01", "2025-03-01", 60, "path");
        assertTrue(c.isNearExpiry());
    }

    @Test
    public void testIsNearExpiry_false() {
        Contract c = new Contract(1, "E1", "2025-01-01", "2025-12-01", 200, "path");
        assertFalse(c.isNearExpiry());
    }
}
