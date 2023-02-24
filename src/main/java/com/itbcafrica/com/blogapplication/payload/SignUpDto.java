package com.itbcafrica.com.blogapplication.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpDto {
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "username should not be null or empty")
    private String username;
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;
    private String password;
}
