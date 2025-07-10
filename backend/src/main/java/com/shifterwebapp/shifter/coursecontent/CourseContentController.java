package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.coursecontent.service.CourseContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/course-content")
@CrossOrigin
public class CourseContentController {

    private final CourseContentService courseContentService;

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseContent(@PathVariable Long courseId) {
        List<CourseContentDto> courseContents = courseContentService.getCourseContentByCourseId(courseId);
        return ResponseEntity.ok(courseContents);
    }
}
