package com.necipvarlik.final_project.service.abstracts;

import java.util.List;

import com.necipvarlik.final_project.entities.Book;
import com.necipvarlik.final_project.entities.User;

public interface IBookService extends CRUDService<Book> {

	List<Book> getBySeller(User seller);
}
