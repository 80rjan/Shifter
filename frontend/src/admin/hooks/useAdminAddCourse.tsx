import {useState} from "react";
import type {CourseFull} from "../types/CourseFull.tsx";
import type {ContentType} from "../../models/types/ContentType.tsx";
import {useAuthContext} from "../../context/AuthContext.tsx";
import {useNavigate} from "react-router-dom";
import CourseCard from "../../components/CourseCard.tsx";
import {createCourseApi, uploadCourseFilesApi} from "../api/addCourse.ts";

export function useAdminAddCourse() {
    const [course, setCourse] = useState<CourseFull>({
        color: "#000000",
        courseContents: [],
        description: "",
        descriptionLong: "",
        descriptionShort: "",
        difficulty: "BEGINNER",
        durationMinutes: 0,
        id: 0,
        imageUrl: "",
        language: "EN",
        price: 0,
        skillsGained: [],
        title: "",
        titleShort: "",
        topicsCovered: [],
        whatWillBeLearned: []
    })
    const [courseImage, setCourseImage] = useState<File | null>(null);
    const [courseLectureFiles, setCourseLectureFiles] = useState<{file: File, type: ContentType, contentPosition: number, lecturePosition: number}[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const {accessToken} = useAuthContext();
    const navigate = useNavigate();

    const courseCard = (
        <CourseCard card={{
            id: 0, // temporary since new javaObjects has no ID yet
            imageUrl: courseImage ? URL.createObjectURL(courseImage) : "",
            color: course.color,
            titleShort: course.titleShort,
            title: course.title,
            difficulty: course.difficulty,
            durationMinutes: course.durationMinutes,
            price: course.price,
            averageRating: 0,
            skillsGained: [],
            topicsCovered: [],
            courseContentCount: course.courseContents.length,
            courseLectureCount: course.courseContents.flatMap(content => content.courseLectures).length,
            urlSlug: "",
            translatedLanguages: []
        }}/>
    )

    const handleAddCourse = async () => {
        const countFileOrVideoLectures = course.courseContents
            .flatMap(content => content.courseLectures)
            .filter(lecture => lecture.contentType === "FILE" || lecture.contentType === "VIDEO" || lecture.contentType === "QUIZ" || lecture.contentType === "TOOL")
            .length;

        if (!courseImage) {
            setError("Please upload a course image");
            return;
        }
        if (!course.titleShort || !course.title || !course.difficulty ||
            !course.descriptionShort || !course.description || !course.descriptionLong ||
            course.whatWillBeLearned.length === 0 || course.skillsGained.length === 0 || course.topicsCovered.length === 0) {
            setError("Please fill in all course details");
            return;
        }
        if (
            course.courseContents.length === 0 || course.courseContents.some(content => content.title.length === 0) ||
            course.courseContents.some(content => content.courseLectures.some(lecture => !lecture.title || !lecture.description || !lecture.contentText)) ||
            course.courseContents.some(content => content.courseLectures.length === 0) || courseLectureFiles.length < countFileOrVideoLectures
        ) {
            setError("Please add at least one content with lectures, fill in all fields and upload files for all file/video/quiz/tool lectures");
            return;
        }

        const formData = new FormData();
        formData.append("courseImage", courseImage);
        courseLectureFiles.forEach(courseLectureFile => {
            formData.append("files", courseLectureFile.file);
            formData.append("types", courseLectureFile.type);
            formData.append("meta", JSON.stringify({
                contentPosition: courseLectureFile.contentPosition,
                lecturePosition: courseLectureFile.lecturePosition
            }));
        });

        setLoading(true);
        setError(null);

        try {
            const courseId = await createCourseApi(course, accessToken || "");
            if (!courseId) {
                throw new Error("Failed to create course.");
            }

            try {
                await uploadCourseFilesApi(courseId, formData, accessToken || "");
                navigate('/admin')
            } catch (err) {
                console.error("Error uploading course image and lecture files:", err);
                setError("An error occurred while uploading course image and lecture files. Please try again or contact support.");
            }
        } catch (err) {
            console.error("Error creating course:", err);
            setError("An error occurred while creating the course. Please try again or contact support.");
        } finally {
            setLoading(false);
        }
    };

    return {
        course,
        setCourse,
        courseCard,
        setCourseImage,
        courseLectureFiles,
        setCourseLectureFiles,
        error,
        loading,
        handleAddCourse
    }
}