package com.itechnews.service;

import com.itechnews.entity.User;

public interface UserService {
    User findOneByEmail(String email);
    User findOneByUsername(String username);
    User findOneByPasswordResetToken(String token);
    User save(User user);
}
