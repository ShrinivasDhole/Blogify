package com.blog.blogApp.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
//    private byte[] image;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId; // Only store user ID instead of full object
    private Long categoryId; // Only store category ID instead of full object
    private List<String> tags; // Convert tags to list of tag names
}
