package com.shifterwebapp.shifter.verificationtoken.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.verificationtoken.VerificationToken;
import com.shifterwebapp.shifter.verificationtoken.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements ImplVerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final Validate validate;

    private final long tokenExpiryMinutes = 60;

    @Override
    public UUID generateNewToken(User user) {
        UUID token = UUID.randomUUID();
        VerificationToken newVerificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .expiresAt(Instant.now().plus(Duration.ofMinutes(tokenExpiryMinutes)))
                .build();
        verificationTokenRepository.save(newVerificationToken);
        return token;
    }
}
