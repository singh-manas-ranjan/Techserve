package com.techserve.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Bean
		UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
		SecurityFilterChain securityFilterchain(HttpSecurity http)  throws Exception{
			return http
					.authorizeHttpRequests()
					.requestMatchers("/**").permitAll()
					.and()
					.authorizeHttpRequests()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login").permitAll()
					.loginProcessingUrl("/signin")
					.defaultSuccessUrl("/default")
					.and()
					.logout().permitAll()
					.and()
					.build();
	}
	
	@Bean
		PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	@Bean
		AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
