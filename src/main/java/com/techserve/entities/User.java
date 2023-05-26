package com.techserve.entities;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 20)
	private String firstname;

	@Column(nullable = false, length = 20)
	private String lastname;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	@Size(min = 5, max = 20)
	private String username;
	
	@Column(nullable = false)
	@Size(min = 8, max = 12)
	private String password;
	
	@OneToMany(mappedBy = "user")
	private Set<Address> address = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id") )
	@Column(nullable = false)
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private Set<Product> product;
	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean accountNonExpired = true;
	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean accountNonLocked = true;
	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean credentialsNonExpired = true;
	
	@Column(nullable=false, columnDefinition="boolean default true")
	private boolean enabled = true;
	
	private String resetPasswordToken;
	
	private String otp;
	
	private Date otpExpiration;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

}
