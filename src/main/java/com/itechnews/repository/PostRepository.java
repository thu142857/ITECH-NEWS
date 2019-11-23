package com.itechnews.repository;

import com.itechnews.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository
        extends CrudRepository<Post, Integer>, PagingAndSortingRepository<Post, Integer> {
    List<Post> findTop8ByStatusTrueAndIdNotInOrderByCreateAtDesc(List<Integer> ids);
    List<Post> findTop5ByStatusTrueOrderByTotalViewsDesc();
    List<Post> findTop5ByStatusTrueAndCategory_IdOrderByCreateAtDesc(Integer catId);
    Post findOneBySlug(String slug);
    Page<Post> findAllByTitleContains(String title, Pageable pageable);
}
