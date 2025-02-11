package com.blog.blogApp.services;

import com.blog.blogApp.dtos.TagDTO;
import com.blog.blogApp.models.Tag;
import com.blog.blogApp.respository.TagRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagDTO.class))
                .collect(Collectors.toList());
    }

    public TagDTO getTagById(Long id) {
        return tagRepository.findById(id)
                .map(tag -> modelMapper.map(tag, TagDTO.class))
                .orElse(null);
    }

    public TagDTO createTag(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        tag = tagRepository.save(tag);
        return modelMapper.map(tag, TagDTO.class);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
