package app.database.dto.exception;

import app.database.dto.DTO;

import java.io.Serializable;

public class ExceptionMessage implements Serializable {

    private String message;

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}