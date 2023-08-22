package com.splitwise.app.suser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitwise.app.suser.entity.SUserEntity;

public interface SUserSaveRepository extends JpaRepository<SUserEntity, Integer>{

}
