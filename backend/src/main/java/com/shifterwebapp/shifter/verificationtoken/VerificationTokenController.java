package com.shifterwebapp.shifter.verificationtoken;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.service.UserService;
import com.shifterwebapp.shifter.verificationtoken.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.base.path}/verification-tokens")
@RequiredArgsConstructor
@CrossOrigin
public class VerificationTokenController {

    private final Validate validate;
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/{token}/verify")
    public ResponseEntity<String> verify(@PathVariable("token") String token) {
        String userEmail = verificationTokenService.verify(token);
        return ResponseEntity.ok(userEmail);
    }

    @PostMapping()
    public ResponseEntity<UUID> createNewToken(Authentication authentication) {
        Long userId = validate.extractUserId(authentication);
        User user = userService.getEntityById(userId);

        UUID uuid = verificationTokenService.generateNewToken(user);
        return ResponseEntity.ok(uuid);
    }

    @PostMapping("/email")
    public ResponseEntity<?> verifyEmail(Authentication authentication) {
        Long userId = validate.extractUserId(authentication);
        User user = userService.getEntityById(userId);

        UUID token = verificationTokenService.generateNewToken(user);

        String verificationUrl = frontendUrl + "/welcome?token=" + token;
        emailService.sendVerificationToken(user.getEmail(), verificationUrl);

        return ResponseEntity.ok("Successfully sent verification email to user.");
    }
}
