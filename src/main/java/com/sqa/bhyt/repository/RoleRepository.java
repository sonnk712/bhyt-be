package com.sqa.bhyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sqa.bhyt.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByCode(String Code);
}
