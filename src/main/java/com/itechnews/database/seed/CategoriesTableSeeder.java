package com.itechnews.database.seed;

import com.itechnews.entity.Category;
import com.itechnews.entity.Role;
import com.itechnews.repository.CategoryRepository;
import com.itechnews.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesTableSeeder implements Seeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(null,"CÔNG NGHỆ",null));
        categories.add(new Category(null,"LẬP TRÌNH",null));
        categories.add(new Category(null,"SỰ KIỆN",null));
        categories.add(new Category(null,"BLOG",null));
        categoryRepository.saveAll(categories);
    }
}
