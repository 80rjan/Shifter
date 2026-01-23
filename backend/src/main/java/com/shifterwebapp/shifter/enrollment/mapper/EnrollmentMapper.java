package com.shifterwebapp.shifter.enrollment.mapper;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.dto.EnrollmentDto;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserCourseProgressMapper.class})
public interface EnrollmentMapper {

    @Mapping(source = "courseVersion.id", target = "courseVersionId")
    EnrollmentDto toDto(Enrollment enrollment);
    List<EnrollmentDto> toDto(List<Enrollment> enrollment);

    @InheritInverseConfiguration
    Enrollment toEntity(EnrollmentDto enrollmentDto);
    @InheritInverseConfiguration
    List<Enrollment> toEntity(List<EnrollmentDto> enrollmentDtos);
}
