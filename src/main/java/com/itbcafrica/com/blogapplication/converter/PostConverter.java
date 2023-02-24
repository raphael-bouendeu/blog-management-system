package com.itbcafrica.com.blogapplication.converter;

import com.itbcafrica.com.blogapplication.entity.Post;
import com.itbcafrica.com.blogapplication.payload.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    @Autowired
    private ModelMapper mapper;

    public Post convertDtoToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
       /*
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        if (postDto.getId() != null) {
            post.setId(postDto.getId());
        }*/
        return post;
    }

    public PostDto convertEntityToDto(Post newPost) {
        PostDto dto = mapper.map(newPost, PostDto.class);

       /*PostDto dto = new PostDto();
        dto.setContent(newPost.getContent());
        dto.setDescription(newPost.getDescription());
        dto.setTitle(newPost.getTitle());
        dto.setId(newPost.getId());*/
        return dto;
    }
}
