package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.shared.domain.Language;
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
// TODO: add indexes
public class TopicTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;               // TODO: add unique in index for one translate for one topic for one value per language (topic, language, value)

    @NotNull(message = "Description cannot be null")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
