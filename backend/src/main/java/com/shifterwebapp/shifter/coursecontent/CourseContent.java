package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private Integer position;

    @OneToMany(mappedBy = "courseContent", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course content is deleted, lectures are deleted. When lectures are removed from the list, they are deleted.
    @OrderBy("position ASC")
    private List<CourseLecture> courseLectures;

    @ManyToOne
    @JoinColumn(name = "course_version_id")
    private CourseVersion courseVersion;

    @OneToMany(mappedBy = "courseContent", cascade = CascadeType.ALL, orphanRemoval = true)
    // when course content is deleted, translations are deleted. When translations are removed from the list, they are deleted.
    private List<CourseContentTranslate> translations;
}

