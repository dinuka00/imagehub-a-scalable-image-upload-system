package com.dinuka.imagehub.service;

import com.dinuka.imagehub.dto.CategoryDTO;
import com.dinuka.imagehub.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    List<Category> findAll();

    Category findById(Integer id);

    Object save(Category category);

    Object update(CategoryDTO category, Integer id);

}
