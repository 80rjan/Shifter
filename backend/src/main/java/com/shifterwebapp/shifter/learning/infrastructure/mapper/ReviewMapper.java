//package com.shifterwebapp.shifter.review;
//
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface ReviewMapper {
//
//    ReviewResponse toDto(Review review);
//    List<ReviewResponse> toDto(List<Review> review);
//
//    @InheritInverseConfiguration
//    Review toEntity(ReviewResponse reviewDto);
//    @InheritInverseConfiguration
//    List<Review> toEntity(List<ReviewResponse> reviewDtos);
//}
//
