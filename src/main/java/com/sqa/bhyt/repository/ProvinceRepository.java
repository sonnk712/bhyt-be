package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Provinces;

@Repository
public interface ProvinceRepository extends JpaRepository<Provinces, String>{
	Optional<Provinces> findByCode(String code);
	
	List<Provinces> findAll(Sort sort);
	
	@Query(value = "Select p.* from provinces p left join districts d on p.code = d.province_code where d.code = :code ", nativeQuery = true)
	Optional<Provinces> findByDistrict(@Param("code") String code);
	
}
