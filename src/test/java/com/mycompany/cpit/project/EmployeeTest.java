/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.cpit.project;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

   
    @Test
    public void testGetId() {
        Employee instance = new Employee("E001", "Tarfa", "IT", "2024-01-01");
        assertEquals("E001", instance.getId());
    }

    
    @Test
    public void testGetName() {
        Employee instance = new Employee("E001", "Tarfa", "IT", "2024-01-01");
        assertEquals("Tarfa", instance.getName());
    }

   
    @Test
    public void testGetDepartment() {
        Employee instance = new Employee("E001", "Tarfa", "IT", "2024-01-01");
        assertEquals("IT", instance.getDepartment());
    }

    
    @Test
    public void testGetJoiningDate() {
        Employee instance = new Employee("E001", "Tarfa", "IT", "2024-01-01");
        assertEquals("2024-01-01", instance.getJoiningDate());
    }

    @Test
    public void testSetName() {
        Employee instance = new Employee("E001", "Old", "IT", "2024-01-01");
        instance.setName("NewName");
        assertEquals("NewName", instance.getName());
    }

    @Test
    public void testSetDepartment() {
        Employee instance = new Employee("E001", "Tarfa", "IT", "2024-01-01");
        instance.setDepartment("HR");
        assertEquals("HR", instance.getDepartment());
    }
}
