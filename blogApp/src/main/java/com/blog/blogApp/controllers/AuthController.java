package com.blog.blogApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogApp.config.JwtUtil;
import com.blog.blogApp.dtos.UserAuthDTO;
import com.blog.blogApp.dtos.UserDTO;
import com.blog.blogApp.services.UserService;

@RestController
@RequestMapping
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtUtil jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO user) {
		System.out.println("in register");
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UserAuthDTO user) {
		System.out.println(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		System.out.println("Before auth" + auth);
		auth = authManager.authenticate(auth);
		System.out.println("After auth" + auth);
		String token = jwtUtils.createToken(auth);
		return ResponseEntity.ok(token);

	}

}
