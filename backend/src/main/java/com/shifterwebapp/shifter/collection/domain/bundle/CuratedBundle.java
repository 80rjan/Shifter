package com.shifterwebapp.shifter.collection.domain.bundle;

import com.shifterwebapp.shifter.identity.domain.Expert;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// todo: indexes
public class CuratedBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //---------MAPPINGS---------
    @Builder.Default
    @ManyToMany(mappedBy = "curatedBundles", fetch = FetchType.LAZY)
    private List<Expert> experts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id", nullable = false)
    private Bundle bundle;

    @Builder.Default
    @OneToMany(mappedBy = "source", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PersonalizedBundle> personalizedBundles = new ArrayList<>();
}
