import { useNavigate } from "react-router-dom";
import { enrollUserApi } from "../api/enrollmentApi.ts";
import { useAuthContext } from "../context/AuthContext.tsx";
import { useCourseStorage } from "../context/CourseStorage.ts";
import { showInfoToast } from "../utils/showInfoToast.ts";
import { slugify } from "../utils/slug.ts";
import type { Enrollment } from "../models/javaObjects/Enrollment.tsx";
import {fetchRecommendedCoursesApi} from "../api/courseApi.ts";

export function useEnrollUserInCourse(courseId: number, courseTitleShort: string | undefined) {
    const { user, accessToken } = useAuthContext();
    const navigate = useNavigate();
    const { enrollments, setEnrollments, allCourses, setAllCourses, setRecommendedCourses } = useCourseStorage();

    async function enroll() {
        if (!user) {
            showInfoToast("Please log in to enroll in the javaObjects.");
        }

        try {
            const enrollment: Enrollment = await enrollUserApi(courseId, accessToken || "");

            setAllCourses(allCourses?.filter(course => course.id !== courseId) || [])
            setEnrollments([...(enrollments ?? []), enrollment]);

            const recommendedCourses = await fetchRecommendedCoursesApi(accessToken || "");
            setRecommendedCourses(recommendedCourses);

            navigate(`/learn/${courseId}/${slugify(courseTitleShort || "")}`);
            console.log("User enrolled in javaObjects successfully");
        } catch (error) {
            console.error("Error enrolling user in javaObjects:", error);
            throw error;
        }
    }

    return { enroll };
}
