package com.necipvarlik.final_project.service.abstracts;

import java.util.List;

import com.necipvarlik.final_project.entities.Order;
import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.exceptions.OrderNotFoundException;

public interface IOrderService extends CRUDService<Order> {

	List<Order> getBySeller(int id) throws Exception;
	
	List<Order> getByCustomer(int id);
}
