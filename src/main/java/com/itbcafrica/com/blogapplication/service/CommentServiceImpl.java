package com.itbcafrica.com.blogapplication.service;

import com.itbcafrica.com.blogapplication.converter.CommentConverter;
import com.itbcafrica.com.blogapplication.entity.Comment;
import com.itbcafrica.com.blogapplication.entity.Post;
import com.itbcafrica.com.blogapplication.exception.BlogAPIException;
import com.itbcafrica.com.blogapplication.exception.ResourceNotFoundException;
import com.itbcafrica.com.blogapplication.payload.CommentDto;
import com.itbcafrica.com.blogapplication.repository.CommentRepository;
import com.itbcafrica.com.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentConverter converter;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = converter.convertDtoToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return converter.convertEntityToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllComment(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment ->
                converter.convertEntityToDto(comment)
        ).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post by Id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }


        return converter.convertEntityToDto(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return converter.convertEntityToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        commentRepository.delete(comment);
    }
}
