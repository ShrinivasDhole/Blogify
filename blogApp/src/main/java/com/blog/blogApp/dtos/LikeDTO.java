package com.blog.blogApp.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;
}
