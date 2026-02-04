package com.shifterwebapp.shifter.tag;

import com.shifterwebapp.shifter.enums.Language;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tag_translate_tag_language",
                        columnNames = {"tag_id", "language"}        // each tag can have only one translation per language
                )
        }
)
public class TagTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;          // EN, MK

    @Column(nullable = false)
    private String value;               // "Leadership", "Маркетинг", etc.

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
}
