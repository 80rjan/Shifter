package com.shifterwebapp.shifter.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.config.JwtService;
import com.shifterwebapp.shifter.exception.InvalidVerificationTokenException;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserMapper;
import com.shifterwebapp.shifter.user.UserRepository;
import com.shifterwebapp.shifter.user.service.UserService;
import com.shifterwebapp.shifter.verificationtoken.VerificationToken;
import com.shifterwebapp.shifter.verificationtoken.VerificationTokenRepository;
import com.shifterwebapp.shifter.verificationtoken.service.VerificationTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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

    private void sendTokens(HttpServletResponse response, User user) throws IOException {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Create secure HTTP-only cookie for refresh token
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true) // true if using HTTPS
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .user(userMapper.toDto(user))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), authResponse);
    }


    public void register(String email, String password) {
        User user = userService.createInitialUser(email, password);

        UUID token = verificationTokenService.generateNewToken(user);

        // TODO: CHANGE THE URL TO BE SHIFT-ER.COM NOT LOCALHOST
        String verificationUrl = "http://localhost:5173/welcome?token=" + token;
        emailService.sendVerificationToken(user.getEmail(), verificationUrl);
    }

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

    public void personalize(UserPersonalizationDto userPersonalizationDto, HttpServletResponse response) throws IOException {
        User user = userService.createUser(userPersonalizationDto);
        sendTokens(response, user);
    }

    public void authenticate(LoginDto request, HttpServletResponse response) throws IOException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.getUserEntityByEmail(request.getEmail());
        if (user.getIsEnabled())
            sendTokens(response, user);
    }


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
        User user = userService.getUserEntityByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .user(userMapper.toDto(user))
                .build();
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public boolean checkEmail(String email) {
        return userService.existsUserByEmail(email);
    }

}
