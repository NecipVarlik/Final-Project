package com.necipvarlik.final_project.service.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.entities.Order;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.exceptions.OrderNotFoundException;
import com.necipvarlik.final_project.repository.OrderRepository;
import com.necipvarlik.final_project.service.abstracts.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

	private final OrderRepository repo;
	
	
	@Override
	public Order add(Order obj) throws Exception {
		
		return repo.save(obj);
	}

	@Override
	public List<Order> getAll() {
		
		return repo.findAll();
	}

	@Override
	public Order getById(int id) throws Exception {
		
		Optional<Order> order = repo.findById(id);
		
		if(order.isPresent()) {
			return order.get();
		} else {
			throw new OrderNotFoundException("Order is not found.");
		}
		
	}

	@Override
	public void deleteById(int id) throws Exception {
		
		repo.delete(getById(id));
	}

	@Override
	public Order update(Order obj) throws Exception {
		
		Order existingOrder = getById(obj.getId());
		existingOrder.setStatus(obj.isStatus());
		return repo.save(obj);
	}

	@Override
	public List<Order> getBySeller(int id) throws OrderNotFoundException {
		
		return repo.findBySellerId(id);
	}

	@Override
	public List<Order> getByCustomer(int id) {
		
		
		return repo.findByCustomerId(id);
	}

}
