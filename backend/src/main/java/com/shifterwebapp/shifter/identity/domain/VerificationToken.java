package com.shifterwebapp.shifter.identity.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
// TODO: add indexes
// TODO: check the token generation and expires at logic
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)  // TODO: make unique with index
    private User user;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @PrePersist
    protected void prePersist() {
        if (token == null) token = UUID.randomUUID();
        createdAt = Instant.now();
        expiresAt = createdAt.plusSeconds(30 * 60); // token expires in 30 mins
    }
}
