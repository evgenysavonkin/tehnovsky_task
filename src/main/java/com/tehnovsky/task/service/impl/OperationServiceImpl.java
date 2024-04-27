package com.tehnovsky.task.service.impl;

import com.tehnovsky.task.model.Operation;
import com.tehnovsky.task.repository.OperationsRepository;
import com.tehnovsky.task.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationsRepository operationsRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addOperation(Operation operation) {
        operationsRepository.save(operation);
    }
}
