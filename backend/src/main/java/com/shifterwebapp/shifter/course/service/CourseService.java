package com.shifterwebapp.shifter.course.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.course.dto.CourseDtoDetail;
import com.shifterwebapp.shifter.course.dto.CourseDtoFull;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreview;
import com.shifterwebapp.shifter.course.dto.CourseDtoPreviewEnrolled;
import com.shifterwebapp.shifter.course.mapper.CourseMapper;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.external.PdfManipulationService;
import com.shifterwebapp.shifter.external.upload.MetaInfo;
import com.shifterwebapp.shifter.external.upload.S3Service;
import com.shifterwebapp.shifter.external.upload.S3UploadResponse;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CourseService implements ImplCourseService {

    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    private final PdfManipulationService pdfService;
    private final CourseMapper courseMapper;
    private final UserService userService;
    private final Validate validate;
    private final EnrollmentService enrollmentService;
    private final CourseDtoBuilder courseDtoBuilder;


    @Override
    public Course createCourse(String courseJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Course course = objectMapper.readValue(courseJson, Course.class);

        for (CourseContent content : course.getCourseContents()) {
            content.setCourse(course);

            for (CourseLecture lecture : content.getCourseLectures()) {
                lecture.setCourseContent(content);
            }
        }

        return courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Course updateCourseWithImagesAndFiles(Long courseId, List<S3UploadResponse> s3UploadResponses) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();

        for (S3UploadResponse s3UploadResponse : s3UploadResponses) {
            if ("COURSE_IMAGE".equals(s3UploadResponse.getType())) {
                course.setImageUrl(s3UploadResponse.getFileName());
                continue;
            }

            MetaInfo meta = s3UploadResponse.getMeta();
            Integer contentPosition = meta.getContentPosition();
            Integer lecturePosition = meta.getLecturePosition();

            if (contentPosition != null && lecturePosition != null) {
                int contentIndex = contentPosition - 1;
                int lectureIndex = lecturePosition - 1;

                if (contentIndex >= 0 && contentIndex < course.getCourseContents().size()) {
                    var courseContent = course.getCourseContents().get(contentIndex);
                    if (lectureIndex >= 0 && lectureIndex < courseContent.getCourseLectures().size()) {
                        CourseLecture courseLecture = courseContent.getCourseLectures().get(lectureIndex);

                        courseLecture.setContentFileName(s3UploadResponse.getFileName());
                    } else {
                        // handle invalid lecture index
                        System.err.println("Invalid lecture index: " + lectureIndex);
                    }
                } else {
                    // handle invalid content index
                    System.err.println("Invalid content index: " + contentIndex);
                }
            }
        }
        return courseRepository.save(course);
    }

    @Override
    public Boolean lectureFileExistsInCourse(Long courseId, String fileName) {
        return courseRepository.lectureFileExistsInCourse(courseId, fileName);
    }

    @Override
    public CourseDtoFull getEnrolledCourseById(Long courseId, Long userId) {
        validate.validateCourseExists(courseId);

        boolean isUserEnrolled = enrollmentService.isUserEnrolledInCourse(userId, courseId);
        if (!isUserEnrolled) {
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the full course with its content!");
        }

        Enrollment enrollment = enrollmentService.getEnrollmentByUserAndCourse(userId, courseId);
        if (enrollment == null) {
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the full course with its content!");
        }
        enrollmentService.updateEnrollmentStatusToActive(enrollment);
        Course course = courseRepository.findById(courseId).orElseThrow();

        return courseDtoBuilder.getCourseDtoFull(course, enrollment);
    }

    public byte[] downloadCertificate(Long courseId, Long userId) throws Exception {
        validate.validateCourseExists(courseId);
        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId))
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the course certificate!");

        String courseTitle = getCourseById(courseId).getTitleShort();
        String userName = userService.getUserById(userId).getName();
        LocalDate completedDate = enrollmentService.getEnrollmentByUserAndCourse(userId, courseId).getCompletedAt();

        // TODO: uncomment this and check if logic for date is okay
