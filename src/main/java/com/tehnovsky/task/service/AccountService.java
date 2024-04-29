package com.tehnovsky.task.service;

import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.enums.ExceptionTemplates;
import com.tehnovsky.task.util.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

import static com.tehnovsky.task.util.ValuesChecker.*;

public interface AccountService {
    void updateBalanceById(long id, BigDecimal amountOfMoney);

    default boolean isCorrectAccountCurrency(Account account, Currency currency) {
        checkAccountNotNull(account);
        checkCurrencyNotNull(currency);
        return account.getCurrency().equals(currency);
    }

    default Account findUserAccountByCurrency(User user, Currency currency) {
        checkUserNotNull(user);
        checkCurrencyNotNull(currency);
        checkUserAccountsNotNull(user);
        Optional<Account> accountOptional = user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst();

        return accountOptional.orElseThrow(() ->
                new AccountNotFoundException(String.format(ExceptionTemplates.ACCOUNT_NOT_FOUND_EXCEPTION_FORMAT.getMessage(), user.getId(), currency)));
    }
}
