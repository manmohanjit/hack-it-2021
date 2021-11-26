package com.cinema.booking.order;

public class InvalidOrderStateException extends Exception {
    public InvalidOrderStateException(Order order, String message) {
        super(message);
    }
}
