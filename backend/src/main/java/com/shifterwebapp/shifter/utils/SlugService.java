 package com.shifterwebapp.shifter.utils;

import com.shifterwebapp.shifter.course.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 @Service
@RequiredArgsConstructor
public class SlugService {

    private final CourseRepository courseRepository; // Inject the repository to fetch the title

    @Transactional(readOnly = true)
    public String getEnCourseTitle(Long courseId) {
        return courseRepository.getEnCourseTitle(courseId);
    }

    public String slugify(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // 1. Replace spaces with hyphens
        String slug = text.trim()
                .replaceAll("&", "and")                     // Replace & with and
                .replaceAll("\\s+", "-").toLowerCase()      // Replace space with hyphens
                .replaceAll("[^a-z0-9-]", "")               // Remove all characters that are not a-z, 0-9, or a hyphen
                .replaceAll("[-]+", "-")                    // Remove consecutive hyphens
                .replaceAll("^-|-$", "");                   // Remove leading/trailing hyphens

        return slug;
    }
}