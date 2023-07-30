package com.necipvarlik.final_project.controllers;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.entities.Order;
import com.necipvarlik.final_project.exceptions.CartNotFoundException;
import com.necipvarlik.final_project.service.abstracts.IAddressService;
import com.necipvarlik.final_project.service.abstracts.IBookService;
import com.necipvarlik.final_project.service.abstracts.ICartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final ICartService cartService;

	private final IBookService bookService;

	private final IAddressService addressService;

	@GetMapping("/usercart")
	public String getCart(Model model) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Cart cart = null;
		int userId = user.getUser().getId();
		
		try {
			
			cart = cartService.getByUserId(userId);
		
		} catch (CartNotFoundException e) {

			e.printStackTrace();
		} 
		if(!(cart.getBooks().isEmpty())) {
		
		
		model.addAttribute("addresses", addressService.getByUserId(userId));
		}
		model.addAttribute("cart",cart );
		model.addAttribute("order", new Order());
		return "cart/usercart";
	}

	@GetMapping("/deletefromcart/{id}")
	public String deleteBook(@PathVariable Integer id, HttpSession session) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Cart cart = user.getUser().getCart();

		Book removedBook = null;
		try {
			removedBook = bookService.getById(id);
			cart.removeBook(removedBook);
		} catch (Exception e1) {
			session.setAttribute("msg", e1.getMessage());
			return "redirect:/usercart";
		}
		
		try {
			cartService.update(cart);
		} catch (Exception e) {
			
			session.setAttribute("msg", e.getMessage());
		}

		session.setAttribute("msg", "Ürün sepetten kaldırıldı.");
		return "redirect:/usercart";
	}
	
	@GetMapping("/addtocart/{id}") 
	public String addToCart(@PathVariable Integer id , HttpSession session) {
		
		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Cart cart = user.getUser().getCart();
		
		try {
			cart.addBook(bookService.getById(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cartService.update(cart);
		} catch (Exception e) {
			session.setAttribute("msg", "Sepete eklenemedi.");
		}
		
		session.setAttribute("msg","Sepete eklendi" );
		
		return "redirect:/";
	}

}
