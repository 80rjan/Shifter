package com.shifterwebapp.shifter.enrollment.mapper;

import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.dto.EnrollmentDto;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgressMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserCourseProgressMapper.class})
public abstract class EnrollmentMapper {

    @Mapping(source = "courseVersion.id", target = "courseVersionId")
    public abstract EnrollmentDto toDto(Enrollment enrollment);
    public abstract List<EnrollmentDto> toDto(List<Enrollment> enrollment);

    @InheritInverseConfiguration
    public abstract Enrollment toEntity(EnrollmentDto enrollmentDto);
     @InheritInverseConfiguration
    public abstract List<Enrollment> toEntity(List<EnrollmentDto> enrollmentDtos);

     @AfterMapping
    protected void afterMapping(Enrollment enrollment, @MappingTarget EnrollmentDto enrollmentDto) {
        if (enrollment.getUserCourseProgresses() != null && !enrollment.getUserCourseProgresses().isEmpty()) {
            int completedCount = (int) enrollment.getUserCourseProgresses().stream()
                    .filter(UserCourseProgress::isCompleted)
                    .count();
            int totalCount = enrollment.getUserCourseProgresses().size();
            int percentCompleted = (int) ((completedCount / (double) totalCount) * 100);
            enrollmentDto.setPercentCompleted(percentCompleted);
        } else {
            enrollmentDto.setPercentCompleted(0);
        }
     }
}
