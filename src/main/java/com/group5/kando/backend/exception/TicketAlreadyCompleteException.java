package com.group5.kando.backend.exception;

public class TicketAlreadyCompleteException extends Exception {
    public TicketAlreadyCompleteException(String message) {
        super(message);
    }
}
