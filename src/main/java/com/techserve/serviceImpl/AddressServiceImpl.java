package com.techserve.serviceImpl;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techserve.CustomExceptions.ResourceNotFoundException;
import com.techserve.entities.Address;
import com.techserve.entities.User;
import com.techserve.payload.user.AddressDto;
import com.techserve.repository.AddressRepository;
import com.techserve.repository.UserRepository;
import com.techserve.service.AddressService;
@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public String createAddress(AddressDto addressDto, Principal principal) {
		
		User user = userRepo.findByEmail(principal.getName()).orElse(null);
		
		Address address = mapper.map(addressDto, Address.class);
		address.setUser(user);
		Address savedAddress = addressRepo.save(address);
		
		if(savedAddress != null) {
			return "Address Saved Successfully";
		}
		else {
			return "OOPS! Something Went Wrong, Pls Try Again Later";
		}
		
	}

	@Override
	public String updateAddress(Integer addressId, AddressDto addressDto, Principal principal) {
		if(addressRepo.findById(addressId).get().getUser().getUsername().equals(principal.getName())){
			
			Address oldAd = addressRepo.findById(addressId).get();
			
			Address newAd = mapper.map(addressDto, Address.class);
			newAd.setId(oldAd.getId());
			newAd.setUser(oldAd.getUser());
			addressRepo.save(newAd);
			return "Address Updated Successfully";
		}
		else {
			return "Something Went Wrong";
		}
	}

	@Override
	public AddressDto getAddressById(Integer addressId,Principal principal) {
		if(addressRepo.findById(addressId).get().getUser().getUsername().equals(principal.getName())){
			Address ad = addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address", "Address_Id", addressId));
			return mapper.map(ad, AddressDto.class);
		}
		else {
			throw new ResourceNotFoundException("Address", "Address_Id", addressId);
		}
	}

	@Override
	public Set<AddressDto> getAddressByUser(User user) {
		Set<Address> returnedAd = addressRepo.findAllByUser(user);
		Set<AddressDto> adDto = returnedAd.stream()
				.map(ad -> mapper.map(ad, AddressDto.class))
				.collect(Collectors.toSet());
		return adDto;
	}

}
