package com.shifterwebapp.shifter.course.courseversion.service;

import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.course.courseversion.repository.CourseVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseVersionService implements ImplCourseVersionService {

    private final CourseVersionRepository courseVersionRepository;

    @Override
    public CourseVersion getActiveByCourseId(Long courseId) {
        return courseVersionRepository.findByActiveTrueAndCourse_Id(courseId);
    }

    @Override
    public Map<Long, CourseVersion> getActiveByCourseIds(List<Long> courseIds) {
        return courseVersionRepository.findByActiveTrueAndCourse_IdIn(courseIds)
                .stream()
                .collect(Collectors.toMap(
                        cv -> cv.getCourse().getId(),
                        Function.identity()
                ));
    }
}
