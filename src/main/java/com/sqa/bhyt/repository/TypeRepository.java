package com.sqa.bhyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, String>{
	boolean existsByCodeAndIsDeleted(String code, int isDeleted);
	
	Optional<Type> findByCodeAndIsDeleted(String code, int isDeleted);
	
	Optional<Type> findByIdAndIsDeleted(String id, int isDeleted);
}	
