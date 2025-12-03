package com.shifterwebapp.shifter.attribute.dto;

import com.shifterwebapp.shifter.attribute.AttributeTranslate;
import com.shifterwebapp.shifter.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDto {

    private AttributeType type;

    List<AttributeTranslateDto> attributeTranslateList;
}
