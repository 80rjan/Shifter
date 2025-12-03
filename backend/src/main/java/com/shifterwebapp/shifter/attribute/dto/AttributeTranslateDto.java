package com.shifterwebapp.shifter.attribute.dto;

import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeTranslateDto {

    private Language language;

    private String value;

}
