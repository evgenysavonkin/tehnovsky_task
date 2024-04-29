package com.tehnovsky.task.util.builders;

import com.tehnovsky.task.model.Operation;
import com.tehnovsky.task.model.User;
import com.tehnovsky.task.util.enums.Currency;
import com.tehnovsky.task.util.enums.OperationType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static com.tehnovsky.task.util.ValuesChecker.*;

public class OperationBuilder {

    private OperationBuilder() {

    }

    public static Operation buildOperation(User user, BigDecimal amountOfMoney, Currency currency, boolean isDeposit) {
        checkUserNotNull(user);
        checkAmountOfMoney(amountOfMoney);
        checkCurrencyNotNull(currency);

        Operation operation = Operation.builder()
                .amount(amountOfMoney)
                .currency(currency)
                .operationDate(new Timestamp(new Date().getTime()))
                .user(user)
                .build();

        if (isDeposit) {
            operation.setOperationType(OperationType.DEPOSIT);
        } else {
            operation.setOperationType(OperationType.WITHDRAW);
        }

        return operation;
    }
}
