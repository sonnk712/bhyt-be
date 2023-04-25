package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, String>{
	Optional<Agency> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<Agency> findByCodeAndIsDeleted(String id, int isDeleted);
	
	Boolean existsByCodeAndIsDeleted(String code, int isDeleted);
	
	@Query(value = "SELECT * from agency where "
			+ " person like concat('%', :textSearch, '%') "
			+ " or (district_code = :districtCode"
			+ " and is_deleted = :isDeleted) "
			+ " ORDER BY district_code", nativeQuery = true)
	List<Agency> filterByDistrict(
			@Param("textSearch") String textSearch, 
			@Param("districtCode") String districtCode,
			@Param("isDeleted") Integer isDeleted);
	
	@Query(value = "SELECT * from agency where "
			+ " person like concat('%', :textSearch, '%') "
			+ " and district_code = :districtCode "
			+ " and ward_code = :wardCode"
			+ " and is_deleted = :isDeleted "
			+ " ORDER BY district_code", nativeQuery = true)
	List<Agency> filterByDistrictAndWard(
			@Param("textSearch") String textSearch, 
			@Param("districtCode") String districtCode,
			@Param("wardCode") String wardCode,
			@Param("isDeleted") Integer isDeleted);
	
	@Query(value = "SELECT * FROM agency where is_deleted = :isDeleted ORDER BY district_code", nativeQuery = true)
	List<Agency> findAll(@Param("isDeleted") int isDeleted);
}
