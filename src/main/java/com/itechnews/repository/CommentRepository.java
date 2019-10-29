package com.itechnews.repository;

import com.itechnews.entity.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository
        extends CrudRepository<Comment, Integer> {
    List<Comment> findByParentIsNullAndPost_IdOrderByCreateAtDesc(Integer postId);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where parent_id = :parentId", nativeQuery = true)
    void deleteByParentId(@Param("parentId") Integer parentId);

    @Modifying
    @Transactional
    @Query(value = "delete from comments where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

}
