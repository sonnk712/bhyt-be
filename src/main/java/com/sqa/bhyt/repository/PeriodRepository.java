package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Period;

@Repository
public interface PeriodRepository extends JpaRepository<Period, String>{
	Optional<Period> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<Period> findByCodeAndIsDeleted(String code, int isDeleted);
	
	Boolean existsByCodeAndIsDeleted(String code, int isDeleted);
	
	@Query(value = "SELECT * from period ORDER BY created_date desc", nativeQuery = true)
	List<Period> findAll();
	
	@Query(value = "SELECT * from period where "
			+ "(name like concat('%', :textSearch ,'%') "
			+ "or code like concat('%', :textSearch ,'%')) "
			+ "and is_deleted = :isDeleted "
			+ "ORDER BY created_date desc", nativeQuery = true)
	List<Period> filter(@Param("textSearch") String textSearch, 
			@Param("isDeleted") int isDeleted);
}
