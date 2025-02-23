package org.finance.casserver.exception;

public class TicketExpiredException extends RuntimeException {
    public TicketExpiredException(String message) {
        super(message);
    }
}