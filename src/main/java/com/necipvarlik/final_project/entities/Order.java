package com.necipvarlik.final_project.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
			name = "order_items",
			joinColumns = @JoinColumn(name = "order_id" , referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "product_id" , referencedColumnName = "id")
			)
	private Set<Book> books = new HashSet<>();
	
	@ManyToOne
	private User customer;
	
	
	@ManyToOne
	private User seller;
	
	private boolean status;
	
	@ManyToOne
	@NotNull
	private Address customerAddress;
	
	public void addBook(Book book) {
		this.books.add(book);
	}
	
}
