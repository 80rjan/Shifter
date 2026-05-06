package com.shifterwebapp.shifter.catalog.domain;

import com.shifterwebapp.shifter.identity.domain.User;
import com.shifterwebapp.shifter.catalog.domain.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// TODO: add indexes
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Slug cannot be null")
    @Column(nullable = false, unique = true)    // TODO: unique in index
    private String slug;

    @Builder.Default
    @ManyToMany(mappedBy = "topics", cascade = {}, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "interestedTopics", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "topic", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false, fetch = FetchType.LAZY) // TODO: fetch type?
    private List<TopicTranslate> translations;
}
