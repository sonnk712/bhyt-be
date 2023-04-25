package com.sqa.bhyt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Identity;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, String>{
	
	@Query(value = "SELECT * FROM identity where cccd = :cccd and is_deleted = :isDeleted", nativeQuery = true)
	Optional<Identity> findByCccdAndIsDeleted(@Param("cccd") String cccd, @Param("isDeleted") int isDeleted);
	
	Optional<Identity> findByCccdAndIsHouseHoldAndIsDeleted(String cccd, Boolean isHousehold, int isDeleted);
	
	@Query(value = "SELECT * FROM identity where family_record_id = :familyRecordId and is_deleted = :isDeleted", 
			nativeQuery = true)
	List<Identity> findByFamilyAndIsDeleted(@Param("familyRecordId") String familyRecordId, @Param("isDeleted") int isDeleted);
	
	boolean existsByCccdAndIsDeleted(String cccd, int isDeleted);
	
}
