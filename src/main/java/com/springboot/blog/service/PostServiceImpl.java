package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exeption.ResourceNotFoundExeption;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponce;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper , CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // get category

        Long categoryId = postDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExeption("Category" , "id" , categoryId));

        //convert DTO to Entity

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //convert Entity to DTO

        PostDto postResponce =  convertToDto(newPost);
        return postResponce;
    }

    @Override
    public PostResponce getAllPosts(int pageNo , int pageSize , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo , pageSize ,sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> pagePostsToList = pagePosts.getContent();
        List<PostDto> postDtos = pagePostsToList.stream()
                .map(post -> convertToDto(post))
                .collect(Collectors.toList());
        PostResponce postResponce = new PostResponce();
        postResponce.setContent(postDtos);
        postResponce.setPageNo(pagePosts.getNumber());
        postResponce.setPageSize(pagePosts.getSize());
        postResponce.setTotalElements(pagePosts.getTotalElements());
        postResponce.setTotalPages(pagePosts.getTotalPages());
        postResponce.setLast(pagePosts.isLast());


        return postResponce;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = new Post();
        post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExeption("post","id" , id));
        PostDto postDtoById = convertToDto(post);
        return postDtoById;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExeption("post","id" , id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        PostDto updatedPostDto = convertToDto(updatedPost);

        return updatedPostDto;
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExeption("post","id",id));
        postRepository.delete(post);
    }

    // Helper method to convert Post entity to PostDto
    private PostDto convertToDto(Post post) {
        PostDto postDto=modelMapper.map(post,PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        // Add other properties if needed

        return postDto;
    }
}
