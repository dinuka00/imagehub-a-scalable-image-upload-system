package com.dinuka.imagehub.serviceImpl;
import com.dinuka.imagehub.dto.CategoryDTO;
import com.dinuka.imagehub.entity.Category;
import com.dinuka.imagehub.exceptions.CategoryNotFoundException;
import com.dinuka.imagehub.repository.CategoryRepository;
import com.dinuka.imagehub.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: "+ id)
        );
    }

    @Override
    public Object save(Category category) {

        Category existingCategory = categoryRepository.findByName(category.getName());

        if (existingCategory != null) {
            return  category.getName()+" already exists";
        }else {

            Category newCategory = new Category();
            newCategory.setName(category.getName());
            newCategory.setDescription(category.getDescription());

            return categoryRepository.save(newCategory);
        }
    }

    @Override
    public Object update(CategoryDTO categoryDto, Integer id) {

        Category existingCategory = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: "+ id)
        );

        if(existingCategory != null) {

            if(categoryDto.getName() != null) {
                existingCategory.setName(categoryDto.getName());
            }

            if(categoryDto.getDescription() != null) {
                existingCategory.setDescription(categoryDto.getDescription());
            }


            return categoryRepository.save(existingCategory);

        }else {
            return "No Category Found with category id "+ id;
        }

    }
}
