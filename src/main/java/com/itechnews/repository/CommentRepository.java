package com.itechnews.repository;

import com.itechnews.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository
        extends CrudRepository<Comment, Integer> {
    List<Comment> findByParentIsNullAndPost_IdOrderByCreateAtDesc(Integer postId);
}
