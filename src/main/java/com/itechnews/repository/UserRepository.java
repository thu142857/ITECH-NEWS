package com.itechnews.repository;

import com.itechnews.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    //@Query("select u from #{#entityName} u where u.username = :username")
    //@Query("select u from User u where u.username = :username")
    User findOneByUsername(@Param("username") String username);

    //@Query("select u from User u where u.username = :username and u.status = :status")
    User findOneByUsernameAndStatus(String username, Boolean status);

    List<User> findByIdLessThan(Integer id);
}
