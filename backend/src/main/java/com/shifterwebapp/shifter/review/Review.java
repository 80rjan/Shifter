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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    private String comment;

    private Boolean canBeUsedAsTestimonial;

    private Date date;

    @OneToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
