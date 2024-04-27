package com.tehnovsky.task.repository;

import com.tehnovsky.task.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long> {

}
