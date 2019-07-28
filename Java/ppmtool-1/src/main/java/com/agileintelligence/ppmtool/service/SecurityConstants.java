package com.agileintelligence.ppmtool.service;

public class SecurityConstants {

	
	public static final String SECRET ="SecretKeyToGenerateJWTs";
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	public static final long EXPIRATION_TIME=300_000;//its in milliseconds i.e 30 seconds
}
