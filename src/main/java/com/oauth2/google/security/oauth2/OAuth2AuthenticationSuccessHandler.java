package com.oauth2.google.security.oauth2;


import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.oauth2.google.dto.LocalUser;
import com.oauth2.google.exception.BadRequestException;
import com.oauth2.google.security.jwt.TokenProvider;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {	
	
	@Value("${redirect.uri}")
	private String redirectUri;
	
	@Value("${app.auth.tokenSecret}")
	private String tokenSecret;

	@Value("${app.auth.tokenExpirationMsec}")
	private String tokenExpirationMsec;
	
	@Value("${app.oauth2.authorizedRedirectUris}")
	private String authorizedRedirectUris;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (!isAuthorizedRedirectUri(redirectUri)) {
			throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri != null ? redirectUri : getDefaultTargetUrl();
		LocalUser user = (LocalUser) authentication.getPrincipal();
		String token = tokenProvider.generateJwtToken(user, true);

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);
		
		URI authorizedURI = URI.create(authorizedRedirectUris);
		if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort()) {
			return true;
		}
		return false;
	}
}