package com.techserve.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techserve.payload.user.UserDtoRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("title", "Home");
		return "all/homepage";
	}
	
	@GetMapping("/login")
	public String login(Model  model, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "all/loginForm";
		}
		if(request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/home";
		}
		else {
			return "redirect:/users/home";
		}
	}
	
	@GetMapping("/default")
	public String defaultHomePage(HttpServletRequest request) {
		if(request.isUserInRole("ROLE_ADMIN")) {
			return "redirect:/admin/home";
		}
		else {
			return "redirect:/users/home";
		}
	}
	
	@GetMapping("/signup")
	public String createUserForm(Model model) {
		model.addAttribute("title", "SignUp");
		model.addAttribute("userDto", new UserDtoRequest());
		return "all/createUserForm";
	}
	
}
