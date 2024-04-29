package com.tehnovsky.task.dto;

import com.tehnovsky.task.util.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopUpUserBalanceDTO {
    private long userId;
    private BigDecimal amountOfMoney;
    private Currency currency;
}
