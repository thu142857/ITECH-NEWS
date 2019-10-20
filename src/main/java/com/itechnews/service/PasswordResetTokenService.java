package com.itechnews.service;

import com.itechnews.entity.PasswordResetToken;
import com.itechnews.entity.User;

public interface PasswordResetTokenService {
    PasswordResetToken save(PasswordResetToken passwordResetToken);
    PasswordResetToken findOneByToken (String token);
    void deleteByUserId(Integer userId);
}
