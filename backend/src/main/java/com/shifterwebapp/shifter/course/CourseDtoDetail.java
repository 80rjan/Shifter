package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.coursecontent.CourseContentDto;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDtoDetail  {

    private Long id;

    private String imageUrl;

    private String color;

    private String titleShort;

    private String title;

    private Difficulty difficulty;

    private Integer durationMinutes;

    private Double price;

    private Integer rating;

    private Integer ratingCount;

    private List<Skills> skillsGained;

    private List<Interests> topicsCovered;

//    NEW FOR DETAILED DTO

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<CourseContentDto> courseContents;
}

