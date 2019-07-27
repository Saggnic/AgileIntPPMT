package com.agileintelligence.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameAlreadyExistsException extends RuntimeException {

	public UserNameAlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	 

}
