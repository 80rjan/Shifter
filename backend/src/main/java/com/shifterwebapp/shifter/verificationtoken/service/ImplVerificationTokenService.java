package com.shifterwebapp.shifter.verificationtoken.service;

import com.shifterwebapp.shifter.account.user.User;

import java.util.UUID;

public interface ImplVerificationTokenService {
    public UUID generateNewToken(User user);
    public String verify(String token);
}
