package com.shifterwebapp.shifter.tag;

import com.shifterwebapp.shifter.tag.dto.TagDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);
    List<TagDto> toDto(List<Tag> tags);

    @InheritInverseConfiguration
    Tag toEntity(TagDto tagDto);
    @InheritInverseConfiguration
    List<Tag> toEntity(List<TagDto> tagDtos);
}
