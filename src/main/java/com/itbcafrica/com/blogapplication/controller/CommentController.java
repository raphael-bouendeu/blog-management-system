package com.itbcafrica.com.blogapplication.controller;

import com.itbcafrica.com.blogapplication.payload.CommentDto;
import com.itbcafrica.com.blogapplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value = "postId") Long postId) {
        return new ResponseEntity<>(commentService.getAllComment(postId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable(value = "postId") Long postId, @Valid @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
