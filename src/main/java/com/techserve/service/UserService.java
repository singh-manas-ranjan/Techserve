package com.techserve.service;

import java.security.Principal;
import java.util.Set;

import com.techserve.payload.user.UserDtoRequest;
import com.techserve.payload.user.UserDtoResponse;
import com.techserve.payload.user.UserProfileDto;

public interface UserService {

		String createUser(UserDtoRequest userDtoRequest);
		
		UserDtoResponse getUserById(Integer userId);
		
		UserDtoResponse getUserByUsername(String username);
		
		Set<UserDtoResponse> getAllUsers();
		
		UserDtoResponse updateUser(UserDtoResponse userDtoResponse, Principal principal);
		
		void deleteUserById(Integer id);
		
		UserProfileDto getProfile(String username);
		
}
