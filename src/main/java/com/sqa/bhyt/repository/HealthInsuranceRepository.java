package com.sqa.bhyt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.HealthInsurance;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, String>{
	Optional<HealthInsurance> findByIdAndIsDeleted(String id, int isDeleted);
	
	Optional<HealthInsurance> findByIdentityNumberAndIsDeleted(String identityNumber, int isDeleted);
	
	@Query(value = "SELECT EXISTS(SELECT * from health_insurance as hi inner join identity as i on hi.identity_id = i.id where (i.cccd = "
			+ ":cccd and hi.is_deleted = :isDeleted and i.is_deleted = :isDeleted))", nativeQuery = true)
	Integer existsByCCCDAndIsDeleted(@Param("cccd") String cccd, @Param("isDeleted") int isDeleted);
	
	List<HealthInsurance> findByCreatedUser(String createdUser);
	
	@Query(value = "SELECT hi.* from health_insurance as hi inner join identity as i on hi.identity_id = i.id where i.cccd = :cccd "
			+ "and hi.is_deleted = :isDeleted", nativeQuery = true)
	Optional<HealthInsurance> findByCccdAndIsDeleted(@Param("cccd") String cccd, @Param("isDeleted") int isDeleted);
	
	List<HealthInsurance> findByCardIssuerCodeAndIsDeletedAndStatus(String cardIssuerCode, int isDeleted, int isActivate);
	
	List<HealthInsurance> findByRegisterPlaceCodeAndCardIssuerCodeAndIsDeletedAndStatus(String registerPlace, String cardIssuer, int isDeleted, int isActivate);
	
	@Query(value = "SELECT * FROM health_insurance where (created_date between :paidFrom and :paidTo) and is_deleted = :isDeleted", nativeQuery = true)
	List<HealthInsurance> findbyPeriod(@Param("paidFrom") Date paidFrom, @Param("paidTo") Date paidTo, @Param("isDeleted") int isDeleted);
	
	@Query(value = "SELECT * from health_insurance "
			+ " where status in(:status) "
			+ " and is_deleted = :isDeleted "
			+ " and ((name like concat('%', :searchText, '%')) or (identity_number like concat('%', :searchText, '%')) or (card_issuer_name like concat('%', :searchText, '%'))) "
			+ " and (updated_date BETWEEN :startDate and :endDate) "
			+ " ORDER BY updated_date desc "
			+ " LIMIT :pageSize OFFSET :pageIndex", nativeQuery = true)
	List<HealthInsurance> statistic(@Param("searchText") String searchText, 
			@Param("status") List<Integer> status, 
			@Param("isDeleted") Integer isDeleted,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, 
			@Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);
	
	@Query(value = "SELECT COUNT(*) from health_insurance where status =:status "
			+ "and is_deleted = :isDeleted "
			+ "and (name like concat('%', :searchText, '%') or identity_number = :searchText) "
			+ "and (updated_date BETWEEN :startDate and :endDate)", nativeQuery = true)
	Integer count(@Param("searchText") String searchText, 
			@Param("status") Integer status, 
			@Param("isDeleted") Integer isDeleted,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
