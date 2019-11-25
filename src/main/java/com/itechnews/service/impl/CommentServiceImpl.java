package com.itechnews.service.impl;

import com.itechnews.entity.Comment;
import com.itechnews.entity.User;
import com.itechnews.repository.CommentRepository;
import com.itechnews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Comment> findAll(Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return commentRepository.findAll(PageRequest.of(pageNumber - 1, 6, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public Page<Comment> findAllByContentContains(String searchingName, Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return commentRepository.findAllByContentContains(searchingName,
                PageRequest.of(pageNumber - 1, 6,
                        Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public void deleteById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Page<Comment> findCommentByPostId(Integer postId,Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return commentRepository.findByPost_Id(postId, PageRequest.of(pageNumber - 1, 6,
                Sort.by(Sort.Direction.DESC, "id")));
    }
    @Override
    public void deleteByParentId(Integer parentId) {
        commentRepository.deleteByParentId(parentId);
    }

    @Override
    public Integer countByPostedUser(User user) {
        return commentRepository.countByPost_PostedUser(user);
    }
}
