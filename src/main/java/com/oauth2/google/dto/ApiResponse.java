package com.oauth2.google.dto;

import org.springframework.http.HttpStatus;

import lombok.Value;

@Value
public class ApiResponse {
	
	private HttpStatus status;
	
	private String message;
}