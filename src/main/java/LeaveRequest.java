
public class LeaveRequest {

    private int requestId;
    private String employeeId;
    private String leaveType;
    private String fromDate;
    private String toDate;
    private int daysRequested;
    private String reason;
    private String status;

    public LeaveRequest(int requestId, String employeeId, String leaveType, String fromDate, String toDate, int daysRequested, String reason, String status) {
        if (employeeId == null || employeeId.isEmpty()
                || leaveType == null || leaveType.isEmpty()
                || fromDate == null || fromDate.isEmpty()
                || toDate == null || toDate.isEmpty()
                || daysRequested <= 0
                || reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("Invalid leave request data");
        }
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.daysRequested = daysRequested;
        this.reason = reason;
        this.status = "PENDING"; //The default status
    }

    public int getRequestId() {
        return requestId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getDaysRequested() {
        return daysRequested;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        this.status = "APPROVED";
    }

    public void reject() {
        this.status = "REJECTED";
    }
}
