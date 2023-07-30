package com.necipvarlik.final_project.service.abstracts;

import com.necipvarlik.final_project.entities.Cart;
import com.necipvarlik.final_project.exceptions.CartNotFoundException;

public interface ICartService extends CRUDService<Cart> {

	Cart getByUserId(Integer id) throws CartNotFoundException;
	
}
