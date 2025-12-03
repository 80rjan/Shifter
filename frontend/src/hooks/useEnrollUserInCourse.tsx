import { useNavigate } from "react-router-dom";
import { enrollUserApi } from "../api/enrollmentApi.ts";
import { useAuthContext } from "../context/AuthContext.tsx";
import { useCourseStorage } from "../context/CourseStorage.ts";
import { showInfoToast } from "../utils/showInfoToast.ts";
import type { Enrollment } from "../models/javaObjects/Enrollment.tsx";
import {fetchRecommendedCoursesApi} from "../api/courseApi.ts";
import {useUserContext} from "../context/UserContext.tsx";
import {useTranslation} from "react-i18next";
import type {Language} from "../models/types/Language.tsx";

export function useEnrollUserInCourse(courseId: number, urlSlug: string) {
    const { accessToken } = useAuthContext();
    const { user } = useUserContext();
    const navigate = useNavigate();
    const { enrollments, setEnrollments, allCourses, setAllCourses, setRecommendedCourses } = useCourseStorage();
    const { i18n } = useTranslation();

    async function enroll() {
        if (!user) {
            showInfoToast("Please log in to enroll in the course.");
        }

        try {
            const enrollment: Enrollment = await enrollUserApi(courseId, accessToken || "");

            setAllCourses(allCourses?.filter(course => course.id !== courseId) || [])
            setEnrollments([...(enrollments ?? []), enrollment]);

            const recommendedCourses = await fetchRecommendedCoursesApi(accessToken || "", i18n.language as Language);
            setRecommendedCourses(recommendedCourses);

            navigate(`/learn/${courseId}/${urlSlug}`);
            console.log("User enrolled in javaObjects successfully");
        } catch (error) {
            console.error("Error enrolling user in javaObjects:", error);
            throw error;
        }
    }

    return { enroll };
}
