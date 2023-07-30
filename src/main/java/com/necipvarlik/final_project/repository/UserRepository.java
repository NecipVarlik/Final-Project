package com.necipvarlik.final_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.necipvarlik.final_project.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
}
