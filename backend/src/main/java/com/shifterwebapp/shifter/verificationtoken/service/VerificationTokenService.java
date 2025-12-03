package com.shifterwebapp.shifter.verificationtoken.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.exception.InvalidVerificationTokenException;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.repository.UserRepository;
import com.shifterwebapp.shifter.verificationtoken.VerificationToken;
import com.shifterwebapp.shifter.verificationtoken.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements ImplVerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final Validate validate;
    private final UserRepository userRepository;

    private final long tokenExpiryMinutes = 30;

    @Override
    @Transactional
    public UUID generateNewToken(User user) {
        validate.validateUserProfileNotComplete(user);

        Optional<VerificationToken> existingToken = verificationTokenRepository.findByUserIdAndExpiresAtAfter(
                user.getId(),
                Instant.now().plus(Duration.ofMinutes(5))
        );

        if (existingToken.isPresent())
            return existingToken.get().getToken();

        verificationTokenRepository.deleteByUserId(user.getId()); // atomic deletion

        UUID token = UUID.randomUUID();
        VerificationToken newVerificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(Duration.ofMinutes(tokenExpiryMinutes)))
                .build();
        verificationTokenRepository.save(newVerificationToken);
        return token;
    }

    @Override
    public String verify(String token) {
        UUID uuid = UUID.fromString(token);
        VerificationToken verificationToken = verificationTokenRepository.findById(uuid)
                .orElseThrow(() -> new InvalidVerificationTokenException("Invalid or expired token"));

        if (verificationToken.getExpiresAt().isBefore(Instant.now()))
            throw new InvalidVerificationTokenException("Expired token");

        User user = verificationToken.getUser();
        user.setIsEnabled(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return user.getEmail();
    }
}
