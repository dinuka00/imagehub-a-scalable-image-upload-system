package com.dinuka.imagehub.controller;


import com.dinuka.imagehub.entity.Category;
import com.dinuka.imagehub.dto.CategoryDTO;
import com.dinuka.imagehub.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>>  getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category>  getCategoryById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(id));
    }

    @PostMapping("/category")
    public ResponseEntity<Object>  createCategory(@RequestBody Category category){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Object>  updateCategory(@RequestBody CategoryDTO category, @PathVariable Integer id){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(category,id));
    }


}
