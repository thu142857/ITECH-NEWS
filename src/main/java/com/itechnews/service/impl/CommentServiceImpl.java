package com.itechnews.service.impl;

import com.itechnews.entity.Comment;
import com.itechnews.repository.CommentRepository;
import com.itechnews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findByParentIsNullAndPostId(Integer postId) {
        return commentRepository.findByParentIsNullAndPost_IdOrderByCreateAtDesc(postId);
    }

    @Override
    public Comment findOneById(Integer id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteByParentId(Integer parentId) {
        commentRepository.deleteByParentId(parentId);
    }

    @Override
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
