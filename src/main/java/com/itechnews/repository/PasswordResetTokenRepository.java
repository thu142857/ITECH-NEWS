package com.itechnews.repository;

import com.itechnews.entity.PasswordResetToken;
import com.itechnews.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    PasswordResetToken findOneByToken(String token);

    @Transactional
    void deleteByUser_Id(Integer userId);
}
