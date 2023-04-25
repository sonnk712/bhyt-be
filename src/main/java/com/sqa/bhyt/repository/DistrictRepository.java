package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Districts;

@Repository
public interface DistrictRepository extends JpaRepository<Districts, String>{
	Optional<Districts> findByCode(String code);
	
	@Query(value = "SELECT * FROM districts where province_code = :provinceCode", nativeQuery = true)
	List<Districts> findByProvinceCode(@Param("provinceCode") String provinceCode);
}
