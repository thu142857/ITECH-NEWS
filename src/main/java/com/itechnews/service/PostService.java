package com.itechnews.service;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import org.springframework.data.domain.Page;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {
    Post findOneBySlug(String slug);
    Post findOneById(Integer id);
    List<Post> findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(Integer catId);
    Post save(Post post);
}
