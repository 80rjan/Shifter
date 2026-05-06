package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.catalog.domain.enums.CourseActivityEventType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: add indexes
public class CourseActivityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Event type cannot be null")
    @Column(nullable = false)
    private CourseActivityEventType eventType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // optional add other attributes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
