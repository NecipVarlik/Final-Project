package com.necipvarlik.final_project.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	private Integer id;
	
	@Column(unique = true , nullable = false , length = 45)
	private String name;
	
	@Column(length = 45)
	private String description;
	
	@ManyToMany(mappedBy = "roleList")
	private final Collection<User> userList = new HashSet<>();
	
	
	 @Override
	    public String toString() {
	        return this.name;
	    }
}
