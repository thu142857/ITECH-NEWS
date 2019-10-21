package com.itechnews.service;

import com.itechnews.entity.PasswordResetToken;
import com.itechnews.entity.VerificationToken;

public interface VerificationTokenService {
    VerificationToken save(VerificationToken verificationToken);
    VerificationToken findOneByToken(String token);
    void deleteByUserId(Integer userId);
}
