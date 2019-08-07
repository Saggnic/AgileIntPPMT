package com.agileintelligence.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agileintellegence.ppmtool.payload.JWTLoginSuccessResponse;
import com.agileintellegence.ppmtool.payload.LoginRequest;
import com.agileintelligence.ppmtool.domain.User;
import com.agileintelligence.ppmtool.security.JwtTokenProvider;
import com.agileintelligence.ppmtool.service.MapValidationErrorService;
import com.agileintelligence.ppmtool.security.SecurityConstants;
import com.agileintelligence.ppmtool.service.UserService;
import com.agileintelligence.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationmanager;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
			BindingResult bindingResult) {

		ResponseEntity<?> errorMap = mapValidationService.mapValidatinService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}

		Authentication authentication = authenticationmanager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=SecurityConstants.TOKEN_PREFIX +jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
		
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

		// validate passwords
		userValidator.validate(user, bindingResult);

		ResponseEntity<?> errorMap = mapValidationService.mapValidatinService(bindingResult);
		if (errorMap != null) {
			return errorMap;
		}

		user.setConfirmPassword("");
		User newUser = userService.saveUser(user);

		return new ResponseEntity<User>(newUser, HttpStatus.OK);

	}

}
