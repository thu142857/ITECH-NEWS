package com.itechnews.service;

import com.itechnews.entity.Post;
import org.springframework.data.domain.Page;
import java.util.List;

public interface PostService {
    Post findOneBySlug(String slug);
    Post findOneById(Integer id);
    void deleteById(Integer id);
    List<Post> findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(Integer catId);
    Post save(Post post);
    Page<Post> findAll(Integer pageNumber);
    Page<Post> findAllByTitleContains(String title, Integer pageNumber);
}
