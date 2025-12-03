package com.shifterwebapp.shifter.attribute;

import com.shifterwebapp.shifter.enums.Language;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeTranslate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Language language; // EN, MK

    private String value; // "Leadership", "Маркетинг", etc.

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
