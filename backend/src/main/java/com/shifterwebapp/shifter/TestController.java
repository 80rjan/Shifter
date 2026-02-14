package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.external.email.EmailService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/test")
public class TestController {

    private final EmailService emailService;

    @Autowired
    private DataSource dataSource;

    @Value("${frontend.url}")
    private String frontendUrl;

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

    @GetMapping("/send-verification-email")
    public void sendVerificationEmail() {
        emailService.sendVerificationToken(
                "borjangjorgjievski1@gmail.com",
                frontendUrl
        );
    }

    @GetMapping("/pool-status")
    public Map<String, Object> getPoolStatus() {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;

        Map<String, Object> status = new HashMap<>();
        status.put("poolName", hikariDataSource.getPoolName());
        status.put("activeConnections", hikariDataSource.getHikariPoolMXBean().getActiveConnections());
        status.put("idleConnections", hikariDataSource.getHikariPoolMXBean().getIdleConnections());
        status.put("totalConnections", hikariDataSource.getHikariPoolMXBean().getTotalConnections());
        status.put("threadsAwaitingConnection", hikariDataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());

        return status;
    }

    @GetMapping
    public String testEndpoint() {
        return "Test endpoint is working!";
    }
}
