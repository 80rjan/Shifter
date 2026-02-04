package com.shifterwebapp.shifter.tag;

import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.enums.TagType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        indexes = {
                @Index(name = "idx_tag_type", columnList = "type")
        }
)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TagType type;         // SKILL or TOPIC

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagTranslate> translations;

    @ManyToMany(mappedBy = "tags")
    private List<User> users;

    @ManyToMany(mappedBy = "tags")
    private List<Course> courses;
}
