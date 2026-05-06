package com.shifterwebapp.shifter.consultation.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.consultation.domain.enums.MeetingEmailReminderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
// TODO: indexes
public class MeetingEmailReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime meetingAt;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Boolean sent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MeetingEmailReminderStatus status = MeetingEmailReminderStatus.PENDING;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String meetingLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
