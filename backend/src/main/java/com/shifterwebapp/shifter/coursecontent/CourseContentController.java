package com.shifterwebapp.shifter.coursecontent;

import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoPreview;
import com.shifterwebapp.shifter.coursecontent.service.CourseContentService;
import com.shifterwebapp.shifter.enums.Language;
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
    public ResponseEntity<?> getCourseContent(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "EN") Language language
            ) {
        List<CourseContentDtoPreview> courseContents = courseContentService.getCourseContentByCourseId(courseId, language);
        return ResponseEntity.ok(courseContents);
    }
}
