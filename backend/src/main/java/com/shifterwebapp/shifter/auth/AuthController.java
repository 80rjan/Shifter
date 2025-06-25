package com.shifterwebapp.shifter.auth;

import com.shifterwebapp.shifter.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/auth")
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto request) {
        accountService.createAccount(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAll(@RequestParam Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("User registered successfully");
    }
}
