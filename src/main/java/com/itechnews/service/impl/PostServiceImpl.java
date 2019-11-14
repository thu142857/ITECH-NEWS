package com.itechnews.service.impl;

import com.itechnews.entity.Post;
import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.PostRepository;
import com.itechnews.service.PostService;
import com.itechnews.util.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Post> findNewPosts(Integer page) {
        Sort sort = Sort.by("id").descending();
        return postRepository.findAll(PageRequest.of(page, 5, sort));
    }

    @Override
    public Page<Post> findTopPosts(Integer page) {
        Sort sort = Sort.by("totalViews").descending();
        return postRepository.findAll(PageRequest.of(page, 5, sort));
    }

}
