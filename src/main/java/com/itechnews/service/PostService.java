package com.itechnews.service;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {
    Post findOneBySlug(String slug);
    Post findOneById(Integer id);
    List<Post> findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(Integer catId);
    Post save(Post post);

    Page<Post> findAll(Integer pageNumber);
    Page<Post> findAllByTitleContains(String title, Integer pageNumber);

    void deleteById(Integer id);

    Integer countByPostedUser(User author);
    List<Post> findByPostedUser(User author);
    Integer calculateTotalViewOfUser(User author);
    Integer calculateTotalLikeOfUser(User author);
    Integer countByPostedUserAndTagsContains(User author, Tag tag);
    Page<Post> findNewPosts(Integer page);
    Page<Post> findTopPosts(Integer page);
    Page<Post> searchByTitle(String searchTitle, Integer page);
    Page<Post> findByTagContains(Tag tag, Integer page);

}
