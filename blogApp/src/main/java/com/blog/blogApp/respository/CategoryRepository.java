package com.blog.blogApp.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogApp.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
