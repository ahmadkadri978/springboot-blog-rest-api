package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exeption.ResourceNotFoundExeption;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto , Category.class);
       Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory , CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundExeption("category" , "id" , categoryId));
        CategoryDto categoryDto = modelMapper.map(category , CategoryDto.class);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        List<CategoryDto> allCategoriesDto = allCategories.stream().map(category -> modelMapper.map(category , CategoryDto.class))
                .collect(Collectors.toList());
        return allCategoriesDto;
    }

    @Override
    public CategoryDto updateCategoryById(Long id , CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExeption("category" , "id" , id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category category1 = categoryRepository.save(category);
        return modelMapper.map(category1 , CategoryDto.class);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);

    }
}
