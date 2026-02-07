import {useAdminCourses} from "../hooks/useAdminCourses.tsx";
import CourseCardSkeleton from "../../components/skeletons/CourseCardSkeleton.tsx";
import AdminCourseCard from "../components/AdminCourseCard.tsx";

export default function AdminCourses() {
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
                    <AdminCourseCard key={i} card={course} />
                ))
            )}
        </main>

    )
}