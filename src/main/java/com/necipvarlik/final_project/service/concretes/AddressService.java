package com.necipvarlik.final_project.service.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.entities.Address;
import com.necipvarlik.final_project.exceptions.AddressNotFoundException;
import com.necipvarlik.final_project.repository.AddressRepository;
import com.necipvarlik.final_project.service.abstracts.IAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

	private final AddressRepository repo;
	
	@Override
	public Address add(Address a) {
		
		a.setActive(true);
		
		return repo.save(a);
	}

	@Override
	public List<Address> getAll() {
		
		return repo.findAll();
	}

	@Override
	public Address getById(int id) throws AddressNotFoundException {
	
		Optional<Address> existAddress = repo.findById(id);
		
		if(existAddress.isPresent()) {
			
			return existAddress.get();
		} else {
			
			throw new AddressNotFoundException("Aranılan adres bulunamadı");
		}
	
	}

	@Override
	public void deleteById(int id) throws AddressNotFoundException {
	
	
		repo.deleteById(id); ;

	}

	@Override
	public Address update(Address a) throws AddressNotFoundException {
		
		Address existingAddress = getById(a.getId());
		
		existingAddress.setZip(a.getZip());
		existingAddress.setProvince(a.getProvince());
		existingAddress.setTown(a.getTown());
		existingAddress.setNeighborhood(a.getNeighborhood());
		existingAddress.setOpenAddress(a.getOpenAddress());
		existingAddress.setExplanation(a.getExplanation());
		existingAddress.setTitle(a.getTitle());
		
		return repo.saveAndFlush(existingAddress);
	}

	@Override
	public List<Address> getByUserId (Integer id) {
		
		return repo.findByUserId(id);
	}

}
