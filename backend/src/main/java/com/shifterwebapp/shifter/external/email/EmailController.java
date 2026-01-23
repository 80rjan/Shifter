package com.shifterwebapp.shifter.external.email;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/emails")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final Validate validate;

    @PostMapping("/contact-us")
    public ResponseEntity<?> sendEmailToExpert(@RequestBody ContactReq contactReq, Authentication authentication) {
        Long userId = validate.extractUserId(authentication);
        String userEmail = userService.getUserEmailById(userId);

        emailService.contactExpert(userEmail, contactReq);
        return ResponseEntity.ok().build();
    }
}
