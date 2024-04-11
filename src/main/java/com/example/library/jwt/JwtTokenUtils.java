package com.example.library.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

@Log4j2
@Service
public class JwtTokenUtils {

	private static String TOKEN_SECRET;
	private static Long ACCESS_TOKEN_VALIDITY;

	public JwtTokenUtils(@Value("${auth.secret}") String secret,@Value("${auth.cookies.jwtCookieName}") String jwtCookie, @Value("${auth.access.expiration}") Long accessValidity
			, @Value("${auth.refresh.expiration}") Long refreshValidity) {
		Assert.notNull(accessValidity, "Validity must not be null");
		Assert.hasText(secret, "Validity must not be null or empty");
		TOKEN_SECRET = secret;
		ACCESS_TOKEN_VALIDITY = accessValidity;

	}

	public  String generateToken(final String username ) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setIssuer("app-Service")
				.setExpiration(calcTokenExpirationDate())
				.claim("created", Calendar.getInstance().getTime())
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
	}


	private static Date calcTokenExpirationDate() {
		return new Date(System.currentTimeMillis() + ( ACCESS_TOKEN_VALIDITY) * 1000);
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	public boolean validateToken(String jwtToken, UserDetails userDetails) {
		final String username = extractUsername(jwtToken);
		return (username.equals(userDetails.getUsername()) && isTokenValid(jwtToken));
	}

	String extractUsername(String jwtToken) {
		return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(jwtToken).getBody().getSubject();
	}
}
