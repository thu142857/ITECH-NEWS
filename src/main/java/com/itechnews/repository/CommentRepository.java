package com.itechnews.repository;

import com.itechnews.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository
        extends PagingAndSortingRepository<Comment, Integer>,
        JpaSpecificationExecutor<Comment>,
        CrudRepository<Comment, Integer> {
    List<Comment> findByParentIsNullAndPost_IdOrderByCreateAtDesc(Integer postId);


    Page<Comment> findByPost_Id(Integer postId,Pageable pageable);

    Page<Comment> findAllByContentContains(String searchingName, Pageable pageable);
}
