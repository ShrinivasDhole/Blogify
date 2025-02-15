package com.blog.blogApp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.blogApp.dtos.UserDTO;
import com.blog.blogApp.models.User;
import com.blog.blogApp.respository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	// Get all users
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}

	// Get a user by ID
	public UserDTO getUserById(Long id) {
		return userRepository.findById(id).map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
	}

	@Transactional
	// Create a new user (no password hashing)
	public UserDTO createUser(UserDTO userDTO) {
		System.out.println(userDTO);
		User user = modelMapper.map(userDTO, User.class);
		System.out.println(user);
		if (user != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setId(null);
			System.out.println(user);
			user = userRepository.save(user);
		}
		return modelMapper.map(user, UserDTO.class);
	}

	// Update an existing user (no password hashing)
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		return userRepository.findById(id).map(user -> {
			// Update entity fields using ModelMapper and avoid overwriting null values
			modelMapper.map(userDTO, user);
			user.setId(null); // Ensure it's a new entity

			// Save user and return DTO
			user = userRepository.save(user);
			return modelMapper.map(user, UserDTO.class);
		}).orElse(null);
	}

	// Delete a user
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not exists");
		}
		return user;
	}
}
