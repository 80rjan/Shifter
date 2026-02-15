import {useAuthContext} from "./context/AuthContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";
import {fetchUserEnrollmentsApi} from "./api/enrollmentApi.ts";
import {getFromSessionStorage, saveToSessionStorage} from "./utils/useSessionStorage.ts";
import {useTranslation} from "react-i18next";
import type {Language} from "./models/types/Language.tsx";

function AppLoader() {
    const {accessToken, authChecked} = useAuthContext();
    const {
        recommendedCourses,
        setRecommendedCourses,
        enrollments,
        setEnrollments
    } = useCourseStorage();
    const { i18n } = useTranslation();

    const fetchRecommendedCourses = async () => {
        try {
            // 💡 Call API with the current language
            const recommended = await fetchRecommendedCoursesApi(accessToken || "", i18n.language as Language);
            setRecommendedCourses(recommended);
        } catch (err) {
            console.error("Failed to fetch recommended courses on language change:", err);
        }
    }

    useEffect(() => {
        if (!authChecked) return;

        const load = async () => {
            // Recommended Courses
            try {
                const recommendedStored = getFromSessionStorage<typeof recommendedCourses>("recommendedCourses");
                if (recommendedStored) {
                    setRecommendedCourses(recommendedStored);
                } else {
                    await fetchRecommendedCourses()
                }
            } catch (err) {
                console.error("Failed to fetch recommended courses:", err);
            }

            // Enrollments
            try {
                const userEnrollmentsStored = getFromSessionStorage<typeof enrollments>("enrollments");
                if (userEnrollmentsStored) {
                    setEnrollments(userEnrollmentsStored);
                } else {
                    const userEnrollments = await fetchUserEnrollmentsApi(accessToken || "");
                    setEnrollments(userEnrollments);
                }
            } catch (err) {
                console.error("Failed to fetch user enrollments:", err);
            }
        };


        load();
    }, [authChecked, accessToken]);

    useEffect(() => {
        setRecommendedCourses([])
        fetchRecommendedCourses()
    }, [i18n.language])

    useEffect(() => {
        saveToSessionStorage("recommendedCourses", recommendedCourses);
    }, [recommendedCourses]);

    useEffect(() => {
        saveToSessionStorage("enrollments", enrollments);
    }, [enrollments]);


    return null
}

export default AppLoader;