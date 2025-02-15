package com.blog.blogApp.services;

import com.blog.blogApp.dtos.LikeDTO;
import com.blog.blogApp.models.Like;
import com.blog.blogApp.respository.LikeRepository;
import com.blog.blogApp.respository.PostRepository;
import com.blog.blogApp.respository.UserRepository;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<LikeDTO> getAllLikes() {
        return likeRepository.findAll().stream()
                .map(like -> modelMapper.map(like, LikeDTO.class))
                .collect(Collectors.toList());
    }

    public LikeDTO getLikeById(Long id) {
        return likeRepository.findById(id)
                .map(like -> modelMapper.map(like, LikeDTO.class))
                .orElse(null);
    }

    public LikeDTO likePost(Long postId, Long userId) {
        Like like = new Like();
        like.setPost(postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found")));
        like.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        like = likeRepository.save(like);
        return modelMapper.map(like, LikeDTO.class);
    }

    public void unlikePost(Long postId, Long userId) {
        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }
}
