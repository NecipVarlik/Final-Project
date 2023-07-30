package com.necipvarlik.final_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.necipvarlik.final_project.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String role);
}
