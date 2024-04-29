package com.tehnovsky.task.handlers;

import com.tehnovsky.task.util.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAccountCurrency.class)
    public ResponseEntity<String> handleInvalidAccountCurrencyException(InvalidAccountCurrency e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<String> handleNullArgumentException(NullArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> handleInvalidArgumentException(InvalidArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<String> handleNotEnoughMoneyException(NotEnoughMoneyException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserAccountsDontExistException.class)
    public ResponseEntity<String> handleUserAccountsDontExistException(UserAccountsDontExistException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
