package com.necipvarlik.final_project;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.repository.BookRepository;
import com.necipvarlik.final_project.service.abstracts.IBookService;
import com.necipvarlik.final_project.service.concretes.BookService;

public class BookServiceScenarios {

	private IBookService service;
	
	@Mock
	private BookRepository repo;
	
	@BeforeEach
	void initialUseCase() {
		service = new BookService(repo);
	}
	
	@Test
	void saveBook() {
		Book book = new Book(null, "Vahşetin Çağrısı", "Jack London",
				"Jack London'ın vahşi hayatı konu alan kitabı.",
				"İş Bankası Yayınları",null, true, null, null, null, null);
		
		try {
			service.add(book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		assertThat(book.getInsertDate()).isNotNull();
		
		}
}
