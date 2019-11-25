package com.itechnews.service;

import com.itechnews.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);

    List<Category> findAllByOrderById();

    Category findCategoryById(Integer id);
}
