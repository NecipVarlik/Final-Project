package com.necipvarlik.final_project.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.entities.Address;
import com.necipvarlik.final_project.service.abstracts.IAddressService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AddressController {

	private final IAddressService service;
	
	
	@GetMapping("/addaddress")
	public String pageAddress(Model model) {
		
		model.addAttribute("address", new Address());
		
		return "home/addressadding";
	}
	
	@PostMapping("/addaddress")
	public String newAddress (@ModelAttribute("address") Address a, HttpSession session ) {
		
		MyUserPrincipal user = (MyUserPrincipal) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		a.setAddressUser(user.getUser());
		try {
			
			service.add(a);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/userdetails";
	}
	
	@GetMapping("/addressedit/{id}")
	public String addressEditting(@PathVariable Integer id,HttpSession session,  Model model){
		
		try {
			
			model.addAttribute("address" , service.getById(id));
			
		} catch (Exception e) {
			
			session.setAttribute("msg", e.getMessage());
		}
		
		return "home/addresseditting";
	}
	
	@PostMapping("/addressedit/{id}")
	public String edit (@PathVariable Integer id, @ModelAttribute("address") Address a, HttpSession session) {
		
		try {
			
			service.update(a);
			
		} catch (Exception e) {
			
			session.setAttribute("msg", e.getMessage());
			}
		
		session.setAttribute("msg", "Adres başarıyla güncellendi.");
		
		return "home/addresseditting";
	}
	
	@GetMapping("/deleteaddress/{id}") 
	public String deleteAddress(@PathVariable Integer id, HttpSession session) {
		
		try {
			service.deleteById(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/userdetails";
	}
	
}
