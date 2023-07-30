package com.necipvarlik.final_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.necipvarlik.final_project.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

	//@Query("SELECT a FROM Address a WHERE a.addressUser.id = :id AND a.active = true")
	
	@Query("SELECT c FROM Cart c WHERE c.customer.id = :id")
	Optional<Cart> findByCustomerId(Integer id);
}
