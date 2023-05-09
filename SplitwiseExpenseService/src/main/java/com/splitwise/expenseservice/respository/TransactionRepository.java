package com.splitwise.expenseservice.respository;

import com.splitwise.expenseservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from transaction_table t where t.groupid = :groupID", nativeQuery = true)
    void deleteTransactionByGroupID(@Param("groupID") String groupID);

    Set<Transaction> findTransactionByGroupID(String groupID);
}
