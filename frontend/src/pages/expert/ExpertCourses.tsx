import {useAdminCourses} from "../../hooks/expert/useAdminCourses.tsx";
import CourseCardSkeleton from "../../components/skeletons/CourseCardSkeleton.tsx";
import ExpertCourseCard from "../../components/expert/ExpertCourseCard.tsx";

export default function ExpertCourses() {
    const {
        courses,
        loading
    } = useAdminCourses();

    return (
        <main className="grid grid-cols-3 gap-12 px-horizontal py-vertical">
            {loading ? (
                Array.from({ length: 20 }).map((_, i) => (
                    <CourseCardSkeleton key={i} />
                ))
            ) : (
                courses.map((course, i) => (
                    <ExpertCourseCard key={i} card={course} />
                ))
            )}
        </main>

    )
}