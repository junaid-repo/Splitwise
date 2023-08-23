package com.splitwise.app.sgroups.repository;

import com.splitwise.app.sgroups.entites.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupMemberSaveRepository extends JpaRepository<GroupMembers, Integer> {

    @Query(value="select * from group_members gm where gm.group_username=?1", nativeQuery = true)
    List<GroupMembers> findByUsername(String groupName);
}
