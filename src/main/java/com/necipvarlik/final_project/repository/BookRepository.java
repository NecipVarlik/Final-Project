package com.necipvarlik.final_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.entities.User;

public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findBySeller(User user);

}
