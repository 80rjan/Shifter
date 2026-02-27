package com.shifterwebapp.shifter.config;

import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import com.shifterwebapp.shifter.account.expert.repository.ExpertRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

    private final String SECRET_KEY;
    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public JwtService(@Value("${jwt.secret}") String secretKey,
                      UserRepository userRepository,
                      ExpertRepository expertRepository) {
        this.SECRET_KEY = secretKey;
        this.userRepository = userRepository;
        this.expertRepository = expertRepository;
    }

    // Helper to get SecretKey instance
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Generate Access Token
    public String generateToken(UserDetails userDetails) {
        String email = userDetails.getUsername();
        Map<String, Object> claims = new HashMap<>();

        // Extract role from UserDetails authorities
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER"); // Default to USER if no authority found

        claims.put("role", role); // Will be "EXPERT" or "USER"

        // Get userId - check both tables
        if (role.equals("EXPERT")) {
            var expertOpt = expertRepository.findByEmail(email);
            if (expertOpt.isPresent()) {
                claims.put("userId", expertOpt.get().getId());
            } else {
                throw new UsernameNotFoundException("Expert not found");
            }
        } else if (role.equals("USER")) {
             var userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                claims.put("userId", userOpt.get().getId());
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        } else {
            throw new IllegalStateException("Unknown role for jwt: " + role);
        }

        return createToken(claims, email);
    }

    // Generate Refresh Token (30 days)
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Create Access Token with optional claims
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check expiration
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    // Extract role from token
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    // Helper method to check if user is expert from token
    public boolean isExpert(String token) {
        return "EXPERT".equals(extractRole(token));
    }

    // Extract single claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims using correct parser
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}