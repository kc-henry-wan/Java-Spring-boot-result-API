package com.hope.apiapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.repository.PharmacistRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private PharmacistRepository userRepository; // Replace with your user repository

	@Override
	public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Fetch the user from the database
		Pharmacist user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("RNF-U001"));
		return new CustomUserDetails(user); // Replace with your UserDetails implementation
	}
}
