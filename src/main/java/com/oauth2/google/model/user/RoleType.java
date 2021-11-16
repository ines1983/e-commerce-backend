package com.oauth2.google.model.user;

public enum RoleType {
	
	ROLE_ADMIN("ROLE_ADMIN"),
	
	ROLE_CLIENT("ROLE_CLIENT"),

	ROLE_SELLER("ROLE_SELLER");
	
	private String value;
		
	RoleType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
