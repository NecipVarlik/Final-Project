package com.necipvarlik.final_project.service.abstracts;

import java.util.List;

import com.necipvarlik.final_project.entities.Address;

public interface IAddressService extends CRUDService<Address> {

	List<Address> getByUserId(Integer id);
}
