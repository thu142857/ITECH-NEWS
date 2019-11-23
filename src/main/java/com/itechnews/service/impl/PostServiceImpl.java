package com.itechnews.service.impl;

import com.itechnews.entity.Post;
import com.itechnews.repository.PostRepository;
import com.itechnews.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void deleteById(Integer id) {
         postRepository.deleteById(id);
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
    public Page<Post> findAll(Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return postRepository.findAll(PageRequest.of(pageNumber - 1, 6, Sort.by(Sort.Direction.ASC, "id")));

    }

    @Override
    public Page<Post> findAllByTitleContains(String title, Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return postRepository.findAllByTitleContains(title,
                PageRequest.of(pageNumber - 1, 6,
                        Sort.by(Sort.Direction.ASC, "id")));
    }
}
