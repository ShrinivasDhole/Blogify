package com.blog.blogApp.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password; // Ensure password is hashed before saving
    private String bio;
//    private byte[] profileImage; // Changed to byte array to store image as binary data
}
