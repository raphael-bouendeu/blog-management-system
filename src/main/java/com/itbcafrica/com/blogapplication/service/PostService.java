package com.itbcafrica.com.blogapplication.service;

import com.itbcafrica.com.blogapplication.payload.PostDto;
import com.itbcafrica.com.blogapplication.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
