package com.necipvarlik.final_project.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.service.abstracts.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

	private final IBookService service;

	private final IFileStorageService storage;

	
	@GetMapping("/newbookpage")
	public String newBook(Model model) {

		model.addAttribute("book", new Book());

		return "book/bookadding";
	}

	
	@PostMapping("/newbook")
	public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, HttpSession session,
			@RequestParam("img") MultipartFile img) {

		if (result.hasErrors()) {

			return "book/bookadding";
		}

		
		try {
			storage.save(img);
		} catch (IOException e) {

			session.setAttribute("msg", e.getMessage());
		}

		book.setImage(img.getOriginalFilename());
		book.setActive(true);
		MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		log.info("{} kullanıcısı {} tarihinde sisteme bir kitap kaydetti.", principal.getUsername(),
				LocalDateTime.now());

		book.setSeller(principal.getUser());
		book.setInsertDate(LocalDateTime.now());

		try {
			service.add(book);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
			return "book/bookadding";
		}

		session.setAttribute("msg", "Kitap kaydedildi.");
		return "book/bookadding";
	}

	@GetMapping("/booklist")
	public String bookList(Model model) {
		MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		model.addAttribute("books", service.getBySeller(principal.getUser()));

		return "book/booklist";
	}

	
	@GetMapping("/deletebook/{id}")
	public String deleteBook(@PathVariable("id") Integer id, HttpSession session) {

		try {
			service.deleteById(id);
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
		}

		return "redirect:/booklist";
	}

	
	@GetMapping("/updatebook/{id}")
	public String updateBook(@PathVariable("id") Integer id, Model model, HttpSession session) {

		try {
			model.addAttribute("book", service.getById(id));
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());

			return "book/booklist";
		}

		return "book/bookupdate";
	}

	
	@PostMapping("/updatebook/{id}")
	public String doUpdate(@ModelAttribute("book") Book book, BindingResult result, HttpSession session,
			@PathVariable Integer id , @RequestParam("img") MultipartFile img) {

		if (result.hasErrors()) {

			return "book/bookupdate";
		}

		
		if(!(img.getOriginalFilename().isEmpty())) {
		
			try {
				storage.save(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			book.setImage(img.getOriginalFilename());
		}
		
		try {
			service.update(book);
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
			return "book/bookupdate";
		}

		session.setAttribute("msg", "Güncelleme başarılı");
		return "book/bookupdate";
	}
}
