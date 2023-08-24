package com.splitwise.app.sbills.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.splitwise.app.sbills.entities.SettleEntity;

public interface SettleSaveRepository extends JpaRepository<SettleEntity, Integer> {
}
