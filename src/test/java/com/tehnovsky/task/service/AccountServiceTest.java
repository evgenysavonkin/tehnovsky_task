package com.tehnovsky.task.service;

import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.impl.AccountServiceImpl;
import com.tehnovsky.task.util.builders.UserBuilder;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.exceptions.AccountNotFoundException;
import com.tehnovsky.task.util.exceptions.NullArgumentException;
import com.tehnovsky.task.util.exceptions.UserAccountsDontExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


class AccountServiceTest {

    @Test
    void findUserAccountByCurrency_returnsUSDAccount_whenCurrencyIsUSD_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        BigDecimal moneyAmount = BigDecimal.valueOf(10.34);
        Account BYNAccount = new Account(1, moneyAmount, Currency.BYN, user);
        Account USDAccount = new Account(2, moneyAmount, Currency.USD, user);
        user.setAccounts(List.of(BYNAccount, USDAccount));

        Account actualAccount = accountService.findUserAccountByCurrency(user, Currency.USD);
        Assertions.assertEquals(actualAccount, USDAccount);
    }

    @Test
    void findUserAccountByBYNCurrency_returnsBYNAccount_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        BigDecimal moneyAmount = BigDecimal.valueOf(10.34);
        Account BYNAccount = new Account(1, moneyAmount, Currency.BYN, user);
        Account USDAccount = new Account(2, moneyAmount, Currency.USD, user);
        user.setAccounts(List.of(BYNAccount, USDAccount));

        Account actualAccount = accountService.findUserAccountByCurrency(user, Currency.BYN);
        Assertions.assertEquals(actualAccount, BYNAccount);
    }

    @Test
    void findUserAccount_byIncorrectCurrency_throwsAccountNotFoundException_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        BigDecimal moneyAmount = BigDecimal.valueOf(10.34);
        Account BYNAccount = new Account(1, moneyAmount, Currency.BYN, user);
        user.setAccounts(List.of(BYNAccount));

        Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.findUserAccountByCurrency(user, Currency.USD));
    }

    @Test
    void findUserAccount_if_no_accounts_throwsUserAccountsDontExistException_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        Assertions.assertThrows(UserAccountsDontExistException.class, () -> accountService.findUserAccountByCurrency(user, Currency.USD));
    }

    @Test
    void isCorrectAccountCurrency_withCorrectValue_returnsTrue_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        BigDecimal moneyAmount = BigDecimal.valueOf(10.34);
        Account BYNAccount = new Account(1, moneyAmount, Currency.BYN, user);
        Assertions.assertTrue(accountService.isCorrectAccountCurrency(BYNAccount, Currency.BYN));
    }

    @Test
    void isCorrectAccountCurrency_withIncorrectValue_returnsFalse_test() {
        AccountService accountService = new AccountServiceImpl(null);
        User user = UserBuilder.buildUser(1, "Bob");
        BigDecimal moneyAmount = BigDecimal.valueOf(10.34);
        Account BYNAccount = new Account(1, moneyAmount, Currency.BYN, user);
        Assertions.assertFalse(accountService.isCorrectAccountCurrency(BYNAccount, Currency.USD));
    }

    @Test
    void isCorrectAccountCurrency_throwsNullArgumentException_whenAccountNull_test() {
        AccountService accountService = new AccountServiceImpl(null);
        Assertions.assertThrows(NullArgumentException.class, () -> accountService.isCorrectAccountCurrency(null, Currency.USD));
    }

    @Test
    void isCorrectAccountCurrency_throwsNullArgumentException_whenCurrencyNull_test() {
        AccountService accountService = new AccountServiceImpl(null);
        Assertions.assertThrows(NullArgumentException.class, () -> accountService.isCorrectAccountCurrency(new Account(), null));
    }
}
