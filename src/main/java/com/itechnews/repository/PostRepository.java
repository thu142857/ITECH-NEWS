package com.itechnews.repository;

import com.itechnews.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository
        extends CrudRepository<Post, Integer> {

}
