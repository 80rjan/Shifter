import AdminAddCourseInfo from "../components/addcourse/AdminAddCourseInfo.tsx";
import AdminAddCourseContent from "../components/addcourse/AdminAddCourseContent.tsx";
import {useAdminAddCourse} from "../hooks/useAdminAddCourse.tsx";

function AdminAddCourse() {
    const {
        course,
        setCourse,
        courseCard,
        setCourseImage,
        courseLectureFiles,
        setCourseLectureFiles,
        error,
        loading,
        handleAddCourse
    } = useAdminAddCourse();

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