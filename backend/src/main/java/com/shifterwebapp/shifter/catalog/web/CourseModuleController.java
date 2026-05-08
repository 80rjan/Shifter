//package com.shifterwebapp.shifter.course.controllers;
//
//import com.shifterwebapp.shifter.shared.domain.Language;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModulePreviewResponse;
//import com.shifterwebapp.shifter.catalog.application.CourseModuleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("${api.base.path}/course-content")
//@CrossOrigin
//public class CourseModuleController {
//
//    private final CourseModuleService courseModuleService;
//
//    @GetMapping("/{courseId}")
//    public ResponseEntity<?> getCourseContent(
//            @PathVariable Long courseId,
//            @RequestParam(defaultValue = "EN") LanguageCode language
//            ) {
//        List<CourseModulePreviewResponse> courseContents = courseModuleService.getCourseContentByCourseId(courseId, language);
//        return ResponseEntity.ok(courseContents);
//    }
//}
