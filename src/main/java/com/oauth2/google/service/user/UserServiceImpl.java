package com.oauth2.google.service.user;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.dto.SignUpRequest;
import com.oauth2.google.dto.UserDto;
import com.oauth2.google.exception.OAuth2AuthenticationProcessingException;
import com.oauth2.google.exception.UserAlreadyExistAuthenticationException;
import com.oauth2.google.model.user.Role;
import com.oauth2.google.model.user.RoleType;
import com.oauth2.google.model.user.User;
import com.oauth2.google.repository.role.RoleRepository;
import com.oauth2.google.repository.user.UserRepository;
import com.oauth2.google.util.Utils;

/**
 * User service implementation
 * 
 * @author Ines heni
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setCreatedDate(now);
		user.setModifiedDate(now);
		user = userRepository.save(user);
		return user;
	}


	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes
			, OidcIdToken idToken, OidcUserInfo userInfo) {
		
		String  name = null;
		String  email = null;
		String  id = null;
		for(Map.Entry<String, Object> entry : attributes.entrySet()) {
			if(entry.getKey().equals("name"))
				name = (String) entry.getValue();
			if(entry.getKey().equals("email"))
				email = (String) entry.getValue();
			if(entry.getKey().equals("sub"))
				id = (String) entry.getValue();
		}
		if (ObjectUtils.isEmpty(name)) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (ObjectUtils.isEmpty(email)) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, id, name, email);
		User user = findUserByEmail(email);
		user = user != null ? updateExistingUser(user, name) : registerNewUser(userDetails);
		return LocalUser.create(user, attributes, idToken, userInfo);
	}
	
	@Override
	public void addUserRole(UserDto userDto) {
		User user = findUserById(userDto.getId()).get();
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(userDto.getRole()));
		user.setRoles(roles);
		userRepository.save(user);
	}

	private User updateExistingUser(User existingUser, String name) {
		existingUser.setDisplayName(name);
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, String id, String name, String email) {
		return SignUpRequest.getBuilder()
				.addProviderUserID(id)
				.addDisplayName(name)
				.addEmail(email)
				.addSocialProvider(Utils.toSocialProvider(registrationId))
				.addPassword("uniquepassword").build();
	}

	
	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setId(2L);
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(formDTO.getPassword()));
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(RoleType.ROLE_CLIENT));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user.setProviderUserId(formDTO.getProviderUserId());
		return user;
	}
}
