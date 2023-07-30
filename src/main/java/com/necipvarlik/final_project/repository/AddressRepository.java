package com.necipvarlik.final_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.necipvarlik.final_project.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	@Query("SELECT a FROM Address a WHERE a.addressUser.id = :id AND a.active = true")
	List<Address> findByUserId(Integer id);
}
