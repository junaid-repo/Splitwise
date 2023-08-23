package com.splitwise.app.sbills.repository;

import com.splitwise.app.sbills.entities.ExpenseSaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseSaveRepository extends JpaRepository<ExpenseSaveEntity, Integer> {
}
