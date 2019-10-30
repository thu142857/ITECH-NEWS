package com.itechnews.service.impl;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.PostRepository;
import com.itechnews.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;


    @Override
    public Post findOneBySlug(String slug) {
        return postRepository.findOneBySlug(slug);
    }

    @Override
    public Post findOneById(Integer id) {
        return postRepository.findById(id).get();
    }

    @Override
    public List<Post> findTop5ByStatusTrueAndCategoryOrderByCreateAtDesc(Integer catId) {
        return postRepository.findTop5ByStatusTrueAndCategory_IdOrderByCreateAtDesc(catId);
    }
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Integer countByPostedUser(User author) {
        return postRepository.countByPostedUser(author);
    }

    @Override
    public List<Post> findByPostedUser(User author) {
        return postRepository.findByPostedUser(author);
    }

    @Override
    public Integer calculateTotalViewOfUser(User author) {
        return postRepository.calculateTotalViewOfUser(author);
    }

    @Override
    public Integer calculateTotalLikeOfUser(User author) {
        return postRepository.calculateTotalLikeOfUser(author);
    }

    @Override
    public Integer countByPostedUserAndTagsContains(User author, Tag tag) {
        return postRepository.countByPostedUserAndTagsContains(author, tag);
    }
}
