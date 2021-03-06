package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String id);

    UserEntity findUserByEmailVerificationToken(String token);
}
