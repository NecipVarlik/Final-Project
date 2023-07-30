package com.necipvarlik.final_project.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String author;
	
	@Column(columnDefinition = "TEXT")
	@NotEmpty
	private String details;
	
	@NotEmpty
	private String publisher;
	
	private String image;
	
	private Boolean active;
	
	private LocalDateTime insertDate;
	
	@ManyToOne(optional = false)
	private User seller;
	
	@ManyToMany(cascade = CascadeType.MERGE , mappedBy = "books")
	private Set<Cart> cart = new HashSet<>();
	
	
	@PreRemove
	public void removeBooks() {
		cart.forEach(cart -> {cart.getBooks().removeIf(e->e.equals(this));
			
		});
		orders.forEach(order -> {order.getBooks().removeIf(e->e.equals(this));
		
		});
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		 if (!(o instanceof Book)) return false;
		 Book book = (Book) o ;
		 
		return getId() != null && getId().equals(book.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(Book.class);
    }
	
	@ManyToMany(cascade = CascadeType.REMOVE , mappedBy = "books")
	private Set<Order> orders = new HashSet<>();
	

	
}
