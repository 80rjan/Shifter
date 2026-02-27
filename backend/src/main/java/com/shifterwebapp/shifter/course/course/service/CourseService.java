package com.shifterwebapp.shifter.course.course.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.course.course.mapper.CourseMapper;
import com.shifterwebapp.shifter.course.courseversion.CourseVersion;
import com.shifterwebapp.shifter.course.courseversion.service.CourseVersionService;
import com.shifterwebapp.shifter.review.service.ReviewService;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.tag.TagTranslate;
import com.shifterwebapp.shifter.course.course.Course;
import com.shifterwebapp.shifter.course.course.dto.*;
import com.shifterwebapp.shifter.course.course.repository.CourseRepository;
import com.shifterwebapp.shifter.enrollment.Enrollment;
import com.shifterwebapp.shifter.enrollment.service.EnrollmentService;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.external.PdfManipulationService;
import com.shifterwebapp.shifter.external.upload.S3Service;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.service.UserService;
import com.shifterwebapp.shifter.tag.service.TagService;
import com.shifterwebapp.shifter.usercourseprogress.UserCourseProgress;
import com.shifterwebapp.shifter.usercourseprogress.service.UserCourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ImplCourseService {

    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    private final PdfManipulationService pdfService;
    private final UserService userService;
    private final Validate validate;
    private final EnrollmentService enrollmentService;
    private final CourseMapper courseMapper;
    private final UserCourseProgressService userCourseProgressService;
    private final ReviewService reviewService;
    private final CourseVersionService courseVersionService;
    private final TagService tagService;

    private record ScoredCourse(Course course, int score) {}

    @Transactional(readOnly = true)
    @Override
    public CourseDtoLearn getEnrolledCourseById(Long courseId, Long userId, Language language) {
        validate.validateCourseExists(courseId);

        Enrollment enrollment = enrollmentService.getEnrollmentByUserAndCourse(userId, courseId);
        if (enrollment == null) {
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the full course with its content!");
        }

        enrollmentService.updateEnrollmentStatusToActive(enrollment);
        Course course = courseRepository.findById(courseId).orElseThrow();

        return courseMapper.toDtoLearn(course, language, enrollment);
    }

    @Transactional(readOnly = true)
    public byte[] downloadCertificate(Long courseId, Long userId) throws Exception {
        validate.validateCourseExists(courseId);
        if (!enrollmentService.isUserEnrolledInCourse(userId, courseId))
            throw new AccessDeniedException("User with ID " + userId + " is not enrolled in course with ID " + courseId + " and is therefore not authorized to access the course certificate!");

        String courseTitle = getCourseById(courseId, Language.EN).getTitleShort();
        String userName = userService.getEntityById(userId).getName();
        LocalDate completedDate = enrollmentService.getEnrollmentByUserAndCourse(userId, courseId).getCompletionDate();

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

    @Transactional(readOnly = true)
    @Override
    public List<CourseDtoPreview> getAllCourses(List<Long> enrolledCourseIds, Language language) {
        List<Course> courses = enrolledCourseIds == null || enrolledCourseIds.isEmpty() ?
                courseRepository.findCoursesByLanguage(language) :
                courseRepository.findCoursesByIdNotInAndLanguage(enrolledCourseIds, language);

        if (courses.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> courseIds = courses.stream()
                .map(Course::getId)
                .toList();

        return courseMapper.toDtoPreview(
                courses,
                language,
                reviewService.getAverageRatingByCourse(courseIds),
                courseVersionService.getActiveByCourseIds(courseIds),
                tagService.getTagsByCourseIdsAndLanguage(courseIds, language)
                );
    }

//    @Override
//    public List<CourseDtoPreview> getAllCourses(Specification<Course> specification) {
//        List<Course> courses = specification == null ?
//                courseRepository.findAll() :
//                courseRepository.findAll(specification);
//        return courseMapperPreview.toDto(courses);
//    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseDtoPreview> getRecommendedCourses(Long userId, Language language) {
        User user = userService.getEntityById(userId);

        // get user enrollments
        List<Long> enrolledCourseIds = enrollmentService.getCourseIdsByUserEnrollments(userId);

        // filter out enrolled courses
        List<Course> courses = courseRepository.findAll()
                .stream()
                .filter(course -> !enrolledCourseIds.contains(course.getId()))
                .toList();

        List<Tag> userTags = user.getTags().stream().filter(a -> a.getType().equals(TagType.TOPIC)).toList();

        List<ScoredCourse> scoredCourses = new ArrayList<>();
        for (Course course : courses) {
            List<Tag> courseTags = course.getTags().stream().filter(a -> a.getType().equals(TagType.TOPIC)).toList();

            int score = courseTags.stream()
                    .filter(userTags::contains)
                    .toList()
                    .size();

            scoredCourses.add(new ScoredCourse(course, score));
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.score(), a.score()));  // descending order

        int limit = Math.min(5, scoredCourses.size());
        scoredCourses = scoredCourses.subList(0, limit);

        List<Long> courseIds =  scoredCourses
                .stream()
                .map(sc -> sc.course().getId())
                .toList();

        return courseMapper.toDtoPreview(
                scoredCourses.stream().map(ScoredCourse::course).toList(),
                language,
                reviewService.getAverageRatingByCourse(courseIds),
                courseVersionService.getActiveByCourseIds(courseIds),
                tagService.getTagsByCourseIdsAndLanguage(courseIds, language)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseDtoPreviewEnrolled> getEnrolledCourses(Long userId, Language language) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsEntityByUser(userId);    // batch fetch enrollments

        if (enrollments.isEmpty()) {
            return new ArrayList<>();
        }

        List<Course> courses = enrollments.stream()
                .map(Enrollment::getCourseVersion)
                .map(CourseVersion::getCourse)
                .toList();

        List<Long> enrollmentIds = enrollments.stream()
                .map(Enrollment::getId)
                .toList();
        Map<Long, List<UserCourseProgress>> progressMap = userCourseProgressService
                .getUserCourseProgressByEnrollmentsAndCompletedTrue(enrollmentIds)  // batch fetch progress
                .stream()
                .collect(Collectors.groupingBy(ucp -> ucp.getEnrollment().getId()));

        Map<Long, Enrollment> enrollmentMap = enrollments.stream()
                .collect(Collectors.toMap(e -> e.getCourseVersion().getCourse().getId(), Function.identity()));

        List<Long> courseIds = courses.stream()
                .map(Course::getId)
                .toList();;

        return courseMapper.toDtoPreviewEnrolled(
                courses,
                language,
                enrollmentMap,
                progressMap,
                reviewService.getAverageRatingByCourse(courseIds),
                courseVersionService.getActiveByCourseIds(courseIds),
                tagService.getTagsByCourseIdsAndLanguage(courseIds, language)
        );
    }


    @Transactional(readOnly = true)
    @Override
    public List<CourseDtoPreview> getTopRatedCourses(Language language) {
        List<Course> courses = courseRepository.findCoursesOrderedByRating();

        int limit = Math.min(5, courses.size());
        courses = courses.subList(0, limit);

        List<Long> courseIds = courses.stream()
                .map(Course::getId)
                .toList();

        return courseMapper.toDtoPreview(
                courses,
                language,
                reviewService.getAverageRatingByCourse(courseIds),
                courseVersionService.getActiveByCourseIds(courseIds),
                tagService.getTagsByCourseIdsAndLanguage(courseIds, language)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseDtoPreview> getMostPopularCourses(Language language) {
        List<Course> courses = courseRepository.findCoursesOrderedByPopularity();

        int limit = Math.min(5, courses.size());
        courses = courses.subList(0, limit);

        List<Long> courseIds = courses.stream()
                .map(Course::getId)
                .toList();

        return courseMapper.toDtoPreview(
                courses,
                language,
                reviewService.getAverageRatingByCourse(courseIds),
                courseVersionService.getActiveByCourseIds(courseIds),
                tagService.getTagsByCourseIdsAndLanguage(courseIds, language)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDtoDetail getCourseById(Long courseId, Language language) {
        validate.validateCourseExists(courseId);
        Course course = courseRepository.findById(courseId).orElseThrow();
        return courseMapper.toDtoDetail(course, language,
                reviewService.getAverageRatingByCourse(List.of(courseId)),
                courseVersionService.getActiveByCourseIds(List.of(courseId)),
                tagService.getTagsByCourseIdsAndLanguage(List.of(courseId), language));
    }

    @Transactional(readOnly = true)
    @Override
    public Course getCourseEntityById(Long courseId) {
        validate.validateCourseExists(courseId);
        return courseRepository.findById(courseId).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public Course getCourseEntityByLectureId(Long lectureId) {
        validate.validateLectureExists(lectureId);

        return courseRepository.findByLectureId(lectureId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllTopics(Language language) {
        List<TagTranslate> translations = courseRepository.getCourseTopics(language);
        return translations.stream()
                .map(t -> t.getTag().getId() + "_" + t.getValue())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllSkills(Language language) {
        List<TagTranslate> translations = courseRepository.getCourseSkills(language);
        return translations.stream()
                .map(t -> t.getTag().getId() + "_" + t.getValue())
                .toList();
    }

}
