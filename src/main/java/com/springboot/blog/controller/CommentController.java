package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") Long id , @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(id , commentDto) , HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getAllCommentsByPostId(postId);

    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByPost_CommentId(@PathVariable(value = "postId") Long postId , @PathVariable(value = "commentId") Long commentId){
        CommentDto commentDto = commentService.getCommentById(postId , commentId);

        return new ResponseEntity<>(commentDto , HttpStatus.OK);

    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId ,
                                                    @PathVariable(value = "commentId") Long commentId ,
                                                    @Valid @RequestBody CommentDto commentDto ){
        return new ResponseEntity<>(commentService.updateComment(postId,commentDto,commentId) , HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId ,
                                                @PathVariable(value = "commentId") Long commentId ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment deleted successfully" , HttpStatus.OK);

    }



}
