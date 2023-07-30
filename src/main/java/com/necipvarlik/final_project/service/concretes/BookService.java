package com.necipvarlik.final_project.service.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.exceptions.BookNotFoundException;
import com.necipvarlik.final_project.repository.BookRepository;
import com.necipvarlik.final_project.service.abstracts.IBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService implements IBookService {

	private final BookRepository repo;

	@Override
	public Book add(Book obj) throws Exception {
//    	Test için
//		obj.setInsertDate(LocalDateTime.now());
//		
//		log.info("{} tarihinde sisteme bir kitap kaydedildi.",
//				LocalDateTime.now());

		return repo.save(obj);
	}

	@Override
	public List<Book> getAll() {

		return repo.findAll();
	}

	@Override
	public Book getById(int id) throws Exception {

		Optional<Book> book = repo.findById(id);

		if (book.isPresent()) {

			return book.get();
		} else {

			throw new BookNotFoundException("Book can not be found.");
		}

	}

	@Override
	public void deleteById(int id) throws Exception {

		Book book = getById(id);

		repo.delete(book);
	}

	@Override
	public Book update(Book obj) throws Exception {

		Book existingBook = getById(obj.getId());

		existingBook.setActive(obj.getActive());
		existingBook.setPublisher(obj.getPublisher());
		existingBook.setTitle(obj.getTitle());
		existingBook.setAuthor(obj.getAuthor());
		existingBook.setDetails(obj.getDetails());

		if (obj.getImage() != null) {
			existingBook.setImage(obj.getImage());
		}

		return repo.saveAndFlush(existingBook);
	}

	@Override
	public List<Book> getBySeller(User user) {
		
		return repo.findBySeller(user);
	}

}
