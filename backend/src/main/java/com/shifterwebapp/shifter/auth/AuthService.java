package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.config.JwtService;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserMapper;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterDto request) {
        // Create user in the database
        User user = userService.createUser(request);

        // Generate token
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        System.out.println("Generated access token: " + accessToken);
        System.out.println("Generated refresh token: " + refreshToken);

        // Return response with tokens and user details
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(user))
                .build();
    }

    public AuthResponse authenticate(LoginDto request) {
        // Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Load userDetails and generate token
        User user = userService.getUserByEmail(request.getEmail());
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        System.out.println(user);
        System.out.println("------------------------------------------");
        System.out.println(userMapper.toDto(user));

        // Return response with tokens and user details
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toDto(user))
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userService.getUserByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Expired or invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // Keep same refresh token or rotate
                .user(userMapper.toDto(user))
                .build();
    }

}
