package com.blog.blogApp.respository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.blog.blogApp.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

}
