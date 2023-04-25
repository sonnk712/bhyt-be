package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.ChargeUnit;

@Repository
public interface ChargeUnitRepository extends JpaRepository<ChargeUnit, String>{
	
	Optional<ChargeUnit> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<ChargeUnit> findByCodeAndIsDeleted(String code, int isDeleted);
	
	Boolean existsByCodeAndIsDeleted(String code, int isDeleted);
	
	@Query(value = "SELECT * from charge_unit ORDER BY created_date desc", nativeQuery = true)
	List<ChargeUnit> findAll();
	
	@Query(value = "SELECT * from charge_unit where "
			+ "(name like concat('%', :textSearch ,'%') "
			+ "or code like concat('%', :textSearch ,'%')) "
			+ "and is_deleted = :isDeleted "
			+ "ORDER BY created_date desc", nativeQuery = true)
	List<ChargeUnit> filter(@Param("textSearch") String textSearch, 
			@Param("isDeleted") int isDeleted);
}
