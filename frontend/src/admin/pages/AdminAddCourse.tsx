import {useState} from "react";
import type {CourseEntity} from "../types/CourseEntity.tsx";
import CourseCard from "../../components/CourseCard.tsx";
import {createCourseApi, uploadCourseFilesApi} from "../api/addCourse.ts";
import {useAuthContext} from "../../context/AuthContext.tsx";
import type {ContentType} from "../../types/ContentType.tsx";
import AdminAddCourseInfo from "../components/AdminAddCourseInfo.tsx";
import AdminAddCourseContent from "../components/AdminAddCourseContent.tsx";
import {useNavigate} from "react-router-dom";

function AdminAddCourse() {
    const [course, setCourse] = useState<CourseEntity>({
        imageUrl: "",
        color: "#000000",
        titleShort: "",
        title: "",
        difficulty: "BEGINNER",
        durationMinutes: 0,
        price: 0,
        descriptionShort: "",
        description: "",
        descriptionLong: "",
        whatWillBeLearned: [],
        skillsGained: [],
        topicsCovered: [],
        courseContents: []
    })
    const [courseImage, setCourseImage] = useState<File | null>(null);
    const [courseLectureFiles, setCourseLectureFiles] = useState<{file: File, type: ContentType, contentPosition: number, lecturePosition: number}[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const {accessToken} = useAuthContext();
    const navigate = useNavigate();

    const courseCard = (
        <CourseCard card={{
            id: 0, // temporary since new course has no ID yet
            imageUrl: courseImage ? URL.createObjectURL(courseImage) : "",
            color: course.color,
            titleShort: course.titleShort,
            title: course.title,
            difficulty: course.difficulty,
            durationMinutes: course.durationMinutes,
            price: course.price,
            rating: 0,
            ratingCount: 0,
            skillsGained: course.skillsGained,
            topicsCovered: course.topicsCovered,
            courseContentCount: course.courseContents.length
        }}/>
    )

    const handleAddCourse = async () => {
        if (
            !courseImage ||
            !course.titleShort || !course.title || !course.difficulty || !course.durationMinutes ||
            !course.descriptionShort || !course.description || !course.descriptionLong ||
            course.whatWillBeLearned.length === 0 || course.skillsGained.length === 0 || course.topicsCovered.length === 0 ||
            course.courseContents.length === 0
        ) {
            setError("Please fill in all fields");
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

        console.log(JSON.stringify(course).length);
        setLoading(true);
        setError(null);

        try {
            const courseId = await createCourseApi(JSON.stringify(course), accessToken || "");
            if (!courseId) {
                throw new Error("Failed to create course.");
            }

            try {
                await uploadCourseFilesApi(courseId, formData, accessToken || "");
                navigate('/')
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

    return (
        <main className="flex flex-col gap-12 px-horizontal-md py-vertical-md">
            <AdminAddCourseInfo
                course={course}
                setCourse={setCourse}
                courseCard={courseCard}
                setCourseImage={setCourseImage}
            />
            <AdminAddCourseContent
                course={course}
                setCourse={setCourse}
                courseLectureFiles={courseLectureFiles}
                setCourseLectureFiles={setCourseLectureFiles}
            />
            {
                error &&
                <p className="text-lg text-red-500 text-center">
                    {error}
                </p>
            }
            <button
                onClick={handleAddCourse}
                disabled={loading}
                className={`hover:shadow-lg transition-all duration-400 ease-in-out
                w-full py-2 border-2 border-white/40 bg-shifter rounded-sm text-white font-medium text-2xl shadow-md shadow-shifter/40 
                disabled:opacity-60 disabled:cursor-not-allowed cursor-pointer`}
            >
                {loading ? "Creating..." : "Create Course"}
            </button>
        </main>
    )
}

export default AdminAddCourse;