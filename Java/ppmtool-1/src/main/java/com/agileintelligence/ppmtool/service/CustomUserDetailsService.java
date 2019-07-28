package com.agileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agileintelligence.ppmtool.domain.User;
import com.agileintelligence.ppmtool.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByUsername(username);
		if(user==null) {
			 new UsernameNotFoundException("User is not foung with name "+username);
		}
		return user;
	}
	
	@Transactional
	public User loadUserById(Long id) {
		
		User user= userRepo.getById(id);
		if(user!=null) {
			return user;
		}
		throw new UsernameNotFoundException("No user of that id");
		
		}

}
