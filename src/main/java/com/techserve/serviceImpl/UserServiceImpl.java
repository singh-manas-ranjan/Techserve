package com.techserve.serviceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techserve.entities.User;
import com.techserve.payload.user.UserDtoRequest;
import com.techserve.payload.user.UserDtoResponse;
import com.techserve.payload.user.UserProfileDto;
import com.techserve.repository.UserRepository;
import com.techserve.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public String createUser(UserDtoRequest userDtoRequest) {
		User user = mapper.map(userDtoRequest, User.class);
		User savedUser = userRepo.save(user);
		if(savedUser != null) {
			return "Registered Successfully";
		}
		return "Something Went Wrong!";
	}

	@Override
	public UserDtoResponse getUserById(Integer userId) {
		return mapper.map(userRepo.findById(userId).orElse(null), UserDtoResponse.class);
	}

	@Override
	public UserDtoResponse getUserByUsername(String username) {
		return mapper.map(userRepo.findByUsername(username), UserDtoResponse.class);
	}

	@Override
	public Set<UserDtoResponse> getAllUsers() {
		List<User> allUsers = userRepo.findAll();
		return allUsers.stream().map(user -> mapper.map(user, UserDtoResponse.class)).collect(Collectors.toSet());
	}

	@Override
	public UserDtoResponse updateUser(UserDtoResponse userDtoResponse, Principal principal) {
		
		UserDtoRequest userDtoRequest = mapper.map(userDtoResponse, UserDtoRequest.class);
		
		User returnedUser = userRepo.findByUsername(principal.getName());
		returnedUser.setFirstname(userDtoRequest.getFirstname());
		returnedUser.setLastname(userDtoRequest.getLastname());
		
		return mapper.map(userRepo.save(returnedUser),UserDtoResponse.class);
	}

	@Override
	public void deleteUserById(Integer id) {
		User user = userRepo.findById(id).orElse(null);
		if(user != null) {
			userRepo.delete(user);
		}
	}

	@Override
	public UserProfileDto getProfile(String username) {
		User returnedUser = userRepo.findByUsername(username);
		UserProfileDto dto = mapper.map(returnedUser, UserProfileDto.class);
		return dto;
	}

}
