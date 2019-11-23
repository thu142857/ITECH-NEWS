package com.itechnews.repository;

import com.itechnews.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository
        extends CrudRepository<Category, Integer> {

    Category findOneByName(String name);
    List<Category> findAllByOrderById();
    Category findCategoryById(Integer catId);
}
