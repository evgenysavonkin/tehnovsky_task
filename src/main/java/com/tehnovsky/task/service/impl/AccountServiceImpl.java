package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.repository.AccountRepository;
import com.tehnovsky.task.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBalanceById(long id, BigDecimal amountOfMoney) {
        accountRepository.updateBalanceById(id, amountOfMoney);
    }
}
