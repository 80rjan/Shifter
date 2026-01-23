package com.shifterwebapp.shifter.attribute;

import com.shifterwebapp.shifter.attribute.dto.AttributeDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    AttributeDto toDto(Attribute attribute);
    List<AttributeDto> toDto(List<Attribute> attributes);

    @InheritInverseConfiguration
    Attribute toEntity(AttributeDto attributeDto);
    @InheritInverseConfiguration
    List<Attribute> toEntity(List<AttributeDto> attributeDtos);
}
