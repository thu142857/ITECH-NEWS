package com.itechnews.service.impl;

import com.itechnews.entity.PasswordResetToken;
import com.itechnews.repository.PasswordResetTokenRepository;
import com.itechnews.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findOneByToken(String token) {
        return passwordResetTokenRepository.findOneByToken(token);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        passwordResetTokenRepository.deleteByUser_Id(userId);
    }
}
