package com.tehnovsky.task.dto;

import com.tehnovsky.task.util.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendingMoneyToUserDTO {
    private long senderUserId;
    private Currency senderCurrency;
    private long receiverUserId;
    private BigDecimal amount;
}
