package com.itechnews.service.impl;

import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.TagRepository;
import com.itechnews.repository.UserRepository;
import com.itechnews.service.TagService;
import com.itechnews.service.UserService;
import com.itechnews.util.SlugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Page<Tag> findAll(Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return tagRepository.findAll(PageRequest.of(pageNumber - 1, 6, Sort.by(Sort.Direction.ASC, "id")));
    }

    @Override
    public List<Tag> findAllByStatusTrue() {
        return tagRepository.findAllByStatusTrue();
    }

    @Override
    public Page<Tag> findAllByNameContains(String searchingName, Integer pageNumber) {
        if (pageNumber == null)
            pageNumber = 1;
        return tagRepository.findAllByNameContains(searchingName,
                PageRequest.of(pageNumber - 1, 6,
                        Sort.by(Sort.Direction.ASC, "id")));
    }

    @Override
    public Tag findOneById(Integer id) {
        Optional<Tag> optional = tagRepository.findById(id);
        if (optional.isPresent()) {
            return  optional.get();
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag save(Tag tag) {
        tag.setSlug(SlugUtil.makeSlug(tag.getName()));
        return tagRepository.save(tag);
    }

    @Override
    public Integer countAllByNameEquals(String tagName) {
        return tagRepository.countAllByNameEquals(tagName);
    }

    @Override
    public List<Tag> findBestTags() {
        return tagRepository.findBestTags(PageRequest.of(0, 10));
    }
}
