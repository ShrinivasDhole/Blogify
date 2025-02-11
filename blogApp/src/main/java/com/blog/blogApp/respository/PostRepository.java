package com.blog.blogApp.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogApp.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
