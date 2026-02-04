package com.shifterwebapp.shifter.scheduledemail;

import com.shifterwebapp.shifter.account.user.User;
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
@Table(
        indexes = {
                @Index(name = "idx_reminder_sent_scheduled", columnList = "sent, scheduledAt"),
                @Index(name = "idx_reminder_user_meeting", columnList = "user_id, meetingAt")
        }
)
public class MeetingEmailReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime meetingAt;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Boolean sent;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String meetingLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
