package com.necipvarlik.final_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.necipvarlik.final_project.entities.Order;
import com.necipvarlik.final_project.entities.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT o FROM Order o WHERE o.seller.id = :id")
	List<Order> findBySellerId (int id);

	@Query("SELECT o FROM Order o WHERE o.customer.id = :id")
	List<Order> findByCustomerId(int id); 
}
