package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponce;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponce getAllPosts(int pageNo , int pageSize  ,String sortBy , String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto , Long id);
    void deletePostById (Long id);
}
