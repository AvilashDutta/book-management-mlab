package com.monstarlab.bookmanagement.exception;


public class EmailException extends RuntimeException {
    public EmailException(){
        super("Exception occurred while sending email");
    }
    public EmailException(String message) {
        super(message);
    }
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
