package com.blog.blogApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "post_tags", 
            joinColumns = @JoinColumn(name = "tags"), 
            inverseJoinColumns = @JoinColumn(name = "posts")
        )
    private List<Post> posts = new ArrayList<>();
}
