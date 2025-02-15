package com.blog.blogApp.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDTO {
	private Long id;
	private String username;
	private String email;
	private String password; // Ensure password is hashed before saving
	private String bio;
	private String role;
//    private byte[] profileImage; // Changed to byte array to store image as binary data
}
