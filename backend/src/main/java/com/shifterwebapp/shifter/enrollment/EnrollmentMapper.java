package com.shifterwebapp.shifter.enrollment;

import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserCourseProgressMapper.class})
public interface EnrollmentMapper {

    @Mapping(source = "course.id", target = "courseId")
    EnrollmentDto toDto(Enrollment enrollment);
    List<EnrollmentDto> toDto(List<Enrollment> enrollment);

    @InheritInverseConfiguration
    Enrollment toEntity(EnrollmentDto enrollmentDto);
    @InheritInverseConfiguration
    List<Enrollment> toEntity(List<EnrollmentDto> enrollmentDtos);
}
