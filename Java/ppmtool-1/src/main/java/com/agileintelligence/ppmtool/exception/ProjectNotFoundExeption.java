package com.agileintelligence.ppmtool.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProjectNotFoundExeption extends RuntimeException{
	public ProjectNotFoundExeption(String message) {
		super(message);
	}
	

}
