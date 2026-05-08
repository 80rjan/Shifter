package com.shifterwebapp.shifter.auth.application.impl;

import com.shifterwebapp.shifter.identity.domain.User;

import java.util.UUID;

public interface VerificationTokenServiceImpl {
    public UUID generateNewToken(User user);
    public String verify(String token);
}
