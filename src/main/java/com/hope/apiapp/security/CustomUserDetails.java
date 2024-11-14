package com.hope.apiapp.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hope.apiapp.model.Pharmacist;

public class CustomUserDetails implements UserDetails {
	private Pharmacist user;

	public CustomUserDetails(Pharmacist user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Return authorities based on user roles
//		return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
//				.collect(Collectors.toList());

//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
//		authorities.add(authority);
//
//		return authorities;
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

	}

	public Long getUserId() {
		return user.getPharmacistId();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	public String getStatus() {
		return user.getStatus();
	}

	public String getRole() {
		return user.getRole();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // Implement as needed
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // Implement as needed
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Implement as needed
	}

	@Override
	public boolean isEnabled() {
		return true; // Implement as needed
	}
}
