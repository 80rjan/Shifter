package com.shifterwebapp.shifter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "review_sequence", allocationSize = 1)
    private Integer id;

    private Integer rating;

    private String comment;

    private Boolean canBeUsedAsTestimonial;

    private Date date;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
