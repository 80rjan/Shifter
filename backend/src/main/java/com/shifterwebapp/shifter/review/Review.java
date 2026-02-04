package com.shifterwebapp.shifter.review;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name = "idx_review_date", columnList = "reviewDate DESC")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDate reviewDate;

    @OneToOne
    @JoinColumn(name = "enrollment_id", unique = true)
    private Enrollment enrollment;
}
