package com.api.productengine.exception;
import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime stamp;
    private int status;
    private String message;
    private String path;

    public ErrorResponse(int status, String message, String path){
        this.stamp = LocalDateTime.now();
        this.status=status;
        this.message=message;
        this.path=path;
    }

    public LocalDateTime getStamp() {
        return stamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    
 
}
