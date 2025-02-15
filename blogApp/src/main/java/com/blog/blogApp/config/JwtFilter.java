package com.blog.blogApp.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.blogApp.models.User;
import com.blog.blogApp.respository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		boolean validHeader = authHeader != null && authHeader.startsWith("Bearer");

		Authentication auth = null;

		if (validHeader) {
			String token = authHeader.replace("Bearer", "").trim();
			Map<String, Object> subject = jwtUtils.validateToken(token);
			String email = (String) (subject.get("email"));
			String role = (String) (subject.get("role"));

			User user = userRepository.findByEmail(email);

			if (user != null) {
				List<GrantedAuthority> authorities = java.util.Collections
						.singletonList(new SimpleGrantedAuthority(role));
				auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
			}

		}

		if (auth != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}

}
