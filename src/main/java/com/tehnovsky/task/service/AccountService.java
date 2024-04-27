package com.tehnovsky.task.service;

import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {

    String ACCOUNT_NOT_FOUND_FORMAT = "Account with user id = %d and currency = %s not found";

    void updateBalanceById(long id, BigDecimal amountOfMoney);

    default Account getAccountByCurrency(User user, Currency currency) {
        Optional<Account> optionalAccount = user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst();

        return optionalAccount.orElseThrow(() ->
                new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_FORMAT, user.getId(), currency)));
    }

    default boolean isCorrectAccountCurrency(Account account, Currency currency) {
        return account.getCurrency().equals(currency);
    }

    default Account findUserAccountByCurrency(User user, Currency currency) {
        Optional<Account> accountOptional = user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst();

        return accountOptional.orElseThrow(() ->
                new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_FORMAT, user.getId(), currency)));
    }
}
