package com.necipvarlik.final_project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.entities.Role;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.repository.RoleRepository;
import com.necipvarlik.final_project.repository.UserRepository;
import com.necipvarlik.final_project.service.concretes.UserService;

@SpringBootApplication
public class FinalProjectApplication {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.UK);
		
		SpringApplication.run(FinalProjectApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner (UserRepository repo , PasswordEncoder encoder, RoleRepository roleRepo ) {
//		
//		Role roleAdmin = new Role(1 , "ROLE_ADMIN" , "Admin");
//		Role roleMusteri = new Role(2, "ROLE_CUSTOMER" , "Müşteri");
//		Role roleSatici = new Role(3 , "ROLE_SELLER" , "Satıcı");
//	
//		
//		roleRepo.save(roleAdmin);
//		roleRepo.save(roleMusteri);
//		roleRepo.save(roleSatici);
//		
//		Set<Role> normalUser = new HashSet<>();
//		Set<Role> maxUser = new HashSet<>();
//		
//		
//		
//		normalUser.add(roleMusteri);
//		
//		maxUser.add(roleAdmin);
//		maxUser.add(roleMusteri);
//		maxUser.add(roleSatici);
//		
//		Cart adminCart = new Cart();
//		
//		Cart userCart = new Cart();
//		
//		
//		User user1 = new User(null, "necip99", encoder.encode("123") , "Necip", "Varlık",
//				"necipvarlik@hotmail.com", true, LocalDateTime.now(), maxUser, null);
//		
//		User user2 = new User(null, "rastalevent", encoder.encode("456") , "Levent", "Varlık",
//				"rastalevent@hotmail.com", true , LocalDateTime.now(),normalUser , null);
//		
//		adminCart.setCustomer(user1);
//		
//		user1.setCart(adminCart);
//		
//		userCart.setCustomer(user2);
//
//		user2.setCart(userCart);
//		
//		return args -> {
//			repo.save(user1);
//			repo.save(user2);
//		};
//	}
}
