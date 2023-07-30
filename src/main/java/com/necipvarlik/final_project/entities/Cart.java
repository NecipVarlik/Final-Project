package com.necipvarlik.final_project.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	private User customer;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "cart_items",
			joinColumns = @JoinColumn(name = "cart_id" , referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "book_id" , referencedColumnName = "id")
			)
	private Set<Book> books = new HashSet<>();
	
	public void addBook (Book book) {
		
		this.books.add(book);
	}
	
	public void removeBook (Book book) {
		
		this.books.remove(book);
		book.getCart().remove(this);
		
	}
}
