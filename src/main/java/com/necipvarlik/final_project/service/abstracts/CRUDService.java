package com.necipvarlik.final_project.service.abstracts;

import java.util.List;

import com.necipvarlik.final_project.exceptions.ExistingUserNameException;
import com.necipvarlik.final_project.exceptions.UserNotFoundException;

public interface CRUDService<T> {

	T add(T obj) throws Exception;
	List<T> getAll();
	T getById(int id) throws Exception;
	void deleteById(int id) throws Exception;
	T update(T obj) throws Exception;
	
}
