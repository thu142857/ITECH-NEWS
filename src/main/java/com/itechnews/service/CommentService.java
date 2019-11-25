package com.itechnews.service;

import com.itechnews.entity.Comment;
import com.itechnews.entity.Post;
import org.springframework.data.domain.Page;
import com.itechnews.entity.User;
import java.util.List;

public interface CommentService {
    List<Comment> findByParentIsNullAndPostId(Integer postId);
    Comment findOneById(Integer id);
    Comment save(Comment comment);

    Page<Comment> findAll(Integer page);

    Page<Comment> findAllByContentContains(String q, Integer page);

    void deleteById(Integer commentId);

    Page<Comment> findCommentByPostId(Integer postId,Integer pageNumber);

    void deleteByParentId(Integer parentId);

    Integer countByPostedUser(User user);
}
