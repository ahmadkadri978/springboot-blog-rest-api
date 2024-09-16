package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty
    @Size(min = 10 , message = "Comment should not be empty")
    private String body;
    public CommentDto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
