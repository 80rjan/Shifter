package com.shifterwebapp.shifter.review;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toDto(Review review);
    List<ReviewDto> toDto(List<Review> review);

    @InheritInverseConfiguration
    Review toEntity(ReviewDto reviewDto);
    @InheritInverseConfiguration
    List<Review> toEntity(List<ReviewDto> reviewDtos);
}

