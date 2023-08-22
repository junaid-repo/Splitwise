package com.splitwise.app.suser.repository;

import com.splitwise.app.suser.dto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.splitwise.app.suser.entity.SUserEntity;
import org.springframework.data.jpa.repository.Query;

public interface SUserSaveRepository extends JpaRepository<SUserEntity, Integer>{

    @Query(value="select * from suser s where s.username=?1", nativeQuery = true)
    SUserEntity findbyUsername(String username);
}
