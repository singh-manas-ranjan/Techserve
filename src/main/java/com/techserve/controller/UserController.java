package com.techserve.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techserve.payload.user.AddressDto;
import com.techserve.payload.user.UserDtoRequest;
import com.techserve.payload.user.UserDtoResponse;
import com.techserve.payload.user.UserProfileDto;
import com.techserve.serviceImpl.AddressServiceImpl;
import com.techserve.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired 
	private UserServiceImpl userService;
	
	@Autowired
	private AddressServiceImpl addressService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/signup")
	public String processcreateUserForm(@Valid @ModelAttribute UserDtoRequest userDtoRequest,BindingResult result, RedirectAttributes redirectAttr ) {
		if(result.hasErrors()) {
			return "all/createUserForm";
		}
		System.out.println(userDtoRequest);
		String msg = userService.createUser(userDtoRequest);
		redirectAttr.addFlashAttribute("signupMsg", msg);
		return "redirect:/login";
	}
	
	@GetMapping("/profile")
	public String getProfile(Model model) {
		model.addAttribute("title", "Profile");
		return "user/profile";
	}
	
	@GetMapping("/update-profile")
	public String updateProfileForm(Model model, Principal principal) {
		model.addAttribute("title", "Update-Profile");
		UserProfileDto profileDto = userService.getProfile(principal.getName());
		model.addAttribute("profileDto", profileDto);
		return "user/updateProfileForm";
	}
	
	@PostMapping("/update-profile")
	public String processUpdateProfileForm(@Valid @ModelAttribute UserProfileDto userProfileDto, BindingResult result, RedirectAttributes redirAttr, Principal principal) {
		if(result.hasErrors()) {
			return "user/profile";
		}
		UserDtoResponse returnedUserDto = userService.updateUser(mapper.map(userProfileDto, UserDtoResponse.class), principal);
		if(returnedUserDto != null) {
			redirAttr.addFlashAttribute("profileUpdateMsg", "Profile Updated Successfully");
			return "redirect:/user/profile";
		}else {
			redirAttr.addFlashAttribute("profileUpddateMsg", "Something Went Wrong Try Again");
			return "redirect:/user/profile";
		}
		
	}
	
	@GetMapping("/address")
	public String getAllAddress() {
		return "users/allAddress";
	}
	
	@GetMapping("/update-address/{addressId}")
	public String updateAddressForm(@PathVariable Integer addressId, Principal principal,Model model) {
		AddressDto returnedAd = addressService.getAddressById(addressId, principal);
		model.addAttribute("title", "Update Address");
		model.addAttribute("addressDto", returnedAd);
		return "user/updateAddressForm";
	}
	
	@PostMapping("/update-address")
	public String processUpdateAddressForm(@Valid @ModelAttribute AddressDto addressDto, BindingResult result, RedirectAttributes redirAttr, Principal principal) {
		if(result.hasErrors()) {
			return "user/updateAddressForm";
		}
		String message = addressService.updateAddress(addressDto.getId(), addressDto, principal);
		redirAttr.addFlashAttribute("updateAddressMsg", message);
		return "redirect:/address";
	}
}
