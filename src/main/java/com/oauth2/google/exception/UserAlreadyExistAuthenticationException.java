package com.oauth2.google.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author ih
 *
 */
public class UserAlreadyExistAuthenticationException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistAuthenticationException(final String msg) {
        super(msg);
    }

}
