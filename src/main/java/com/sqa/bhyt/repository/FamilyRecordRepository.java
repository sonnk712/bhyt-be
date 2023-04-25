package com.sqa.bhyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.FamilyRecord;

@Repository
public interface FamilyRecordRepository extends JpaRepository<FamilyRecord, String>{
	Optional<FamilyRecord> findByIdAndIsDeleted(String id, int isDeleted);
}
