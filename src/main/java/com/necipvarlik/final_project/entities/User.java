package com.necipvarlik.final_project.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false , unique = true)
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String surname;
	
	@Email
	@NotBlank
	private String email;
	
	private Boolean enabled;
	
	private LocalDateTime insertDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id" )
			)
	private Collection<Role> roleList;
	
	@OneToMany(mappedBy = "addressUser")
	private final List<Address> addresses = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "seller")
	private final List<Book> books = new ArrayList<>();
	
	@OneToOne(mappedBy = "customer" , cascade = CascadeType.PERSIST)
	private Cart cart;

	@OneToMany(mappedBy = "customer")
	private final List<Order> customerOrder = new ArrayList<>();
	
	@OneToMany(mappedBy = "seller")
	private final List<Order> sellerOrder = new ArrayList<>();
}
