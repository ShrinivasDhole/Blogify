package com.blog.blogApp.controllers;

import com.blog.blogApp.dtos.LikeDTO;
import com.blog.blogApp.services.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<LikeDTO>> getAllLikes() {
        return ResponseEntity.ok(likeService.getAllLikes());
    }

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<LikeDTO> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.likePost(postId, userId));
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.unlikePost(postId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
