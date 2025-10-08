package com.shifterwebapp.shifter.external.google;

import com.shifterwebapp.shifter.config.JwtService;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.service.UserService;
import com.shifterwebapp.shifter.verificationtoken.service.VerificationTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Executes immediately after a successful OAuth2 handshake, allowing us to
 * access the authenticated OAuth2User before the SecurityContext is lost
 * due to a STATELESS session policy.
 */
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final JwtService jwtService;

    // Frontend base URL where the final redirect must go
    private static final String FRONTEND_REDIRECT_BASE = "http://localhost:5173/oauth2/redirect";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // 1. Safely cast and extract the OAuth2User Principal
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication token type.");
            return;
        }

        OAuth2User oauthUser = ((OAuth2AuthenticationToken) authentication).getPrincipal();

        // At this point, oauthUser is guaranteed to be non-null if this handler is reached
        String email = oauthUser.getAttribute("email");
        String token;
        boolean login;
        User user;

        boolean userExists = userService.existsUserByEmail(email);
        if (userExists) {
            // Existing user → generate and return JWT
            user = userService.getUserEntityByEmail(email);
            token = jwtService.generateToken(user);
            login = true;
        } else {
            // New user → create minimal account and generate verification token
            String randomPassword = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            user = userService.createInitialUser(email, randomPassword, LoginProvider.GOOGLE);
            token = verificationTokenService.generateNewToken(user).toString();
            login = false;
        }

        assert email != null;
        String redirectUrl = String.format("%s?token=%s&login=%s&email=%s",
                FRONTEND_REDIRECT_BASE,
                URLEncoder.encode(token, StandardCharsets.UTF_8),
                login,
                URLEncoder.encode(email, StandardCharsets.UTF_8));

        response.sendRedirect(redirectUrl);
    }
}
