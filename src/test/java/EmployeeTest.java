//Test for Employee class
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {

    Employee instance;

    @Before
    public void setUp() {
        instance = new Employee("E1", "Amal", "HR", "2022-03-10");
    }

    //Test of getId method, of class Employee.
    @Test
    public void testGetId() {
        System.out.println("getId");
        assertEquals("E1", instance.getId());
    }

    //Test of getName method, of class Employee.
    @Test
    public void testGetName() {
        System.out.println("getName");
        assertEquals("Amal", instance.getName());
    }

    //Test of getDepartment method, of class Employee.
    @Test
    public void testGetDepartment() {
        System.out.println("getDepartment");
        assertEquals("HR", instance.getDepartment());
    }

    //Test of getJoiningDate method, of class Employee.
    @Test
    public void testGetJoiningDate() {
        System.out.println("getJoiningDate");

        assertEquals("2022-03-10", instance.getJoiningDate());
    }

    //Test setName (valid)
    @Test
    public void testSetName() {
        System.out.println("setName: Valid");
        instance.setName("Lama");
        assertEquals("Lama", instance.getName());
    }

    // Test setName (invalid)
    @Test(expected = IllegalArgumentException.class)
    public void testSetName_Invalid() {
        System.out.println("setName: invalid (empty)");
        instance.setName("");
    }

    // Test setDepartment (valid)
    @Test
    public void testSetDepartment_Valid() {
        System.out.println("setDepartment: valid");
        instance.setDepartment("HR");
        assertEquals("HR", instance.getDepartment());
    }

    // Test setDepartment (invalid)
    @Test(expected = IllegalArgumentException.class)
    public void testSetDepartment_Invalid() {
        System.out.println("setDepartment: invalid (empty)");
        instance.setDepartment("");
    }

    // Test setJoiningDate (valid)
    @Test
    public void testSetJoiningDate_Valid() {
        System.out.println("setJoiningDate: valid");
        instance.setJoiningDate("2025-02-10");
        assertEquals("2025-02-10", instance.getJoiningDate());
    }

    // Test setJoiningDate (invalid)
    @Test(expected = IllegalArgumentException.class)
    public void testSetJoiningDate_Invalid() {
        System.out.println("setJoiningDate: invalid (empty)");
        instance.setJoiningDate("");
    }

    // Test toString
    @Test
    public void testToString() {
        System.out.println("toString");
        String expected = "Employee{id=E1, name=Amal, department=HR, joiningDate=2022-03-10}";
        assertEquals(expected, instance.toString());
    }

    // Test invalid constructor values
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidId() {
        new Employee("", "Amal", "IT", "2024-01-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidName() {
        new Employee("E1", "", "IT", "2024-01-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidDept() {
        new Employee("E1", "Amal", "", "2024-01-01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_InvalidJoinDate() {
        new Employee("E1", "Amal", "IT", "");
    }

}
