package com.shifterwebapp.shifter.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.config.JwtService;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.mapper.UserMapper;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import com.shifterwebapp.shifter.account.user.service.UserService;
import com.shifterwebapp.shifter.verificationtoken.VerificationTokenRepository;
import com.shifterwebapp.shifter.verificationtoken.service.VerificationTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

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

    private void sendTokens(HttpServletResponse response, User user) throws IOException {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Create secure HTTP-only cookie for refresh token
        // TODO: change config here
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)           // true for prod
                .sameSite("Lax")     // Strict for prod
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .user(userMapper.toDtoAuth(user))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), authResponse);
    }


    public void register(String email, String password, HttpServletResponse response) throws IOException {
        User user = userService.createInitialUser(email, password, LoginProvider.LOCAL);

        UUID token = verificationTokenService.generateNewToken(user);

        // TODO: CHANGE THE URL TO BE SHIFT-ER.COM NOT LOCALHOST
        String verificationUrl = "http://localhost:5173/welcome?token=" + token;
        emailService.sendVerificationToken(user.getEmail(), verificationUrl);

        sendTokens(response, user);
    }

    public void personalize(UserPersonalizationDto userPersonalizationDto, HttpServletResponse response) throws IOException {
        User user = userService.personalizeUser(userPersonalizationDto);
        sendTokens(response, user);
    }

    public void authenticate(LoginDto request, HttpServletResponse response) throws IOException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getLoginProvider() != LoginProvider.LOCAL) {
                throw new BadCredentialsException(
                        "This account was registered with " + user.getLoginProvider() +
                                ". Please use the corresponding login method."
                );
            }
        }

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

        User user = userService.getUserEntityByEmail(request.getEmail());

        if (user.isVerified())
            sendTokens(response, user);
    }

    public void finalizeOAuthLogin(Long userId, HttpServletResponse response) throws IOException {
        User user = userService.getUserEntityById(userId);

        if (user.isVerified())
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
                .user(userMapper.toDtoAuth(user))
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
