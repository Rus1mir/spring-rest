package web.model;

public class Message {
    private String reason;
    private String cause;

    public Message() {
    }

    public Message(String reason, String cause) {
        this.reason = reason;
        this.cause = cause;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
