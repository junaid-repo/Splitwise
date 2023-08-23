package com.splitwise.app.sbills.repository;

import com.splitwise.app.sbills.entities.ExpenseSaveEntity;
import com.splitwise.app.sbills.entities.GroupExpenseAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupExSaveRepository extends JpaRepository<GroupExpenseAccounts, Integer> {
}
