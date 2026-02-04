package com.shifterwebapp.shifter.tag.dto;

import com.shifterwebapp.shifter.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private TagType type;

    List<TagTranslateDto> tagTranslateList;
}
