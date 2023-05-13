package com.techserve.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techserve.payload.user.UserDtoRequest;
import com.techserve.payload.user.UserProfileDto;
import com.techserve.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired 
	private UserServiceImpl userService;
	
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
		UserProfileDto profile = userService.getProfile(principal.getName());
		model.addAttribute("profileDto", profile);
		return "user/updateProfileForm";
	}
}
