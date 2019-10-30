package com.itechnews.repository;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository
        extends CrudRepository<Post, Integer> {
    List<Post> findTop8ByStatusTrueAndIdNotInOrderByCreateAtDesc(List<Integer> ids);
    List<Post> findTop5ByStatusTrueOrderByTotalViewsDesc();
    List<Post> findTop5ByStatusTrueAndCategory_IdOrderByCreateAtDesc(Integer catId);
    Post findOneBySlug(String slug);
    Integer countByPostedUser(User author);
    List<Post> findByPostedUser(User author);

    @Query(value = "select sum(p.totalViews) from Post p where p.postedUser = :postedUser")
    Integer calculateTotalViewOfUser(@Param("postedUser") User author);

    @Query(value = "select sum(p.likedUsers.size) from Post p where p.postedUser = :postedUser")
    Integer calculateTotalLikeOfUser(@Param("postedUser") User author);

    Integer countByPostedUserAndTagsContains(User author, Tag tag);

}
