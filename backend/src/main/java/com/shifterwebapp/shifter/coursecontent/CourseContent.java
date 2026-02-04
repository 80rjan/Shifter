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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_content_course_version_position",
                        columnNames = {"course_version_id", "position"}        // each course version can have only one content with the same position
                )
        }
)
public class CourseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer position;

    @OneToMany(mappedBy = "courseContent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    // when course content is deleted, lectures are deleted. When lectures are removed from the list, they are deleted.
    @OrderBy("position ASC")
    private List<CourseLecture> courseLectures;

    @ManyToOne
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @OneToMany(mappedBy = "courseContent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    // when course content is deleted, translations are deleted. When translations are removed from the list, they are deleted.
    private List<CourseContentTranslate> translations;
}

