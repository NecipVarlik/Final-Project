package com.necipvarlik.final_project.service.concretes;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.dto.MyUserPrincipal;
import com.necipvarlik.final_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService{

	private final UserRepository repo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repo
				.findByUsername(username)
				.map(MyUserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found."));
	}
	
	
	

}
