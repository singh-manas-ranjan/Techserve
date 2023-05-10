package com.techserve.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techserve.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT CASE WHEN COUNT (u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE email =: email")
	Boolean isUserExistsByEmail(@Param("email") String email);
	
	Optional<User> findByResetPasswordToken(String token);
	
	
}
