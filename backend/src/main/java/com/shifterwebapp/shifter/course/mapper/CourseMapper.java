package com.shifterwebapp.shifter.course.mapper;

import com.shifterwebapp.shifter.course.Course;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreviewEnrolled;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    // ---- Forward mappings ----
    CourseDtoPreview toDtoPreview(Course course);
    List<CourseDtoPreview> toDtoPreview(List<Course> courses);

    CourseDtoDetail toDtoDetail(Course course);
    List<CourseDtoDetail> toDtoDetail(List<Course> courses);

    CourseDtoPreviewEnrolled toDtoEnrolled(Course course);
    List<CourseDtoPreviewEnrolled> toDtoEnrolled(List<Course> courses);

    CourseDtoFull toDtoFull(Course course);
    List<CourseDtoFull> toDtoFull(List<Course> courses);

    // ---- Inverse mappings (explicitly reference which to invert) ----
    @InheritInverseConfiguration(name = "toDtoPreview")
    Course toEntityPreview(CourseDtoPreview courseDto);
    @InheritInverseConfiguration(name = "toDtoPreview")
    List<Course> toEntityPreview(List<CourseDtoPreview> courseDtos);

    @InheritInverseConfiguration(name = "toDtoDetail")
    Course toEntityDetail(CourseDtoDetail courseDto);
    @InheritInverseConfiguration(name = "toDtoDetail")
    List<Course> toEntityDetail(List<CourseDtoDetail> courseDtos);

    @InheritInverseConfiguration(name = "toDtoEnrolled")
    Course toEntityEnrolled(CourseDtoPreviewEnrolled courseDto);
    @InheritInverseConfiguration(name = "toDtoEnrolled")
    List<Course> toEntityEnrolled(List<CourseDtoPreviewEnrolled> courseDtos);

    @InheritInverseConfiguration(name = "toDtoFull")
    Course toEntityFull(CourseDtoFull courseDto);
    @InheritInverseConfiguration(name = "toDtoFull")
    List<Course> toEntityFull(List<CourseDtoFull> courseDtos);
}


