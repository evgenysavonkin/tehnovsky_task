package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;
import com.tehnovsky.task.model.Account;
import com.tehnovsky.task.model.Operation;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.service.AccountService;
import com.tehnovsky.task.service.MoneyActionsService;
import com.tehnovsky.task.service.OperationService;
import com.tehnovsky.task.service.UserService;
import com.tehnovsky.task.util.builders.OperationBuilder;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.exceptions.InvalidAccountCurrency;
import com.tehnovsky.task.util.exceptions.NotEnoughMoneyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MoneyActionsServiceImpl implements MoneyActionsService {

    private final UserService userService;
    private final AccountService accountService;
    private final OperationService operationService;
    private static final String INVALID_CURRENCY_FORMAT = "Requested currency is invalid! Required currency for this operation is '%s', but actual was '%s'";
    private static final String NEGATIVE_MONEY_FORMAT = "Amount of money can't be <= 0";
    private static final String NOT_ENOUGH_MONEY_FORMAT = "Not enough money to perform this action";

    @Override
    public void topUpUserBalance(TopUpUserBalanceDTO balanceDTO) {
        User user = userService.findById(balanceDTO.getUserId());
        Currency currency = balanceDTO.getCurrency();
        Account userAccount = accountService.getAccountByCurrency(user, currency);
        BigDecimal amountOfMoney = balanceDTO.getAmountOfMoney();
        if (!accountService.isCorrectAccountCurrency(userAccount, currency)) {
            throw new InvalidAccountCurrency(String.format(INVALID_CURRENCY_FORMAT, userAccount.getCurrency(), currency));
        }

        performActionsWithUserAccount(userAccount, amountOfMoney, true);
        addOperationToUser(user, amountOfMoney, currency, true);
    }

    @Override
    public void sendMoneyToAnotherUser(SendingMoneyToUserDTO moneyToUserDTO) {
        BigDecimal amountOfMoney = moneyToUserDTO.getAmount();
        if (amountOfMoney.doubleValue() <= 0) {
            throw new NotEnoughMoneyException(NEGATIVE_MONEY_FORMAT);
        }

        User sender = userService.findById(moneyToUserDTO.getSenderUserId());
        User receiver = userService.findById(moneyToUserDTO.getReceiverUserId());
        Account senderAccount = accountService.findUserAccountByCurrency(sender, moneyToUserDTO.getSenderCurrency());
        Currency currency = senderAccount.getCurrency();
        Account receiverAccount = accountService.getAccountByCurrency(receiver, currency);
        performActionsWithUserAccount(senderAccount, amountOfMoney, false);
        performActionsWithUserAccount(receiverAccount, amountOfMoney, true);
        addOperationToUser(sender, amountOfMoney, currency, false);
        addOperationToUser(sender, amountOfMoney, currency, true);
    }

    private void addOperationToUser(User user, BigDecimal amountOfMoney, Currency currency, boolean isDeposit) {
        Operation operation = OperationBuilder.buildOperation(user, amountOfMoney, currency, isDeposit);
        operationService.addOperation(operation);
    }

    private void performActionsWithUserAccount(Account userAccount, BigDecimal amountOfMoney, boolean isDeposit) {
        BigDecimal prevBalance = userAccount.getBalance();
        BigDecimal balanceAfterActions;
        if (isDeposit) {
            balanceAfterActions = prevBalance.add(amountOfMoney);
        } else {
            balanceAfterActions = prevBalance.subtract(amountOfMoney);
        }

        if (balanceAfterActions.doubleValue() < 0.0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FORMAT);
        }

        accountService.updateBalanceById(userAccount.getId(), balanceAfterActions);
    }
}
