package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.model.Operation;
import com.tehnovsky.task.repository.OperationsRepository;
import com.tehnovsky.task.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final OperationsRepository operationsRepository;

    @Override
    @Transactional
    public void addOperation(Operation operation) {
        log.info("OperationServiceImpl addOperation start");
        operationsRepository.save(operation);
        log.info("OperationServiceImpl addOperation end");
    }
}
