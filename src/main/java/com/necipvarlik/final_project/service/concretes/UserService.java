package com.necipvarlik.final_project.service.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.entities.User;

import com.necipvarlik.final_project.exceptions.*;
import com.necipvarlik.final_project.repository.RoleRepository;
import com.necipvarlik.final_project.repository.UserRepository;
import com.necipvarlik.final_project.service.abstracts.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

	private final UserRepository repo;
	
	private final RoleRepository roleRepo;
	
	@Override
	public User add(User u) throws ExistingUserNameException, ExistingMailException {
		
		if(repo.existsByUsername(u.getUsername())) {
			throw new ExistingUserNameException("This user name is already taken.");
		}	
		
		if(repo.existsByEmail(u.getEmail())) {
			throw new ExistingMailException("This mail address is already used.");
		}
			
//		Collection<Role> role = new ArrayList<>();
//		
//		role.add(roleRepo.findByName("ROLE_USER"));
//		
//		u.setRoleList(role);
//		
//		Basket basket = new Basket(null, u, null)
		
		
		log.info("{} , {} sisteme kayıt oldu.", u.getUsername(), LocalDateTime.now());
		
		return repo.save(u);
	}
	
	@Override
	public List<User> getAll() {
	
		return repo.findAll();
	}
	
	@Override
	public User getById(int id) throws UserNotFoundException {
		
		Optional<User> user = repo.findById(id);
		
		if(user.isPresent()) {
			return user.get();
		} else {
			throw new UserNotFoundException("No user found.");
		}
		
	}
	
	@Override
	public void deleteById(int id) throws UserNotFoundException {
		
		User u = getById(id);
		u.setEnabled(false);
		
		log.info("{} , {} sistemde inaktif edildi.", u.getUsername(), LocalDateTime.now());
		
		repo.saveAndFlush(u);
		
	}
	@Override
	public User update(User u) throws UserNotFoundException, ExistingUserNameException, ExistingMailException {
		
		User existingUser = getById(u.getId());
		
		if(!(existingUser.getUsername().equals(u.getUsername()))) {
			if(repo.existsByUsername(u.getUsername())) {
				throw new ExistingUserNameException("This user name is already taken.");
			}	
		}
		
		if(!(existingUser.getEmail().equals(u.getEmail()))) {
			if(repo.existsByEmail(u.getEmail())) {
				throw new ExistingMailException("This mail address is already used.");
			}
		}
		existingUser.setEmail(u.getEmail());
		existingUser.setUsername(u.getUsername());
		existingUser.setName(u.getName());
		existingUser.setSurname(u.getSurname());
		existingUser.setRoleList(u.getRoleList());
		existingUser.setEnabled(u.getEnabled());
		return repo.saveAndFlush(existingUser);
	}

}
