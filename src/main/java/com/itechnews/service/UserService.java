package com.itechnews.service;

import com.itechnews.entity.User;

public interface UserService {
    User findOneByEmail(String email);
    User findOneById(Integer id);
    User findOneByUsername(String username);
    User findOneByPasswordResetToken(String token);
    User save(User user);
}
