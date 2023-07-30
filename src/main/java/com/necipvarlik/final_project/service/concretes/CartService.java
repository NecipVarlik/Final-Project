package com.necipvarlik.final_project.service.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.exceptions.CartNotFoundException;
import com.necipvarlik.final_project.repository.CartRepository;
import com.necipvarlik.final_project.service.abstracts.ICartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService implements ICartService {

	private final CartRepository repo;
	
	@Override
	public Cart add(Cart obj) {
		
		return repo.save(obj);
	}

	@Override
	public List<Cart> getAll() {
		
		
		return repo.findAll();
	}

	@Override
	public Cart getById(int id) throws Exception {
	
		Optional<Cart> cart = repo.findById(id);
		
		if(cart.isPresent()) {
			
			return cart.get();
		} else {
			
			throw new CartNotFoundException("Cart is not found");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cart update(Cart obj) throws Exception {
	
		Cart existingCart = getById(obj.getId());
		
		existingCart.setBooks(obj.getBooks());
		
		
		return repo.save(obj) ;
	}

	@Override
	public Cart getByUserId(Integer id) throws CartNotFoundException {
		
		Optional<Cart> c = repo.findByCustomerId(id);
		
		if(c.isPresent()) {
			
			return c.get();
		} else {
			
			throw new CartNotFoundException("Cart is not by this customer id.");
		}
		 
	}

}
