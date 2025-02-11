package com.blog.blogApp.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogApp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
