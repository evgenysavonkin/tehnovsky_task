package com.tehnovsky.task.dto;

import com.tehnovsky.task.util.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopUpUserBalanceDTO {
    private long userId;
    private BigDecimal amountOfMoney;
    private Currency currency;
}
