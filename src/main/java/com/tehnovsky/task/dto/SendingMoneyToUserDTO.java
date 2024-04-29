package com.tehnovsky.task.dto;

import com.tehnovsky.task.util.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SendingMoneyToUserDTO {
    private long senderUserId;
    private Currency senderCurrency;
    private long receiverUserId;
    private BigDecimal amount;
}
