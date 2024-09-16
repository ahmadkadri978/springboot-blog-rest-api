package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exeption.BlogApiException;
import com.springboot.blog.exeption.ResourceNotFoundExeption;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository , ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long id, CommentDto commentDto) {
        Comment comment = convertToComment(commentDto);

//        Optional<Post> optionalPost = postRepository.findById(id);
//
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            comment.setPost(post);
//            Comment savedComment = commentRepository.save(comment);
//            CommentDto commentDto1 = convertToDto(savedComment);
//            return commentDto1;
//
//        }
//        else throw new ResourceNotFoundExeption("post" , "id" , id);

        Optional<Post> optionalPost = postRepository.findById(id);

        return optionalPost.map(post -> {
            comment.setPost(post);
            Comment savedComment = commentRepository.save(comment);
            return convertToDto(savedComment);
        }).orElseThrow(() -> new ResourceNotFoundExeption("Post", "id", id));



    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);


        return comments.stream().map(comment->convertToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundExeption("post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundExeption("comment" , "id" , commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }

        return convertToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId , CommentDto commentDto, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundExeption("post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundExeption("comment" , "id" , commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundExeption("post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundExeption("comment" , "id" , commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comment does not belong to post");
        }
        commentRepository.delete(comment);

    }

    private CommentDto convertToDto(Comment comment) {
            CommentDto commentDto = modelMapper.map(comment ,CommentDto.class );
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        // Add other properties if needed

        return commentDto;
    }
    private Comment convertToComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto , Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        // Add other properties if needed

        return comment;
    }
}
