package com.shifterwebapp.shifter.course;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("${api.base.path}/courses")
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, name = "difficulty") List<Difficulty> difficulties,
            @RequestParam(required = false, name = "price") List<String> prices,
            @RequestParam(required = false, name = "duration") List<String> durations,
            @RequestParam(required = false, name = "skill") List<Skills> skills,
            @RequestParam(required = false, name = "topic") List<Interests> topics
            ) throws JsonProcessingException {
        Specification<Course> spec = null;


        if (search != null && !search.isEmpty()) {
            spec = CourseSpecification.hasSearchLike(search);
        }

        if (difficulties != null && !difficulties.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasDifficulties(difficulties) : spec.and(CourseSpecification.hasDifficulties(difficulties));
        }

        if (prices != null && !prices.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasPricesBetween(prices) : spec.and(CourseSpecification.hasPricesBetween(prices));
        }

        if (durations != null && !durations.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasDurationBetween(durations) : spec.and(CourseSpecification.hasDurationBetween(durations));
        }

        if (skills != null && !skills.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasSkills(skills) : spec.and(CourseSpecification.hasSkills(skills));
        }

        if (topics != null && !topics.isEmpty()) {
            spec = (spec == null) ? CourseSpecification.hasTopics(topics) : spec.and(CourseSpecification.hasTopics(topics));
        }

        List<CourseDto> courseDtos = courseService.getAllCourses(spec);

        return ResponseEntity.ok(courseDtos);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable("courseId") Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        return ResponseEntity.ok(courseDto);
    }

    @GetMapping("/topics")
    public ResponseEntity<List<Interests>> getAllTopics() {
        return ResponseEntity.ok(courseService.getAllTopics());
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Skills>> getAllSkills() {
        return ResponseEntity.ok(courseService.getAllSkills());
    }
}
