package com.oauth2.google.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String role;

}
