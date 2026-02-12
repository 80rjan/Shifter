package com.shifterwebapp.shifter.course.courseversion;

import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_version_course_version",
                        columnNames = {"course_id", "versionNumber"}        // each course can have only one version with the same versionNumber
                )
        },
        indexes = {
        @Index(name = "idx_course_version_course_id", columnList = "course_id"),
        @Index(name = "idx_course_version_active", columnList = "active"),
        @Index(name = "idx_course_version_course_active", columnList = "course_id, active")
})
public class CourseVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer versionNumber;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "courseVersion")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "courseVersion", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    // when courseVersion is deleted, courseContents are deleted. When courseContents are removed from the list, they are deleted.
    @OrderBy("position ASC")
    private List<CourseContent> courseContents;
}
