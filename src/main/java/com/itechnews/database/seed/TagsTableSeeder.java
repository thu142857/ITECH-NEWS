package com.itechnews.database.seed;

import com.itechnews.database.factory.JsonFactory;
import com.itechnews.entity.Tag;
import com.itechnews.repository.TagRepository;
import com.itechnews.util.SlugUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagsTableSeeder implements Seeder {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    Faker faker;

    @Override
    public void run() {
        List<Tag> categories = new ArrayList<>();
        //get category names from data.json
        JsonNode nodeCategories = JsonFactory.getJsonNode("tags");

        if (nodeCategories != null) {
            nodeCategories.forEach((item) -> categories.add(
                new Tag(null, item.asText(),
                    SlugUtil.makeSlug(item.asText()), null, true)
            ));
        }
        tagRepository.saveAll(categories);
    }
}
