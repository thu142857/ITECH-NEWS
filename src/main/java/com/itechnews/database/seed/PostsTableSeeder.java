package com.itechnews.database.seed;

import com.itechnews.database.factory.JsonFactory;
import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.PostRepository;
import com.itechnews.repository.TagRepository;
import com.itechnews.repository.UserRepository;
import com.itechnews.util.SlugUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PostsTableSeeder implements Seeder {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;

    @Autowired
    Faker faker;

    @Override
    public void run() {
        List<Post> posts = new ArrayList<>();
        JsonNode nodePosts = JsonFactory.getJsonNode("posts");
        List<User> authors = userRepository.findByIdLessThan(6);
        Random random = new Random();
        if (nodePosts != null) {
            nodePosts.forEach((item) -> {
                //random author 1->5
                int userId = random.nextInt(5);
                User author = authors.get(userId);

                //if tags not exited => insert tags to the database
                List<Tag> tags = new ArrayList<>();
                item.get("tags").forEach(nodeTagName -> {
                    String tagName = nodeTagName.asText();
                    Tag tag = tagRepository.findOneByName(tagName);
                    if (tag == null) {
                        tagRepository.save(new Tag(null, tagName, SlugUtil.makeSlug(tagName), null, true));
                        tag = tagRepository.findOneByName(tagName);
                    }
                    tags.add(tag);
                });

                Post post = new Post(null, tags, item.get("title").asText(), item.get("content").asText(),null,
                        SlugUtil.makeSlug(item.get("title").asText()), item.get("total_views").asInt(),
                        item.get("status").asBoolean(),item.get("image").asText(), null,
                        author, null, null);
                posts.add(post);
            });
        }
        postRepository.saveAll(posts);
    }
}
