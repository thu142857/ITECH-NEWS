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
        categories.add(new Category(null,"Công Nghệ",null));
        categories.add(new Category(null,"Lập Trình",null));
        categories.add(new Category(null,"Sự kiện",null));
        categories.add(new Category(null,"Blog",null));
        categoryRepository.saveAll(categories);
    }
}
