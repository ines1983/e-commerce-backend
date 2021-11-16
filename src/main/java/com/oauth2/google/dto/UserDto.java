package com.oauth2.google.dto;

import java.io.Serializable;

import com.oauth2.google.model.user.RoleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private RoleType role;

}
