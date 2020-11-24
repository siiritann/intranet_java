package com.intranet.project.controller.response;

public class ResponseBody {
    private String message;

    public ResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
