package com.techserve.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techserve.entities.Address;
import com.techserve.entities.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	Set<Address> findAllByUser(User user);
}
