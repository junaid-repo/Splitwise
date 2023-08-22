package com.splitwise.app.sgroups.repository;

import com.splitwise.app.sgroups.entites.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberSaveRepository extends JpaRepository<GroupMembers, Integer> {
}
