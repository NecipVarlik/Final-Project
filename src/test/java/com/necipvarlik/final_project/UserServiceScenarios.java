package com.necipvarlik.final_project;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.service.abstracts.IUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceScenarios {

	@Autowired
	private IUserService service;
	
	@Test
	void savingUserTest() {
		
		User user2 = new User(null, "rastaman", "123" , "Levent", "Varlık",
				"rastaman@hotmail.com", true , LocalDateTime.now(),null , null);
		
		try {
			service.add(user2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertThat(user2.getId()).isNotNull();

	}
}
