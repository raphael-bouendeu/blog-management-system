package com.itbcafrica.com.blogapplication.converter;

import com.itbcafrica.com.blogapplication.entity.Comment;
import com.itbcafrica.com.blogapplication.payload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    @Autowired
    private ModelMapper mapper;

    public Comment convertDtoToEntity(CommentDto dto) {
        Comment comment = mapper.map(dto, Comment.class);
       /* Comment comment = new Comment();
        comment.setBody(dto.getBody());
        comment.setEmail(dto.getEmail());
        comment.setName(dto.getName());

        if (dto.getId() != null) {
            comment.setId(dto.getId());
        }*/
        return comment;
    }

    public CommentDto convertEntityToDto(Comment comment) {
        CommentDto dto = mapper.map(comment, CommentDto.class);

      /*  CommentDto dto = new CommentDto();
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());
        dto.setId(comment.getId());*/
        return dto;
    }
}
