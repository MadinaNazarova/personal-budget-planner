
public class FinancialRequest {
    private String requestId;
    private String userRole; // "CLIENT" or "MANAGER"
    private String actionDetails;

    public FinancialRequest(String requestId, String userRole, String actionDetails) {
        this.requestId = requestId;
        this.userRole = userRole;
        this.actionDetails = actionDetails;
    }

    public String getRequestId() { return requestId; }
    public String getUserRole() { return userRole; }
    public String getActionDetails() { return actionDetails; }

    @Override
    public String toString() {
        return "FinancialRequest{id='" + requestId + "', role='" + userRole + "', details='" + actionDetails + "'}";
    }
}
