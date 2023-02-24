package com.itbcafrica.com.blogapplication.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {

    private Long id;
    @NotNull
    @NotEmpty
    @Size(min = 2, message = "Post Title should have a least 2 Characters")
    private String title;
    @NotNull
    @NotEmpty
    @Size(min = 10, message = "Post Description should have a least 10 Characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
