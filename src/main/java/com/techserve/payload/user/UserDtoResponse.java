package com.techserve.payload.user;

import java.util.HashSet;
import java.util.Set;

import com.techserve.entities.Address;

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
public class UserDtoResponse {
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private Set<Address> address = new HashSet<>();
}
