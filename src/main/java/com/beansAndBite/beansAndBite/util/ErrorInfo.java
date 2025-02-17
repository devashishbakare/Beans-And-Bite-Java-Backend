package com.beansAndBite.beansAndBite.util;

public class ErrorInfo extends BaseResponse {
    private int status;
    private String message;
    private String timestamp;

    public ErrorInfo(int status, String message, String timestamp) {
        super(message);
        this.status = status;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}