package com.itechnews.service.impl;

import com.itechnews.entity.PasswordResetToken;
import com.itechnews.entity.VerificationToken;
import com.itechnews.repository.PasswordResetTokenRepository;
import com.itechnews.repository.VerificationTokenRepository;
import com.itechnews.service.PasswordResetTokenService;
import com.itechnews.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findOneByToken(String token) {
        return verificationTokenRepository.findOneByToken(token);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        verificationTokenRepository.deleteByUser_Id(userId);
    }
}
