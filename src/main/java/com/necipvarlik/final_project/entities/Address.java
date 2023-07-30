package com.necipvarlik.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String openAddress;
	
	private String explanation;

	private String neighborhood ;
	
	private String town;
	
	private Province province;
	
	private String zip;
	
	@ManyToOne(optional = false)
	private User addressUser;
	
	private Boolean active;
	
	@OneToMany(mappedBy = "customerAddress")
	private final List<Order> orders = new ArrayList<>();
	
}
