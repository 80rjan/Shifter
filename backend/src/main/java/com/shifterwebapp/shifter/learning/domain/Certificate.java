package com.shifterwebapp.shifter.learning.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.learning.domain.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: add indexes
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Issue date cannot be null")
    @Column(nullable = false)
    private LocalDate issueDate;

    @NotNull(message = "Certificate url cannot be null")
    @Column(nullable = false)
    private String certificateUrl;

    @NotNull(message = "Certificate number cannot be null")
    @Column(nullable = false)
    private String certificateNumber;                   // TODO: add unique with index

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;                                  // TODO: unique with index for one user to have one certificate for one enrollment (user, enrollment)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDate.now();
    }
}
