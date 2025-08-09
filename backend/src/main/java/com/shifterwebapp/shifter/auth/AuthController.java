package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterDto request, HttpServletResponse response) throws IOException {
        authService.register(request, response);
    }

    @PostMapping("/authenticate")
    public void authenticate(@RequestBody LoginDto request, HttpServletResponse response) throws IOException {
        authService.authenticate(request, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = authService.checkEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long accountId) {
        userService.deleteUser(accountId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
