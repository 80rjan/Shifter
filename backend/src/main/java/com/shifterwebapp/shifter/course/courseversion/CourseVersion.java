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
public class CourseVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer versionNumber;

    private LocalDate createdAt;

    private boolean active;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "courseVersion")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "courseVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    // when courseVersion is deleted, courseContents are deleted. When courseContents are removed from the list, they are deleted.
    @OrderBy("position ASC")
    private List<CourseContent> courseContents;
}
