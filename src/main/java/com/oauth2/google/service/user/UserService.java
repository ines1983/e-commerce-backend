package com.oauth2.google.service.user;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.dto.SignUpRequest;
import com.oauth2.google.dto.UserDto;
import com.oauth2.google.exception.UserAlreadyExistAuthenticationException;
import com.oauth2.google.model.user.User;

/**
 * User service
 * 
 * @author Heni Ines
 *
 */
public interface UserService {

	/**
	 * Register a new user
	 * 
	 * @param signUpRequest
	 * @return {@link User}
	 * @throws UserAlreadyExistAuthenticationException
	 */
	User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	/**
	 * Find user by email
	 * 
	 * @param email
	 * @return  {@link User}
	 */
	User findUserByEmail(String email);

	/**
	 * Find user by id
	 * 
	 * @param id
	 * @return
	 */
	Optional<User> findUserById(Long id);

	/**
	 * 
	 * @param registrationId
	 * @param attributes
	 * @param idToken
	 * @param userInfo
	 * @return
	 */
	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	/**
	 * Add role to the current user
	 * 
	 * @param user {@link UserDto}
	 */
	void addUserRole(UserDto user);
}
