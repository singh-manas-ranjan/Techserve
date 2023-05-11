package com.techserve.payload.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDtoRequest {

	@NotBlank(message = "*required field")
	@Size(min = 3, max = 20)
	private String firstname;
	
	@NotBlank(message = "*required field")
	@Size(min = 3, max = 20)
	private String lastname;
	
	@Email(regexp = "^[a-z0-9+_.-]+@[gmail|yahoo|zoho]+.com", message = "only gmail,zoho & yahoo is accepted.")
	private String email;
	
	@NotBlank(message = "*required field")
	@Size(min = 5, max = 20)
	private String username;
	
	@NotBlank(message = "*required field")
	@Size(min = 8, max = 20)
	private String password;
	
}
