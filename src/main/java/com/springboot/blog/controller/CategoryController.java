package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import org.apache.catalina.realm.CombinedRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto) , HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public  ResponseEntity<CategoryDto> findCategoryById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id) , HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories() , HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") Long id , @RequestBody CategoryDto categoryDto ){
        return new ResponseEntity<>(categoryService.updateCategoryById(id , categoryDto) , HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id  ){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("category:"+id+" deleted successfuly" , HttpStatus.OK);

    }

}
