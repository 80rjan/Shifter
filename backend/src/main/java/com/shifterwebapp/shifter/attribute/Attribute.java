package com.shifterwebapp.shifter.attribute;

import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.enums.AttributeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttributeType type;         // SKILL or TOPIC

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributeTranslate> translations;

    @ManyToMany(mappedBy = "attributes")
    private List<User> users;

    @ManyToMany(mappedBy = "attributes")
    private List<Course> courses;
}
