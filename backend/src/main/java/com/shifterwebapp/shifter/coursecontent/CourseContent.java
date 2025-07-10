package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import jakarta.persistence.*;
import com.shifterwebapp.shifter.enums.ContentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CourseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer position;

    @OneToMany(mappedBy = "courseContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseLecture> courseLectures;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

