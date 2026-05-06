package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
import com.shifterwebapp.shifter.catalog.domain.CourseLecture;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseLectureTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "text")
    private String contentFileName;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(columnDefinition = "text")
    private String contentText;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_lecture_id", nullable = false)
    private CourseLecture courseLecture;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
