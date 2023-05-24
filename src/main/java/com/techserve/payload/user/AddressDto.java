package com.techserve.payload.user;

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
public class AddressDto {

	private int id;
	private String houseNo;
	private String street;
	private String state;
	private String city;
	private String pincode;
}