//        if (completedDate == null)
//            throw new AccessDeniedException("User with ID " + userId + " has not yet completed course with ID " + courseId + " and is therefore not authorized to access the course certificate!");

        String date = (completedDate != null ? completedDate : LocalDate.now()).format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH));

        // 1. Define the S3 key for your template PDF
        String s3Key = "private/Shifter_Certificate.pdf";

        // 2. Fetch the template PDF from S3 as an InputStream
        try (InputStream templateStream = s3Service.downloadFile(s3Key)) {

            // 3. Modify the PDF with the dynamic data
            byte[] personalizedPdf = pdfService.fillPdfForm(
                    templateStream,
                    userName,
                    "This is to certify that",
                    "has successfully completed the course " + courseTitle + " through Shifter, demonstrating commitment to continuous learning and professional growth.",
                    date,
                    courseTitle
            );

            return personalizedPdf;
        }
    }

    @Override
    public List<CourseDtoPreview> getAllCourses(List<Long> courseIds) {
        List<Course> courses = courseIds != null && !courseIds.isEmpty() ?
                courseRepository.findCoursesByIdNotIn(courseIds) :
                courseRepository.findAll();
        return courseDtoBuilder.getCourseDtoPreview(courses);
    }

//    @Override
//    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
//        List<Course> courses = specification == null ?
//                courseRepository.findAll() :
//                courseRepository.findAll(specification);
//        return courseMapperPreview.toDto(courses);
//    }

    @Override
    public List<CourseDtoPreview> getRecommendedCourses(Long userId) {
        UserDto user = userService.getUserById(userId);
        List<String> skills = user.getSkills();
        List<String> interests = user.getInterests();

        // get user enrollments
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);

        // filter out enrolled courses
        List<Course> courses = courseRepository.findAll()
                .stream()
                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                .toList();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : courses) {
            boolean matchesSkills = course.getSkillsGained().stream().anyMatch(skills::contains);
            boolean matchesTopics = course.getTopicsCovered().stream().anyMatch(interests::contains);

            int score = 0;
            if (matchesSkills && matchesTopics) {
                score += 2;
            } else if (matchesSkills || matchesTopics) {
                score += 1;
            }

            scoredCourses.add(new ScoredCourse(course, score));
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));  // descending order

        int limit = Math.min(5, scoredCourses.size());
        return scoredCourses
                .subList(0, limit)
                .stream()
                .map(ScoredCourse::getCourse)
                .map(courseDtoBuilder::getCourseDtoPreview)
                .toList();
    }

    @Override
    public List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId) {
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
        List<Course> courses = courseRepository.findAllById(enrolledCourseIds);

        if (courses.isEmpty()) {
            return new ArrayList<>();
        }
        return courseDtoBuilder.getCourseDtoPreviewEnrolled(courses, userId);
    }


    @Override
    public List<CourseDtoPreview> getTopRatedCourses() {
        List<Course> courses = courseRepository.findCoursesOrderedByRating();
        int limit = Math.min(5, courses.size());
        return courseDtoBuilder.getCourseDtoPreview(
                courses
                        .subList(0, limit)
        );
    }

    @Override
    public List<CourseDtoPreview> getMostPopularCourses() {
        List<Course> courses = courseRepository.findCoursesOrderedByPopularity();
        int limit = Math.min(5, courses.size());
        return courseDtoBuilder.getCourseDtoPreview(
                courses
                        .subList(0, limit)
        );
    }

    @Override
    public CourseDtoDetail getCourseById(Long courseId) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseDtoBuilder.getCourseDtoDetail(course);
    }

    @Override
    public Course getCourseEntityById(Long courseId) {
        validate.validateCourseExists(courseId);
        return courseRepository.findById(courseId).orElseThrow();
    }

    @Override
    public List<String> getAllTopics() {
        return courseRepository.getCourseTopics();
    }

    @Override
    public List<String> getAllSkills() {
        return courseRepository.getCourseSkills();
    }


}
