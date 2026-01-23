package com.shifterwebapp.shifter.account.user.repository;

import com.shifterwebapp.shifter.account.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    @Query("select u.email from User u where u.id = :userId")
    String getUserEmailById(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);

    @Query("select u.id from User u where u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);
}
