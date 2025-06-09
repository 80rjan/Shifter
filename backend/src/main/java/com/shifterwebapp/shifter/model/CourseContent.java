package com.shifterwebapp.shifter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class CourseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_sequence", allocationSize = 1)
    private Integer id;

    private String title;

    private Integer position;

    private String contentURL;

    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

enum ContentType {
    VIDEO,
    TEXT,
    FILE,
    QUIZ
}
