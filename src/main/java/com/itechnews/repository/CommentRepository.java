package com.itechnews.repository;

import com.itechnews.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.itechnews.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CommentRepository
        extends PagingAndSortingRepository<Comment, Integer>,
        JpaSpecificationExecutor<Comment>,
        CrudRepository<Comment, Integer> {
    List<Comment> findByParentIsNullAndPost_IdOrderByCreateAtDesc(Integer postId);

    Page<Comment> findByPost_Id(Integer postId,Pageable pageable);

    Page<Comment> findAllByContentContains(String searchingName, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where parent_id = :parentId", nativeQuery = true)
    void deleteByParentId(@Param("parentId") Integer parentId);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    Integer countByPost_PostedUser(User user);

}
