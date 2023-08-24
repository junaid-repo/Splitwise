package com.splitwise.app.sbills.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.splitwise.app.sbills.entities.LogDetailsEntity;

public interface LogSaveRepository extends JpaRepository<LogDetailsEntity, Integer> {

	@Query(value="select * from log_details_entity lde where lde.username=?1", nativeQuery=true)
	List<LogDetailsEntity> findLogs(String username);
}
