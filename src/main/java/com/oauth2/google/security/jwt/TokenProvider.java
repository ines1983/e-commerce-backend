package com.oauth2.google.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.oauth2.google.dto.LocalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Token provider class
 * 
 * @author Ines Heni
 *
 */
@Slf4j
@Component
public class TokenProvider {

	private static final String AUTHENTICATED = "authenticated";
	
	@Value("${app.auth.tokenSecret}")
	private String tokenSecret;

	@Value("${app.auth.tokenExpirationMsec}")
	private long tokenExpirationMsec;

	public String generateJwtToken(LocalUser userPrincipal, boolean authenticated) {
		return Jwts.builder()
				.setSubject(Long.toString(userPrincipal.getUser().getId()))
				.claim(AUTHENTICATED, authenticated)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + tokenExpirationMsec))
				.signWith(SignatureAlgorithm.HS512, tokenSecret)
				.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public Boolean isAuthenticated(String token) {
		Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
		return claims.get(AUTHENTICATED, Boolean.class);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}