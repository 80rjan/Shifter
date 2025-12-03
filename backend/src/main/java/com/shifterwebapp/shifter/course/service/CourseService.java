package com.shifterwebapp.shifter.course.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.AttributeTranslate;
import com.shifterwebapp.shifter.attribute.repository.AttributeRepository;
import com.shifterwebapp.shifter.attribute.service.AttributeService;
import com.shifterwebapp.shifter.course.*;
import com.shifterwebapp.shifter.course.dto.*;
import com.shifterwebapp.shifter.course.mapper.CourseMapper;
import com.shifterwebapp.shifter.course.repository.CourseRepository;
import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.dto.CourseContentDtoTranslate;
import com.shifterwebapp.shifter.coursecontent.CourseContentTranslate;
import com.shifterwebapp.shifter.coursecontent.service.CourseContentService;
import com.shifterwebapp.shifter.courselecture.CourseLecture;
import com.shifterwebapp.shifter.courselecture.dto.CourseLectureDtoTranslate;
import com.shifterwebapp.shifter.courselecture.CourseLectureTranslate;
import com.shifterwebapp.shifter.courselecture.service.CourseLectureService;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.external.PdfManipulationService;
import com.shifterwebapp.shifter.external.upload.MetaInfo;
import com.shifterwebapp.shifter.external.upload.S3Service;
import com.shifterwebapp.shifter.external.upload.S3UploadResponse;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
    private final UserService userService;
    private final Validate validate;
    private final EnrollmentService enrollmentService;
    private final CourseDtoBuilder courseDtoBuilder;


    @Override
    public CourseDtoLearn getEnrolledCourseById(Long courseId, Long userId, Language language) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentService.getEnrollmentByUserAndCourse(userId, courseId);
        if (enrollment == null) {
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the full course with its content!");
        }

        enrollmentService.updateEnrollmentStatusToActive(enrollment);
        Course course = courseRepository.findById(courseId).orElseThrow();

        return courseDtoBuilder.getCourseDtoLearn(course, enrollment, language);
    }


    public byte[] downloadCertificate(Long courseId, Long userId) throws Exception {
        validate.validateCourseExists(courseId);
        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId))
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the course certificate!");

        String courseTitle = getCourseById(courseId, Language.EN).getTitleShort();
        String userName = userService.getUserEntityById(userId).getName();
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
    public List<CourseDtoPreview> getAllCourses(List<Long> courseIds, Language language) {
        List<Course> courses = courseIds != null && !courseIds.isEmpty() ?
                courseRepository.findCoursesByIdNotInAndLanguage(courseIds, language) :
                courseRepository.findCoursesByLanguage(language);

        return courseDtoBuilder.getCourseDtoPreview(courses, language);
    }

//    @Override
//    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
//        List<Course> courses = specification == null ?
//                courseRepository.findAll() :
//                courseRepository.findAll(specification);
//        return courseMapperPreview.toDto(courses);
//    }

    @Override
    public List<CourseDtoPreview> getRecommendedCourses(Long userId, Language language) {
        User user = userService.getUserEntityById(userId);

        // get user enrollments
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);

        // filter out enrolled courses
        List<Course> courses = courseRepository.findAll()
                .stream()
                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                .toList();

        List<Attribute> userAttributes = user.getAttributes().stream().filter(a -> a.getType().equals(AttributeType.TOPIC)).toList();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : courses) {
            List<Attribute> courseAttributes = course.getAttributes().stream().filter(a -> a.getType().equals(AttributeType.TOPIC)).toList();

            int score = courseAttributes.stream()
                    .filter(userAttributes::contains)
                    .toList()
                    .size();

            scoredCourses.add(new ScoredCourse(course, score));
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));  // descending order

        int limit = Math.min(5, scoredCourses.size());
        return scoredCourses
                .subList(0, limit)
                .stream()
                .map(ScoredCourse::getCourse)
                .map(course -> courseDtoBuilder.getCourseDtoPreview(course, language))
                .toList();
    }

    @Override
    public List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId, Language language) {
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);
        List<Course> courses = courseRepository.findAllById(enrolledCourseIds);

        if (courses.isEmpty()) {
            return new ArrayList<>();
        }
        return courseDtoBuilder.getCourseDtoPreviewEnrolled(courses, userId, language);
    }


    @Override
    public List<CourseDtoPreview> getTopRatedCourses(Language language) {
        List<Course> courses = courseRepository.findCoursesOrderedByRating();
        int limit = Math.min(5, courses.size());
        return courseDtoBuilder.getCourseDtoPreview(
                courses.subList(0, limit),
                language
        );
    }

    @Override
    public List<CourseDtoPreview> getMostPopularCourses(Language language) {
        List<Course> courses = courseRepository.findCoursesOrderedByPopularity();
        int limit = Math.min(5, courses.size());
        return courseDtoBuilder.getCourseDtoPreview(
                courses.subList(0, limit),
                language
        );
    }

    @Override
    public CourseDtoDetail getCourseById(Long courseId, Language language) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseDtoBuilder.getCourseDtoDetail(course, language);
    }

    @Override
    public Course getCourseEntityById(Long courseId) {
        validate.validateCourseExists(courseId);
        return courseRepository.findById(courseId).orElseThrow();
    }

    @Override
    public Course getCourseEntityByLectureId(Long lectureId) {
        validate.validateLectureExists(lectureId);

        return courseRepository.findByLectureId(lectureId);
    }

    @Override
    public List<String> getAllTopics(Language language) {
        List<AttributeTranslate> translations = courseRepository.getCourseTopics(language);
        return translations.stream()
                .map(t -> t.getAttribute().getId() + "_" + t.getValue())
                .toList();
    }

    @Override
    public List<String> getAllSkills(Language language) {
        List<AttributeTranslate> translations = courseRepository.getCourseSkills(language);
        return translations.stream()
                .map(t -> t.getAttribute().getId() + "_" + t.getValue())
                .toList();
    }

}
