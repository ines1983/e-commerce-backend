package com.oauth2.google.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.google.dto.JwtAuthenticationResponse;
import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.dto.LoginRequest;
import com.oauth2.google.security.jwt.TokenProvider;
import com.oauth2.google.util.Utils;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;
	
	/*@Autowired
    private ConsumerTokenServices tokenService;*/


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		LocalUser localUser = (LocalUser) authentication.getPrincipal();
		String jwt = tokenProvider.generateJwtToken(localUser, true);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, true, Utils.buildUserInfo(localUser)));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> revoke(HttpServletRequest request, HttpServletResponse response) {
	    /*try {
	        String authorization = request.getHeader("Authorization");
	        if (authorization != null && authorization.contains("Bearer")) {
	        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        	
	            String tokenValue = authorization.replace("Bearer", "").trim();
	            new SecurityContextLogoutHandler().logout(request, null, null);
	            LocalUser oidcUser = (LocalUser) auth.getPrincipal();
	            tokenService.revokeToken(tokenValue);
	            
	            try {
	                //sending back to client app
	                response.sendRedirect(request.getHeader("referer"));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
	            tokenStore.removeAccessToken(accessToken);

	            //OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(tokenValue);
	            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
	            tokenStore.removeRefreshToken(refreshToken);
	        }
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Invalid access token");
	    }*/

	    return ResponseEntity.ok().body("ok");
	}
}