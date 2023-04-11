package com.groupfive.kando.backend.exception;

public class TicketAlreadyCompleteException extends Exception {
    public TicketAlreadyCompleteException(String message) {
        super(message);
    }
}
