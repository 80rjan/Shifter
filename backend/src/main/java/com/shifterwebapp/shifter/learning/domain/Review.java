package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.learning.domain.Enrollment;
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
// TODO: add indexes
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDate date;

    //---------MAPPINGS---------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", unique = true)  // TODO: make unique with index
    private Enrollment enrollment;
}
