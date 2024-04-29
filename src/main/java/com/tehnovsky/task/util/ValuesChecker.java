package com.tehnovsky.task.util;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;
import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.enums.ExceptionTemplates;
import com.tehnovsky.task.util.exceptions.InvalidArgumentException;
import com.tehnovsky.task.util.exceptions.NullArgumentException;
import com.tehnovsky.task.util.exceptions.UserAccountsDontExistException;

import java.math.BigDecimal;

public class ValuesChecker {
    private ValuesChecker() {

    }

    public static void checkTopUpUserBalanceDTO(TopUpUserBalanceDTO balanceDTO) {
        checkUserId(balanceDTO.getUserId());
        checkAmountOfMoney(balanceDTO.getAmountOfMoney());
        checkCurrencyNotNull(balanceDTO.getCurrency());
    }

    public static void checkSendingMoneyToUserDTO(SendingMoneyToUserDTO moneyToUserDTO) {
        checkUserId(moneyToUserDTO.getSenderUserId());
        checkCurrencyNotNull(moneyToUserDTO.getSenderCurrency());
        checkUserId(moneyToUserDTO.getReceiverUserId());
        checkAmountOfMoney(moneyToUserDTO.getAmount());
    }

    public static void checkUserNotNull(User user) {
        if (user == null) {
            throw new NullArgumentException(String.format(ExceptionTemplates.NULL_ARGUMENT_EXCEPTION_FORMAT.getMessage(), "User"));
        }
    }

    public static void checkAccountNotNull(Account account) {
        if (account == null) {
            throw new NullArgumentException(String.format(ExceptionTemplates.NULL_ARGUMENT_EXCEPTION_FORMAT.getMessage(), "Account"));
        }
    }

    public static void checkUserAccountsNotNull(User user) {
        if (user.getAccounts() == null) {
            throw new UserAccountsDontExistException(String.format(ExceptionTemplates.USER_ACCOUNTS_DONT_EXIST_EXCEPTION_FORMAT.getMessage(), user.getId()));
        }
    }

    public static void checkUserId(long userId) {
        if (userId <= 0) {
            throw new InvalidArgumentException(String.format(ExceptionTemplates.INVALID_USER_ID_EXCEPTION_FORMAT.getMessage(), userId));
        }
    }

    public static void checkCurrencyNotNull(Currency currency) {
        if (currency == null) {
            throw new NullArgumentException(String.format(ExceptionTemplates.NULL_ARGUMENT_EXCEPTION_FORMAT.getMessage(), "Currency"));
        }
    }

    public static void checkAmountOfMoney(BigDecimal amountOfMoney) {
        if (amountOfMoney == null) {
            throw new NullArgumentException(String.format(ExceptionTemplates.NULL_ARGUMENT_EXCEPTION_FORMAT.getMessage(), "Amount of money"));
        }
        if (amountOfMoney.doubleValue() <= 0) {
            throw new InvalidArgumentException(String.format(ExceptionTemplates.INVALID_MONEY_AMOUNT_EXCEPTION_FORMAT.getMessage(), amountOfMoney.doubleValue()));
        }
    }
}
