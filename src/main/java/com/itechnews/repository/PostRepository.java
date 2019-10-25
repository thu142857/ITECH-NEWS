package com.itechnews.repository;

import com.itechnews.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository
        extends CrudRepository<Post, Integer> {
    List<Post> findTop8ByStatusTrueOrderByCreateAtDesc();
    List<Post> findTop4ByStatusTrueOrderByTotalViewsDesc();
    Post findOneBySlug(String slug);
}
