package com.splitwise.app.sbills.repository;

import com.splitwise.app.sbills.entities.GroupExpenseAccounts;
import com.splitwise.app.sbills.entities.LogDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogSaveRepository extends JpaRepository<LogDetailsEntity, Integer> {
}
