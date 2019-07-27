package com.agileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agileintelligence.ppmtool.domain.User;
import com.agileintelligence.ppmtool.exception.UserNameAlreadyExistsException;
import com.agileintelligence.ppmtool.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User user) {
		
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			//User name has to be unique
			user.setUsername(user.getUsername());
			//match password with confirm password
			// do not persist or show confirm password
			
			return userRepository.save(user);
			
		}catch(Exception e) {
			throw new UserNameAlreadyExistsException("User:" +user.getUsername()+" already exists");
		}
		
		
		
	}
	
}
