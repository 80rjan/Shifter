package com.shifterwebapp.shifter.catalog.web.response;

import com.shifterwebapp.shifter.shared.domain.enums.Difficulty;
import com.shifterwebapp.shifter.shared.domain.enums.LanguageCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseFullResponse {
    // Course entity
    private Long id;

    private String imageUrl;

    private String color;

    private Difficulty difficulty;

    private Double price;

    private Integer durationMinutes;

    // Language specific
    private LanguageCode language;

    private String titleShort;

    private String title;

    private String descriptionShort;

    private String description;

    private String descriptionLong;

    private List<String> whatWillBeLearned;

    private List<String> skillsGained;

    private List<String> topicsCovered;

    private List<CourseModuleFullResponse> courseContents;

}
