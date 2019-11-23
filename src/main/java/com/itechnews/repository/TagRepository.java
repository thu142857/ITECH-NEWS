package com.itechnews.repository;

import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository
        extends PagingAndSortingRepository<Tag, Integer>,
        JpaSpecificationExecutor<Tag>,
        CrudRepository<Tag, Integer> {

    Tag findBySlug(String slug);

    Tag findOneByName(String name);
    @Query(
        value = "select t from Tag t order by t.posts.size desc"
    )
    List<Tag> findBestTags(Pageable pageable);

    Page<Tag> findAllByNameContains(String searchingName, Pageable pageable);

    Integer countAllByNameEquals(String tagName);

    List<Tag> findAllByStatusTrue();

    @Query(value = "select distinct t from Tag t join t.posts p where p.postedUser = :user")
    List<Tag> findByUser(@Param("user") User user);

    @Query(value = "select t from Tag t order by t.posts.size desc")
    List<Tag> findTopTags(Pageable pageable);

    List<Tag> findByIdIn(List<Integer> ids);


}
