package com.tehnovsky.task.service;

import com.tehnovsky.task.dto.SendingMoneyToUserDTO;
import com.tehnovsky.task.dto.TopUpUserBalanceDTO;

public interface MoneyActionsService {
    void topUpUserBalance(TopUpUserBalanceDTO userBalanceDTO);

    void sendMoneyToAnotherUser(SendingMoneyToUserDTO moneyToUserDTO);
}
