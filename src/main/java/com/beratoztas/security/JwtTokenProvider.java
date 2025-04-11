package com.beratoztas.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${e-commerce.app.secret}")
	private String APP_SECRET;

	@Value("${e-commerceapp.expires.in}")
	private long EXPIRES_IN;

	public String generateJwtToken(JwtUserDetails userDetails) {

		Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
		return Jwts.builder().setSubject(Long.toString(userDetails.getId())).setIssuedAt(new Date())
				.setExpiration(expireDate).signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	public Claims getClaims(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		return claims;
	}

	public <T> T exportToken(String token, Function<Claims, T> claimsFunc) {
		Claims claims = getClaims(token);
		return claimsFunc.apply(claims);
	}

	public Long getUserIdFromToken(String token) {
		return exportToken(token, claims -> Long.parseLong(claims.getSubject()));
	}

	public boolean validateToken(String token) {
		try {
			Claims claims = getClaims(token);
			return !isTokenExpired(claims);
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private boolean isTokenExpired(Claims claims) {
		Date expiration = claims.getExpiration();
		return expiration.before(new Date());
	}

	public Key getKey() {
		return Keys.hmacShaKeyFor(APP_SECRET.getBytes(StandardCharsets.UTF_8));
	}

}
