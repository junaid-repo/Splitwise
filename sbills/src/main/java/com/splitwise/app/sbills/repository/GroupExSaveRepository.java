package com.splitwise.app.sbills.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.splitwise.app.sbills.entities.GroupExpenseAccounts;

public interface GroupExSaveRepository extends JpaRepository<GroupExpenseAccounts, Integer> {

	@Query(value = "select * from group_expense_accounts gea where gea.giver=?1", nativeQuery = true)
	List<GroupExpenseAccounts> findByUsername(String username);

	@Query(value = "select * from group_expense_accounts gea where gea.giver=?1 or gea.takers=?1", nativeQuery = true)
	List<GroupExpenseAccounts> findTakersbyUsername(String username);

	@Query(value = "select * from group_expense_accounts gea where gea.giver=?1 and gea.takers=?2 and settled_ind is null", nativeQuery = true)
	List<GroupExpenseAccounts> findReceiverByUsername(String tempUser, String username);

	@Query(value = "select distinct gea.groupname  from group_expense_accounts gea where gea.giver = ?1 or gea.takers = ?1", nativeQuery = true)
	List<String> getUserGroups(String username);

	@Query(value = "select * from group_expense_accounts gea where gea.groupname=?1  and gea.giver=?2 and gea.settled_ind is null", nativeQuery = true)
	List<GroupExpenseAccounts> amountGivenToGroup(String obj, String username);

	@Query(value = "select * from group_expense_accounts gea where gea.groupname=?1  and gea.takers=?2 and gea.settled_ind is null", nativeQuery = true)
	List<GroupExpenseAccounts> amoutTakenFromGroup(String obj, String username);

	@Query(value = "update group_expense_accounts set settled_ind='Y' where giver=?1 and takers=?2", nativeQuery = true)
	void updateTheOutstandingBalance(String friend, String self);

	@Query(value = "update group_expense_accounts set settled_ind='Y' where giver=?1 and takers=?2", nativeQuery = true)
	void updateTheOutstandingBalance2(String self, String friend);
}
