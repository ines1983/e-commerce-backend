package com.oauth2.google.dto;

/**
 * 
 * @author ih
 *
 */
public enum SocialProvider {

	 GOOGLE("google") ,
	 LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}

}
