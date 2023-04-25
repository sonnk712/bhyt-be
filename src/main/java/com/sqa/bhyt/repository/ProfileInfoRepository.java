package com.sqa.bhyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.ProfileInformation;

@Repository
public interface ProfileInfoRepository extends JpaRepository<ProfileInformation, String>{
	Optional<ProfileInformation> findByIdAndIsDeleted(String id, int isDeleted);
}
