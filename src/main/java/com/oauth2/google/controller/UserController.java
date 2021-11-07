package com.oauth2.google.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.google.dto.UserDto;
import com.oauth2.google.model.user.User;
import com.oauth2.google.service.user.UserService;

/**
 * User controller class
 * 
 * @author Ines Heni
 *
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;

	@GetMapping("/find")
	//@PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> findByUserName() {
		Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			  username = ((UserDetails)principal).getUsername();
			} else {
			  username = principal.toString();
			}
        User user = userService.findUserByEmail(username);
        return ResponseEntity.ok(user);
    }
	
	@PostMapping("/save")
	public ResponseEntity<?> addUserRole(@RequestBody UserDto user) {
		userService.addUserRole(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}