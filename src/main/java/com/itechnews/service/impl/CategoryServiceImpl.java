package com.itechnews.service.impl;

import com.itechnews.entity.Category;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByOrderById() {
        return categoryRepository.findAllByOrderById();
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository.findCategoryById(id);
    }
}
