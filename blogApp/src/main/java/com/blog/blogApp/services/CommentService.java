package com.blog.blogApp.services;

import com.blog.blogApp.dtos.CommentDTO;
import com.blog.blogApp.models.Comment;
import com.blog.blogApp.respository.CommentRepository;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .orElse(null);
    }

    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDTO.class);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        return commentRepository.findById(id).map(comment -> {
            modelMapper.map(commentDTO, comment);
            comment = commentRepository.save(comment);
            return modelMapper.map(comment, CommentDTO.class);
        }).orElse(null);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
