package com.itechnews.service;

import com.itechnews.entity.Comment;
import com.itechnews.entity.Post;

import java.util.List;

public interface CommentService {
    List<Comment> findByParentIsNullAndPostId(Integer postId);
    Comment findOneById(Integer id);
    Comment save(Comment comment);
    void deleteByParentId(Integer parentId);
    void deleteById(Integer id);
}
