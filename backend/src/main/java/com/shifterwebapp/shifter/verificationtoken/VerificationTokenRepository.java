package com.shifterwebapp.shifter.verificationtoken;

import com.shifterwebapp.shifter.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByUser(User user);

    Optional<VerificationToken> findByUserIdAndExpiresAtAfter(Long userId, Instant instant);

    @Transactional  // required so that the action is done within a single transaction
    @Modifying      // required for DML (Data Manipulation Language) - DELETE, UPDATE, INSERT
    @Query("delete from VerificationToken vt where vt.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
