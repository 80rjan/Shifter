package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.external.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/test")
public class TestController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public void sendTestEmail() {
        emailService.sendCoursePurchaseConfirmation(
                "borjangjorgjievski1@gmail.com",
                "Sales Fundamentals",
                "Test Test",
                "www.google.com",
                "Borjan"
                );
    }


}
