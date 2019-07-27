package com.agileintelligence.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agileintelligence.ppmtool.domain.User;
import com.agileintelligence.ppmtool.service.MapValidationErrorService;
import com.agileintelligence.ppmtool.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MapValidationErrorService mapValidationService;

	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult bindingResult){
		
		//validate passwords
		
		ResponseEntity<?> errorMap = mapValidationService.mapValidatinService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		} 
		
		User newUser=userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser,HttpStatus.OK);
		
	}
	
	
}
