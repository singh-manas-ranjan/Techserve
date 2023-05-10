package com.techserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.techserve.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	UserDetails findByUsername(String username);
}
