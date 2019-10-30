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

public interface UserRepository extends CrudRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer>,
        JpaSpecificationExecutor<Tag>{

    //@Query("select u from #{#entityName} u where u.username = :username")
    //@Query("select u from User u where u.username = :username")
    User findOneByUsername(@Param("username") String username);

    List<User> findAll();
    Page<User> findAllByDisplayedNameContains(String name, Pageable pageable);
    User findUserById(Integer id);
    //@Query("select u from User u where u.username = :username and u.status = :status")
    User findOneByUsernameAndStatus(String username, Boolean status);

    List<User> findByIdLessThan(Integer id);
    List<User> findByIdGreaterThan(Integer id);

    User findOneByEmail(String email);
    Integer countAllByEmailEquals(String email);
    @Query("select u from User u where u.passwordResetToken.token= :token")
    User findOneByPasswordResetToken(String token);
}
