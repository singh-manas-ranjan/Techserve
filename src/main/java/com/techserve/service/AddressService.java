package com.techserve.service;

import java.security.Principal;
import java.util.Set;

import com.techserve.entities.User;
import com.techserve.payload.user.AddressDto;

public interface AddressService {
	
	String createAddress(AddressDto address, Principal principal);
	String updateAddress(Integer addressId, AddressDto address, Principal principal);
	AddressDto getAddressById(Integer addressId, Principal principal);
	Set<AddressDto> getAddressByUser(User user);
}
