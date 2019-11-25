package com.itechnews.service;

import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Page<Tag> findAll(Integer pageNumber);

    Page<Tag> findAllByNameContains(String searchingName, Integer pageNumber);
    Tag findOneById(Integer id);
    void deleteById(Integer id);
    Tag save(Tag tag);
    Integer countAllByNameEquals(String tagName);
    List<Tag> findBestTags();
    List<Tag> findByUser(User user);
    List<Tag> findTopTags(Integer quantity);
    Page<Tag> findAll(Pageable pageable);
    List<Tag> findAll();
    List<Tag> findByIdIn(List<Integer> ids);
    Tag findBySlug(String slug);

    List<Tag> findAllByStatusTrue();

}
