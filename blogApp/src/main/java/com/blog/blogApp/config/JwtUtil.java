package com.blog.blogApp.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	@Value("${jwt.token.expiration.millis}")
	public long jwtExpiration;
	@Value("${jwt.token.secret}")
	public String jwtSecret;
	private Key jwtKey;

	@PostConstruct
	public void init() {
		jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public String createToken(Authentication auth) {
		System.out.println(auth);
//		User user = (User) auth.getPrincipal();
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String email = userDetails.getUsername();
		String role = userDetails.getAuthorities().iterator().next().getAuthority();
//		String subject = "" + user.getId();
//		Map<String, Object> map = new HashMap<>();
//		map.put("role", user.getRole());
//		map.put("email", user.getEmail());
		Map<String, Object> map = new HashMap<>();
		map.put("role", role);
		map.put("email", email);

		return Jwts.builder().addClaims(map).setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(jwtKey, SignatureAlgorithm.HS256).compact();
	}

	public Map<String, Object> validateToken(String token) {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(jwtKey).build();
		Claims claims = parser.parseClaimsJws(token).getBody();
		Map<String, Object> map = new HashMap<>();
		String role = (String) claims.get("role");
		if (!role.startsWith("ROLE_")) {
			role = "ROLE_" + role;
		}
		map.put("role", role);
		map.put("email", claims.get("email"));
		map.put("userId", claims.getSubject());
		return map;
	}

}
