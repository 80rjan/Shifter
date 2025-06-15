package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.course.service.CourseService;
import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) List<Difficulty> difficulties,
            @RequestParam(required = false) Float floorPrice,
            @RequestParam(required = false) Float ceilPrice,
            @RequestParam(required = false) Float floorHours,
            @RequestParam(required = false) Float ceilHours,
            @RequestParam(required = false) List<Skills> skills
            ) {
        Specification<Course> spec = null;

        if (title != null && !title.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasTitleLike(title) : spec.and(CourseSpecification.hasTitleLike(title));
        }

        if (topic != null && !topic.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasTopicLike(topic) : spec.and(CourseSpecification.hasTopicLike(topic));
        }

        if (difficulties != null && !difficulties.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasDifficulties(difficulties) : spec.and(CourseSpecification.hasDifficulties(difficulties));
        }

        if (floorPrice != null || ceilPrice != null) {
            spec = (spec == null) ? CourseSpecification.hasPriceBetween(floorPrice, ceilPrice) : spec.and(CourseSpecification.hasPriceBetween(floorPrice, ceilPrice));
        }

        if (floorHours != null || ceilHours != null) {
            spec = (spec == null) ? CourseSpecification.hasHoursBetween(floorHours, ceilHours) : spec.and(CourseSpecification.hasHoursBetween(floorHours, ceilHours));
        }

        if (skills != null && !skills.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasSkills(skills) : spec.and(CourseSpecification.hasSkills(skills));
        }

        List<CourseDto> courseDtos = courseService.getAllCourses(spec);

        return ResponseEntity.ok(courseDtos);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable("courseId") Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseDto);
    }

}
