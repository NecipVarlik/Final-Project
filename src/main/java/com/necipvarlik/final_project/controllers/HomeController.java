package com.necipvarlik.final_project.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.entities.Role;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.repository.RoleRepository;
import com.necipvarlik.final_project.service.abstracts.IAddressService;
import com.necipvarlik.final_project.service.abstracts.IBookService;
import com.necipvarlik.final_project.service.abstracts.ICartService;
import com.necipvarlik.final_project.service.abstracts.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

//	private final MyUserDetailsService detailservice;

	private final IUserService userService;

	private final IAddressService addressService;

	private final IBookService bookService;
	
	private final ICartService cartService;

	private final PasswordEncoder encoder;
	
	private final RoleRepository roleRepo;

	@GetMapping("/userdetails")
	public String userDetails(Model model) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = user.getUser().getId();

		try {
			model.addAttribute("user", userService.getById(userId));

		} catch (Exception e) {

			e.printStackTrace();

			return "redirect:/";
		}

		model.addAttribute("addresses", addressService.getByUserId(userId));

		return "home/userdetails";
	}


	@GetMapping("/useredit")
	public String userEditPage(Model model) {

		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			model.addAttribute("user", userService.getById(user.getUser().getId()));
		} catch (Exception e) {

			return "redirect:/";
		}

		return "home/usereditting";
	}


	@PostMapping("/useredit/{id}")
	public String userEdit(@ModelAttribute("user") User u, BindingResult result, HttpSession session,
			@PathVariable(name = "id") Integer id) {

		if (result.hasErrors()) {

			return "home/usereditting";
		}
		try {
			userService.update(u);
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
			return "home/usereditting";
		}

		session.setAttribute("msg", "Değişiklikler başarıyla kaydedildi.");
		return "home/usereditting";

	}
//	@PostMapping("/useredit")
//	public String userEdit(@ModelAttribute("user") User u 
//			,BindingResult result, HttpSession session ) {
//		
//		if(result.hasErrors()) {
//			return "home/userdetails";
//		}
//		try {
//			service.update(u);
//		} catch (Exception e) {
//			session.setAttribute("msg", e.getMessage());
//			return "home/userdetails";
//		}
//		return "redirect:/userdetails";
//	}

	@GetMapping("/login")
	public String login(boolean error, HttpSession session) {

		if (error == true) {
			session.setAttribute("msg", "Kullanıcı adı veya şifre hatalı.");
		}

		return "home/login";
	}

	@PostMapping("/dologin")
	public String doLogin() {

		return "redirect:/";
	}

	@GetMapping
	public String index(Model model) {

		model.addAttribute("books", bookService.getAll());

		return "home/index";
	}

	@GetMapping("/accessdenied")
	public String accessDenied() {

		return "home/accessdenied";
	}

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/userlist")
	public String userList(Model model) {

		model.addAttribute("users", userService.getAll());

		return "home/userlist";
	}

	@GetMapping("/registrate")
	public String registration(Model model) {

		model.addAttribute("user", new User());
		List<Role> roleList = new ArrayList<>();
		roleList.add(roleRepo.findByName("ROLE_CUSTOMER"));
		roleList.add(roleRepo.findByName("ROLE_SELLER"));
		model.addAttribute("roles" , roleList);

		return "home/registrate";
	}

	@PostMapping("/registrate")
	public String doRegistration(@Valid @ModelAttribute("user") User u, BindingResult result, HttpSession session) {

		if (result.hasErrors()) {
			return "home/registrate";
		}
		
		u.setRoleList(u.getRoleList());
		u.setInsertDate(LocalDateTime.now());
		u.setEnabled(true);
		u.setPassword(encoder.encode(u.getPassword()));
		Cart c = new Cart();
		c.setCustomer(u);
		u.setCart(c);
		

		try {
			userService.add(u);
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
			return "home/registrate";
		}

		return "redirect:/login";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id, HttpSession session) {

		try {
			userService.deleteById(id);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
		}
		return "redirect:/userlist";

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/edit/{id}")
	public String updateUser(@PathVariable Integer id, HttpSession session, Model model) {

		try {
			model.addAttribute("user", userService.getById(id));
			
			model.addAttribute("roles" , roleRepo.findAll());
		} catch (Exception e) {

			session.setAttribute("msg", e.getMessage());
		}

		return "home/edit";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/edit/{id}")
	public String doUpdateUser(@ModelAttribute("user") User u, BindingResult result, HttpSession session,
			@PathVariable Integer id) {

		if (result.hasErrors()) {
			return "home/edit";
		}
		try {
			userService.update(u);
		} catch (Exception e) {
			session.setAttribute("msg", e.getMessage());
			return "home/edit";
		}

		session.setAttribute("msg", "Değişiklikleriniz başarıyla kaydedildi.");

		return "home/edit";
	}

	
}
