package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long id , CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId , Long commentId);
    CommentDto updateComment(Long postId , CommentDto commentDto , Long id);
    void deleteComment(Long postId ,Long commentId);
}
