package com.blog.blogApp.services;

import com.blog.blogApp.dtos.UserDTO;
import com.blog.blogApp.models.User;
import com.blog.blogApp.respository.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    // Get a user by ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }

    // Create a new user (no password hashing)
    public UserDTO createUser(UserDTO userDTO) {
        // Convert DTO to entity
        User user = modelMapper.map(userDTO, User.class);

        // Save user and return DTO (no password encoding)
        user = userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    // Update an existing user (no password hashing)
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            // Update entity fields using ModelMapper and avoid overwriting null values
            modelMapper.map(userDTO, user);

            // Save user and return DTO
            user = userRepository.save(user);
            return modelMapper.map(user, UserDTO.class);
        }).orElse(null);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
