package com.shifterwebapp.shifter.collection.domain.learningpath;

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
public class CuratedLearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Builder.Default
    @ManyToMany(mappedBy = "curatedLearningPaths", fetch = FetchType.LAZY)
    private List<Expert> experts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @Builder.Default
    @OneToMany(mappedBy = "source", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<PersonalizedLearningPath> personalizedLearningPaths = new ArrayList<>();
}
