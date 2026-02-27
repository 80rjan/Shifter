package com.shifterwebapp.shifter.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.config.JwtService;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.mapper.UserMapper;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import com.shifterwebapp.shifter.account.user.service.UserService;
import com.shifterwebapp.shifter.account.expert.Expert; // ADD THIS
import com.shifterwebapp.shifter.account.expert.repository.ExpertRepository; // ADD THIS
import com.shifterwebapp.shifter.verificationtoken.VerificationTokenRepository;
import com.shifterwebapp.shifter.verificationtoken.service.VerificationTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Value("${cookie.same-site}")
    private String cookieSameSite;


    private void sendTokens(HttpServletResponse response, UserDetails userDetails) throws IOException {
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), authResponse);
    }

    @Transactional
    public void register(String email, String password, HttpServletResponse response) throws IOException {
        User user = userService.createInitialUser(email, password, LoginProvider.LOCAL);

        UUID token = verificationTokenService.generateNewToken(user);

        String verificationUrl = frontendUrl + "/welcome?token=" + token;
        emailService.sendVerificationToken(user.getEmail(), verificationUrl);

        sendTokens(response, user);
    }

    @Transactional
    public void personalize(UserPersonalizationDto userPersonalizationDto, HttpServletResponse response) throws IOException {
        User user = userService.personalizeUser(userPersonalizationDto);
        sendTokens(response, user);
    }

    @Transactional(readOnly = true)
    public void authenticate(LoginDto request, HttpServletResponse response) throws IOException {
        // Check both User and Expert tables
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        Optional<Expert> expertOptional = expertRepository.findByEmail(request.getEmail());


        // Authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        // Determine which entity to use
        if (expertOptional.isPresent()) {
            Expert expert = expertOptional.get();
            // Assuming experts don't need verification check, adjust if needed
            sendTokens(response, expert);
        } else if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate login provider for Users (Experts might not have this check)
            if (user.getLoginProvider() != LoginProvider.LOCAL) {
                throw new BadCredentialsException(
                        "This account was registered with " + user.getLoginProvider() +
                                ". Please use the corresponding login method."
                );
            }

            if (user.isVerified()) {
                sendTokens(response, user);
            } else {
                throw new BadCredentialsException("Please verify your email before logging in.");
            }
        } else {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    @Transactional(readOnly = true)
    public void finalizeOAuthLogin(Long userId, HttpServletResponse response) throws IOException {
        User user = userService.getEntityById(userId);

        if (user.isVerified())
            sendTokens(response, user);
    }

    @Transactional(readOnly = true)
    public AuthResponse refreshToken(HttpServletRequest request) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        String userEmail = jwtService.extractUsername(refreshToken);

        // Check both User and Expert tables
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        Optional<Expert> expertOptional = expertRepository.findByEmail(userEmail);

        UserDetails userDetails;
        if (expertOptional.isPresent()) {
            userDetails = expertOptional.get();
        } else if (userOptional.isPresent()) {
            userDetails = userOptional.get();
        } else {
            throw new RuntimeException("User not found");
        }

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .maxAge(0)
                .sameSite(cookieSameSite)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        // Check both tables
        return userService.existsUserByEmail(email) || expertRepository.existsByEmail(email);
    }
}