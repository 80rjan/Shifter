package com.shifterwebapp.shifter.review;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "review_sequence", allocationSize = 1)
    private Long id;

    private Integer rating;

    private String comment;

    private Boolean canBeUsedAsTestimonial;

    private Date date;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
