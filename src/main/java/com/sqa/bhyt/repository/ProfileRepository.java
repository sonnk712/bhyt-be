package com.sqa.bhyt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String>{
	Optional<Profile> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<Profile> findByCreatedUserAndIsDeleted(String username, int isDeleted);
	
	@Query(value = "SELECT * FROM profile ORDER BY updated_date", nativeQuery = true)
	List<Profile> findAll();
	
	@Query(value = "SELECT * FROM profile where name like concat('%', :textSearch, '%') and is_deleted = :isDeleted", nativeQuery = true)
	List<Profile> filterByTextSearch(@Param("textSearch") String textSearch, @Param("isDeleted") Integer isDeleted);
	
	@Query(value = "SELECT * FROM profile where "
			+ "name like concat('%', :textSearch, '%') "
			+ "and (created_date between :from and :to) "
			+ "ORDER BY created_date", nativeQuery = true)
	List<Profile> fitlerByPeriod(@Param("textSearch") String textSearch, 
			@Param("from") Date from, 
			@Param("to") Date to);
	
	@Query(value = "SELECT * from profile where type_id = :typeId and name like concat('%', :textSearch, '%')", nativeQuery = true)
	List<Profile> fitlerByType(@Param("textSearch") String textSearch, @Param("typeId") String typeId);
	
	@Query(value = "SELECT * from profile where status = :status and name like concat('%', :textSearch, '%')", nativeQuery = true)
	List<Profile> fitlerByStatus(@Param("textSearch") String textSearch, @Param("status") String status);
}
