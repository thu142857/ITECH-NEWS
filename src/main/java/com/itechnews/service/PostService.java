package com.itechnews.service;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {
    Post findOneBySlug(String slug);
    Post findOneById(Integer id);
    List<Post> findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(Integer catId);
    Post save(Post post);
    Integer countByPostedUser(User author);
    List<Post> findByPostedUser(User author);
    Integer calculateTotalViewOfUser(User author);
    Integer calculateTotalLikeOfUser(User author);
    Integer countByPostedUserAndTagsContains(User author, Tag tag);
}
