package com.itechnews.service;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import org.springframework.data.domain.Page;

public interface PostService {
    Post findOneBySlug(String slug);
    Post findOneById(Integer id);
}
