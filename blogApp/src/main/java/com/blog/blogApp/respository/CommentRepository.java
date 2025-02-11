package com.blog.blogApp.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogApp.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
