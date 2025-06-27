package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.coursecontent.CourseContentDto;
import com.shifterwebapp.shifter.enums.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private Long id;

    private String title;

    private String topic;

    private Difficulty difficulty;

    private Float durationHours;

    private Float price;

    private Integer rating;

    private Integer ratingCount;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<Skills> skillsGained;

    private List<String> whatWillBeLearned;

    // DO I NEED THIS ???
    private List<CourseContentDto> courseContents;
}

