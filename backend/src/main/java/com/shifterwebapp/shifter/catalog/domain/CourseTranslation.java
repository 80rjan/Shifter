package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// todo: add indexes
public class CourseTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String titleShort;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String descriptionShort;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(columnDefinition = "text", nullable = false)
    private String descriptionLong;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "text")
    private List<String> whatWillBeLearned;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;                              // todo: add unique index for pair (language, course)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
