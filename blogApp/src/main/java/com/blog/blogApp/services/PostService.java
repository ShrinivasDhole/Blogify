package com.blog.blogApp.services;

import com.blog.blogApp.dtos.PostDTO;
import com.blog.blogApp.models.Post;
import com.blog.blogApp.models.User;
import com.blog.blogApp.respository.*;
import com.blog.blogApp.models.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> modelMapper.map(post, PostDTO.class))
                .orElse(null);
    }

    public PostDTO createPost(PostDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        post.setCategory(category);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        return postRepository.findById(id).map(post -> {
            modelMapper.map(postDTO, post);
            post = postRepository.save(post);
            return modelMapper.map(post, PostDTO.class);
        }).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

