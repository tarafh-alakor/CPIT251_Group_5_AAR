
public class Employee {

    private String id;
    private String name;
    private String department;
    private String joiningDate; // YYYY-MM-DD
    //Constructor:
    public Employee(String id, String name, String department, String joiningDate) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty() || department == null
                || department.isEmpty() || joiningDate == null || joiningDate.isEmpty()) {
            throw new IllegalArgumentException("All employee fields are mandatory");
        }
        this.id = id;
        this.name = name;
        this.department = department;
        this.joiningDate = joiningDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setDepartment(String department) {
        if (department == null || department.isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        this.department = department;
    }

    public void setJoiningDate(String joiningDate) {
        if (joiningDate == null || joiningDate.isEmpty()) {
            throw new IllegalArgumentException("Joining-Date cannot be empty");
        }
        this.joiningDate = joiningDate;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", department=" + department + ", joiningDate=" + joiningDate + '}';
    }

}
