package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.repository.AccountRepository;
import com.tehnovsky.task.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void updateBalanceById(long id, BigDecimal amountOfMoney) {
        log.info("AccountServiceImpl updateBalanceById start");
        accountRepository.updateBalanceById(id, amountOfMoney);
        log.info("AccountServiceImpl updateBalanceById end");
    }
}
