package com.sqa.bhyt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<User> findByGmailAndIsDeleted(String gmail, int isDeleted);
	
	Optional<User> findByUsernameAndIsDeleted(String username, int isDeleted);
	
//	@Query(value = "select gmail from user where username = :username and is_deleted = :isDeleted", nativeQuery = true)
//	String findGmailByUsername(@Param("gmail") String gmail, @Param("isDeleted") int isDeleted);
	
	Boolean existsByIdAndIsDeleted(String id, int isDeleted);

	Boolean existsByUsernameAndIsDeleted(String username, int isDeleted);
	
	Boolean existsByGmailAndIsDeleted(String gmail, int isDeleted);
	
	Boolean existsByPhoneNumberAndIsDeleted(String phoneNumber, int isDeleted);
	
//	@Query(value = "select * from user where (id like CONCAT('%', :textSearch,'%') or name like CONCAT('%',:textSearch,'%')) and is_deleted = :isDeleted", nativeQuery = true)
//	List<User> filterByIdAndNameAndIsDeleted(@Param("textSearch") String textSearch, @Param("isDeleted") int isDeleted);
}
