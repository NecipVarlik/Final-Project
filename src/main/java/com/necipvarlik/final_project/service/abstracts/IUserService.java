package com.necipvarlik.final_project.service.abstracts;

import javax.validation.Valid;


import com.necipvarlik.final_project.entities.User;
import com.necipvarlik.final_project.exceptions.ExistingMailException;
import com.necipvarlik.final_project.exceptions.ExistingUserNameException;
import com.necipvarlik.final_project.exceptions.UserNotFoundException;

public interface IUserService extends CRUDService<User> {

	
}
