//package com.shifterwebapp.shifter.learning.domain.mapper;
//
//import com.shifterwebapp.shifter.learning.domain.Enrollment;
//import com.shifterwebapp.shifter.learning.web.response.EnrollmentDto;
//import com.shifterwebapp.shifter.catalog.domain.LectureProgress;
//import com.shifterwebapp.shifter.catalog.infrastructure.mapper.LectureProgressMapper;
//import org.mapstruct.*;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", uses = {LectureProgressMapper.class})
//public abstract class EnrollmentMapper {
//
//    @Mapping(source = "courseVersion.id", target = "courseVersionId")
//    public abstract EnrollmentDto toDto(Enrollment enrollment);
//    public abstract List<EnrollmentDto> toDto(List<Enrollment> enrollment);
//
//    @InheritInverseConfiguration
//    public abstract Enrollment toEntity(EnrollmentDto enrollmentDto);
//     @InheritInverseConfiguration
//    public abstract List<Enrollment> toEntity(List<EnrollmentDto> enrollmentDtos);
//
//     @AfterMapping
//    protected void afterMapping(Enrollment enrollment, @MappingTarget EnrollmentDto enrollmentDto) {
//        if (enrollment.getLectureProgresses() != null && !enrollment.getLectureProgresses().isEmpty()) {
//            int completedCount = (int) enrollment.getLectureProgresses().stream()
//                    .filter(LectureProgress::isCompleted)
//                    .count();
//            int totalCount = enrollment.getLectureProgresses().size();
//            int percentCompleted = (int) ((completedCount / (double) totalCount) * 100);
//            enrollmentDto.setPercentCompleted(percentCompleted);
//        } else {
//            enrollmentDto.setPercentCompleted(0);
//        }
//     }
//}
