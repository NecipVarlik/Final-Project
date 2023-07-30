package com.necipvarlik.final_project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.necipvarlik.final_project.dto.EmailDetails;
import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.entities.Order;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.service.abstracts.EmailService;
import com.necipvarlik.final_project.service.abstracts.ICartService;
import com.necipvarlik.final_project.service.abstracts.IOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final IOrderService orderService;

	private final ICartService cartService;

	private final EmailService emailService;

	@PostMapping("/neworder")
	public String createOrder(@ModelAttribute("order") Order o, HttpSession session) {

		if (o.getCustomerAddress() == null) {

			session.setAttribute("msg", "Adres seçiniz.");
			return "redirect:/usercart";
		}
		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = user.getUser().getId();
		o.setCustomer(user.getUser());
		o.setStatus(false);
		Set<Book> bookList;
		Cart cart = null;
		try {
			cart = cartService.getByUserId(userId);
			bookList = cart.getBooks();

		} catch (Exception e1) {

			session.setAttribute("msg", e1.getMessage());

			return "redirect:/usercart";
		}
		// = o.getBooks();
		// List<User> sellerList = new ArrayList<>();
		// Set<User> sellerList = new HashSet<>();

		for (Book book : bookList) {
			o.addBook(book);
			o.setSeller(book.getSeller());
			// sellerList.add(book.getSeller());

		}

//		o.setSeller(sellerList);
//		int i = 0;

		bookList.clear();

		try {
			cartService.update(cart);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			orderService.add(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.setAttribute("msg", e.getMessage());
		}

		session.setAttribute("msg", "Siparişiniz alındı.");

		EmailDetails customerEmailDetails = new EmailDetails(o.getCustomer().getEmail(), "Siparişiniz bize ulaştı",
				String.format(
						"%s satıcısından vermiş olduğunuz sipariş bize ulaştı. Satıcının sipariş onay mailini bekleyiniz.",
						o.getSeller().getUsername()));

		emailService.send(customerEmailDetails);

		EmailDetails sellerEmailDetails = new EmailDetails(o.getSeller().getEmail(), "Sipariş alındı", String.format(
				"%s müşterisinin verdiği sipariş alınmıştır. Onaylamak için gelen siparişlerim bölümüne bakınız.",
				o.getCustomer().getUsername()));
		emailService.send(sellerEmailDetails);

		log.info("{} kullanıcı adlı müşteri {} kullanıcı adlı satıcıdan sipariş verdi.", o.getCustomer().getUsername(),
				o.getSeller().getUsername());

		return "redirect:/usercart";
	}

	@GetMapping("/sellerorders")
	public String sellerOrder(Model model, HttpSession session) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			model.addAttribute("orders", orderService.getBySeller(user.getUser().getId()));
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
		}

		return "order/sellerorder";

	}

	@GetMapping("cancelorder/{id}")
	public String deleteOrder(@PathVariable Integer id, HttpSession session) {

		try {
			orderService.deleteById(id);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
		}

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		log.info("{} satıcısı {} no'lu siparişi sildi.", user.getUsername(), id);
		session.setAttribute("msg", "Sipariş Başarıyla Silindi");
		return "redirect:/sellerorders";
	}

	@GetMapping("approveorder/{id}")
	public String approveOrder(@PathVariable Integer id, HttpSession session) {
		Order order = null;
		try {
			order = orderService.getById(id);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
		}

		order.setStatus(true);

		try {
			orderService.update(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EmailDetails customerEmailDetails = new EmailDetails(order.getCustomer().getEmail(), "Siparişiniz Onaylandı",
				String.format(
						"%s satıcısından vermiş olduğunuz sipariş bize ulaştı. Satıcının sipariş onay mailini bekleyiniz.",
						order.getSeller().getUsername()));

		emailService.send(customerEmailDetails);

		EmailDetails sellerEmailDetails = new EmailDetails(order.getSeller().getEmail(), "Siparişi Onayladınız", String.format(
				"%s müşterisinin verdiği siparişi onayladınız.",
				order.getCustomer().getUsername()));
		emailService.send(sellerEmailDetails);
		log.info("{} satıcısı {} no'lu siparişi onayladı.", order.getId());
		session.setAttribute("msg", "Sipariş onaylandı");
		return "redirect:/sellerorders";
	}

	@GetMapping("/customerorders")
	public String customerOrder(Model model, HttpSession session) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			model.addAttribute("orders", orderService.getByCustomer(user.getUser().getId()));
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
		}

		return "order/customerorder";
	}

	@GetMapping("/customercancel/{id}")
	public String customerDeleteOrder(@PathVariable Integer id, HttpSession session) {

		try {
			orderService.deleteById(id);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
		}

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		log.info("{} müşterisi {} no'lu siparişini iptal etti.", user.getUsername(), id);
		session.setAttribute("msg", "Sipariş Başarıyla Silindi");
		return "redirect:/sellerorders";
	}
}
