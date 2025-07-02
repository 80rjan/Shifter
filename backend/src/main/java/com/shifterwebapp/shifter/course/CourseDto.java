package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.coursecontent.CourseContentDto;
import com.shifterwebapp.shifter.enums.Interests;
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

    private String imageUrl;

    private String color;

    private String titleShort;

    private String title;

    private String topic;

    private Difficulty difficulty;

    private Double durationHours;

    private Double price;

    private Integer rating;

    private Integer ratingCount;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<Skills> skillsGained;

    private List<Interests> whatWillBeLearned;

    // DO I NEED THIS ???
//    private List<CourseContentDto> courseContents;
}

