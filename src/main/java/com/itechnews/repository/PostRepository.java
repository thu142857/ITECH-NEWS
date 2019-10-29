package com.itechnews.repository;

import com.itechnews.entity.Post;
import com.itechnews.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository
        extends CrudRepository<Post, Integer> {
    List<Post> findTop8ByStatusTrueAndIdNotInOrderByCreateAtDesc(List<Integer> ids);
    List<Post> findTop5ByStatusTrueOrderByTotalViewsDesc();
    List<Post> findTop5ByStatusTrueAndCategory_IdOrderByCreateAtDesc(Integer catId);
    Post findOneBySlug(String slug);
    Integer countByPostedUser(User author);
}
