package com.shifterwebapp.shifter.courselecture;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import jakarta.persistence.*;
import com.shifterwebapp.shifter.enums.ContentType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CourseLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private Integer durationMinutes;

    private Integer position;

    @Column(columnDefinition = "text")
    private String contentText;

    @Column(columnDefinition = "text")
    private String contentFileName;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_content_id")
    private CourseContent courseContent;
}

