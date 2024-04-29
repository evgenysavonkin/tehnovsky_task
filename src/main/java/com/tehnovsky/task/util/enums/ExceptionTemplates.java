package com.tehnovsky.task.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionTemplates {
    NULL_ARGUMENT_EXCEPTION_FORMAT("%s can't be null"),
    ACCOUNT_NOT_FOUND_EXCEPTION_FORMAT("Account with user id = %d and currency = %s not found"),
    USER_NOT_FOUND_EXCEPTION_FORMAT("User with id = %d wasn't found"),
    USER_ACCOUNTS_DONT_EXIST_EXCEPTION_FORMAT("Accounts of user with id = %d don't exist"),
    INVALID_USER_ID_EXCEPTION_FORMAT("User id can't be <= 0. Actual is %d"),
    INVALID_CURRENCY_EXCEPTION_FORMAT("Requested currency is invalid! Required currency for this operation is '%s', but actual was '%s'"),
    NOT_ENOUGH_MONEY_EXCEPTION_FORMAT("Not enough money to perform this action"),
    NEGATIVE_MONEY_EXCEPTION_FORMAT("Amount of money can't be <= 0"),
    INVALID_MONEY_AMOUNT_EXCEPTION_FORMAT("Amount of money can't be <= 0. Actual is %.2f");

    private final String message;
}
