package com.tehnovsky.task.util.exceptions;

public class InvalidAccountCurrency extends RuntimeException {
    public InvalidAccountCurrency(String message) {
        super(message);
    }
}
