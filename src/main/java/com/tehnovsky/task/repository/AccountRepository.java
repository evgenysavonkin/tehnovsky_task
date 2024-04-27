package com.tehnovsky.task.repository;

import com.tehnovsky.task.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE account SET balance = :amount WHERE id = :account_id")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void updateBalanceById(@Param("account_id") long id, @Param("amount") BigDecimal amount);
}
