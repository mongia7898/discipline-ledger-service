package com.mongia.discpline.ledger.Repository;

import com.mongia.discpline.ledger.Entity.Expenses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Integer> {

    List<Expenses> getExpensesBy(Pageable pageable);
}
