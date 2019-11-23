package com.itechnews.service;

import com.itechnews.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {
    Page<Tag> findAll(Integer pageNumber);
    List<Tag> findAllByStatusTrue();
    Page<Tag> findAllByNameContains(String searchingName, Integer pageNumber);
    Tag findOneById(Integer id);
    void deleteById(Integer id);
    Tag save(Tag tag);
    Integer countAllByNameEquals(String tagName);
    List<Tag> findBestTags();
}
