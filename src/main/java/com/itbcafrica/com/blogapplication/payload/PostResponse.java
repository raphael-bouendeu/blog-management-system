package com.itbcafrica.com.blogapplication.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElement;
    private int totalPages;
    private boolean last;
}
