package com.shifterwebapp.shifter.collection.domain.bundle;

import com.shifterwebapp.shifter.identity.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class UserBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate acquiredDate;

    //---------MAPPINGS---------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private Bundle bundle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        acquiredDate = LocalDate.now();
    }
}
