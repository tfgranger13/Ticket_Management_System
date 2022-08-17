package com.tfg.tms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfg.tms.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByCode(String code);

}
