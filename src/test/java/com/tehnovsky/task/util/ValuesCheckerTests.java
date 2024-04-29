package com.tehnovsky.task.util;

import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.exceptions.InvalidArgumentException;
import com.tehnovsky.task.util.exceptions.NullArgumentException;
import com.tehnovsky.task.util.exceptions.UserAccountsDontExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class ValuesCheckerTests {

    private User user;
    private Account account;
    private static final BigDecimal balanceToTestWith = BigDecimal.valueOf(10.34);
    private static final Currency currencyToTestWith = Currency.USD;

    {
        setUpValues();
    }

    public void setUpValues() {
        user = User.builder()
                .username("Bob")
                .build();
        account = Account.builder()
                .balance(balanceToTestWith)
                .currency(currencyToTestWith)
                .user(user)
                .build();
    }

    @Test
    void checkUserNotNullPasses_whenArgumentNotNull_test() {
        ValuesChecker.checkUserNotNull(user);
    }

    @Test
    void checkUserNotNull_throwsNullArgumentException_whenArgumentNull_test() {
        user = null;
        Assertions.assertThrows(NullArgumentException.class, () -> ValuesChecker.checkUserNotNull(user));
    }

    @Test
    void checkAccountNotNullPasses_whenArgumentNotNull_test() {
        ValuesChecker.checkAccountNotNull(account);
    }

    @Test
    void checkAccountNotNull_throwsNullArgumentException_whenArgumentNull_test() {
        account = null;
        Assertions.assertThrows(NullArgumentException.class, () -> ValuesChecker.checkAccountNotNull(account));
    }

    @Test
    void checkUserAccountsNotNullPasses_whenAccountsNotNull_test() {
        user.setAccounts(List.of(account));
        ValuesChecker.checkUserAccountsNotNull(user);
    }

    @Test
    void checkUserAccountsNotNull_throwsUserAccountsDontExistException_whenAccountsNull_test() {
        Assertions.assertThrows(UserAccountsDontExistException.class, () -> ValuesChecker.checkUserAccountsNotNull(user));
    }

    @Test
    void checkUserIdPasses_whenIdValid_test() {
        ValuesChecker.checkUserId(1);
    }

    @Test
    void checkUserId_throwsInvalidArgumentException_whenIdInvalid_test() {
        Assertions.assertThrows(InvalidArgumentException.class, () -> ValuesChecker.checkUserId(0));
    }

    @Test
    void checkCurrencyNotNullPasses_whenCurrencyNotNull_test() {
        ValuesChecker.checkCurrencyNotNull(currencyToTestWith);
    }

    @Test
    void checkCurrencyNotNull_throwsNullArgumentException_whenCurrencyNull_test() {
        Assertions.assertThrows(NullArgumentException.class, () -> ValuesChecker.checkCurrencyNotNull(null));
    }

    @Test
    void checkAmountOfMoneyPasses_whenArgumentValid_test() {
        ValuesChecker.checkAmountOfMoney(balanceToTestWith);
    }

    @Test
    void checkAmountOfMoney_throwsNullArgumentException_whenArgumentNull_test() {
        Assertions.assertThrows(NullArgumentException.class, () -> ValuesChecker.checkAmountOfMoney(null));
    }

    @Test
    void checkAmountOfMoney_throwsInvalidArgumentException_whenArgumentLowerZero_test() {
        Assertions.assertThrows(InvalidArgumentException.class, () -> ValuesChecker.checkAmountOfMoney(BigDecimal.valueOf(-1)));
    }
}
