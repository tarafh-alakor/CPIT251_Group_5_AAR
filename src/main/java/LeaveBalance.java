
public class LeaveBalance {

    private String employeeId;
    private int annualEntitlement; // the total leave days allowed per year
    private int takenDays;         // the leave days that are already taken

    public LeaveBalance(String employeeId, int annualEntitlement, int takenDays) {
        if (employeeId == null || employeeId.isEmpty() || annualEntitlement < 0) {
            throw new IllegalArgumentException("Invalid leave balance data");
        }
        this.employeeId = employeeId;
        this.annualEntitlement = annualEntitlement;
        this.takenDays = 0;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getAnnualEntitlement() {
        return annualEntitlement;
    }

    public int getTakenDays() {
        return takenDays;
    }

    // "The System shall automatically calculate and display available leave entitlements."
    public int getAvailable() {
        return (annualEntitlement - takenDays);
    }

    // "automatically deduct approved leave from the employee's balance"
    public void deduct(int days) {
        if (days <= 0 || days > getAvailable()) {
            throw new IllegalArgumentException("Invalid deduction");
        }
        this.takenDays += days;
    }

}
