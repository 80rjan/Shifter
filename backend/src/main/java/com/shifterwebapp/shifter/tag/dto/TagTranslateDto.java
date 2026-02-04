package com.shifterwebapp.shifter.tag.dto;

import com.shifterwebapp.shifter.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagTranslateDto {

    private Language language;

    private String value;

}
