package com.splitwise.app.sgroups.repository;

import com.splitwise.app.sgroups.entites.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBasicSaveRepository extends JpaRepository<GroupEntity, Integer> {
}
