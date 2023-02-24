package com.itbcafrica.com.blogapplication.service;

import com.itbcafrica.com.blogapplication.converter.PostConverter;
import com.itbcafrica.com.blogapplication.entity.Post;
import com.itbcafrica.com.blogapplication.exception.ResourceNotFoundException;
import com.itbcafrica.com.blogapplication.payload.PostDto;
import com.itbcafrica.com.blogapplication.payload.PostResponse;
import com.itbcafrica.com.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;


    @Autowired
    private PostConverter postConverter;

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post newPost = repository.save(postConverter.convertDtoToEntity(postDto));
        //convert entity to DTO
        return postConverter.convertEntityToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<PostDto> postDtos = new ArrayList<>();
        Page<Post> posts = repository.findAll(pageable);
        //Get content of page object

        List<Post> listOfPosts = posts.getContent();

        for (Post post : listOfPosts) {
            postDtos.add(postConverter.convertEntityToDto(post));
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        return postConverter.convertEntityToDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        PostDto dto = postConverter.convertEntityToDto(post);
        dto.setTitle(postDto.getTitle());
        dto.setDescription(postDto.getDescription());
        dto.setContent(postDto.getContent());
        Post newPost = repository.save(postConverter.convertDtoToEntity(dto));
        return dto;
    }

    @Override
    public void deletePost(Long id) {
        Post post = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        repository.delete(post);
    }
}
