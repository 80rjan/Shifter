import {useAdminTranslateCourse} from "../hooks/useAdminTranslateCourse.tsx";
import AdminTranslateCourseInfo from "../components/translatecourse/AdminTranslateCourseInfo.tsx";
import AdminTranslateCourseContent from "../components/translatecourse/AdminTranslateCourseContent.tsx";
import AdminTranslateCourseSkeleton from "../components/skeletons/AdminTranslateCourseSkeleton.tsx";
import {useEffect} from "react";

function AdminTranslateCourse() {
    const {
        courseFull,
        courseTranslate,
        setCourseTranslate,
        courseCard,
        courseLectureFiles,
        setCourseLectureFiles,
        error,
        loading,
        isFetchingCourse,
        handleTranslateCourse
    } = useAdminTranslateCourse();

    if (!courseFull || !courseCard)
        return null;

    if (isFetchingCourse) {
        return (
            <AdminTranslateCourseSkeleton />
        )
    }

    return (
        <main className="flex flex-col gap-12 px-horizontal-md py-vertical-md">
            <AdminTranslateCourseInfo
                initCourse={courseFull}
                course={courseTranslate}
                setCourse={setCourseTranslate}
                courseCard={courseCard}
            />
            <AdminTranslateCourseContent
                initCourse={courseFull}
                course={courseTranslate}
                setCourse={setCourseTranslate}
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
                onClick={handleTranslateCourse}
                disabled={loading}
                className={`hover:shadow-lg transition-all duration-400 ease-in-out
                w-full py-2 border-2 border-white/40 bg-shifter rounded-sm text-white font-medium text-2xl shadow-md shadow-shifter/40 
                disabled:opacity-60 disabled:cursor-not-allowed cursor-pointer`}
            >
                {loading ? "Translating..." : "Translate Course"}
            </button>
        </main>
    )
}

export default AdminTranslateCourse;