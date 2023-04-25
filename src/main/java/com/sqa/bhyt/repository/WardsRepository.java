package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Wards;

@Repository
public interface WardsRepository extends JpaRepository<Wards, String>{
	Optional<Wards> findByCode(String code);
	
	@Query(value = "SELECT * FROM wards where district_code = :districtCode", nativeQuery = true)
	List<Wards> findByDistrictCode(@Param("districtCode") String districtCode);
}
