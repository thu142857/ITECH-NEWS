package com.itechnews.repository;

import com.itechnews.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {

    VerificationToken findOneByToken(String token);

    @Transactional
    void deleteByUser_Id(Integer userId);
}
