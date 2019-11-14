package com.itechnews.service;

import com.itechnews.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findOneByEmail(String email);
    User findOneById(Integer id);
    User findOneByUsername(String username);
    User findOneByPasswordResetToken(String token);
    User save(User user);
    List<User> findTopUsers(Integer quantity);
}
