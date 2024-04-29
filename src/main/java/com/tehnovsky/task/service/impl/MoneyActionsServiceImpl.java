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
import com.tehnovsky.task.util.enums.ExceptionTemplates;
import com.tehnovsky.task.util.exceptions.InvalidAccountCurrency;
import com.tehnovsky.task.util.exceptions.NotEnoughMoneyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.tehnovsky.task.util.ValuesChecker.checkSendingMoneyToUserDTO;
import static com.tehnovsky.task.util.ValuesChecker.checkTopUpUserBalanceDTO;

@Service
@RequiredArgsConstructor
public class MoneyActionsServiceImpl implements MoneyActionsService {

    private final UserService userService;
    private final AccountService accountService;
    private final OperationService operationService;

    @Override
    public void topUpUserBalance(TopUpUserBalanceDTO balanceDTO) {
        checkTopUpUserBalanceDTO(balanceDTO);
        Currency currency = balanceDTO.getCurrency();
        BigDecimal amountOfMoney = balanceDTO.getAmountOfMoney();
        User user = userService.findById(balanceDTO.getUserId());
        Account userAccount = accountService.findUserAccountByCurrency(user, currency);
        if (!accountService.isCorrectAccountCurrency(userAccount, currency)) {
            throw new InvalidAccountCurrency(String.format(ExceptionTemplates.INVALID_CURRENCY_EXCEPTION_FORMAT.getMessage(), userAccount.getCurrency(), currency));
        }

        updateUserAccountBalance(userAccount, amountOfMoney, true);
        addOperationToUser(user, amountOfMoney, currency, true);
    }

    @Override
    public void sendMoneyToAnotherUser(SendingMoneyToUserDTO moneyToUserDTO) {
        checkSendingMoneyToUserDTO(moneyToUserDTO);
        BigDecimal amountOfMoney = moneyToUserDTO.getAmount();
        User sender = userService.findById(moneyToUserDTO.getSenderUserId());
        User receiver = userService.findById(moneyToUserDTO.getReceiverUserId());
        Account senderAccount = accountService.findUserAccountByCurrency(sender, moneyToUserDTO.getSenderCurrency());
        Currency currency = senderAccount.getCurrency();
        Account receiverAccount = accountService.findUserAccountByCurrency(receiver, currency);
        updateUserAccountBalance(senderAccount, amountOfMoney, false);
        updateUserAccountBalance(receiverAccount, amountOfMoney, true);
        addOperationToUser(sender, amountOfMoney, currency, false);
        addOperationToUser(sender, amountOfMoney, currency, true);
    }

    private void addOperationToUser(User user, BigDecimal amountOfMoney, Currency currency, boolean isDeposit) {
        Operation operation = OperationBuilder.buildOperation(user, amountOfMoney, currency, isDeposit);
        operationService.addOperation(operation);
    }

    private void updateUserAccountBalance(Account userAccount, BigDecimal amountOfMoney, boolean isDeposit) {
        BigDecimal prevBalance = userAccount.getBalance();
        BigDecimal balanceAfterActions;
        if (isDeposit) {
            balanceAfterActions = prevBalance.add(amountOfMoney);
        } else {
            balanceAfterActions = prevBalance.subtract(amountOfMoney);
        }
        if (balanceAfterActions.doubleValue() < 0.0) {
            throw new NotEnoughMoneyException(ExceptionTemplates.NOT_ENOUGH_MONEY_EXCEPTION_FORMAT.getMessage());
        }

        accountService.updateBalanceById(userAccount.getId(), balanceAfterActions);
    }
}
