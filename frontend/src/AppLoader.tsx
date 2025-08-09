import {useAuthContext} from "./context/AuthContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";
import {fetchUserEnrollmentsApi} from "./api/enrollmentApi.ts";
import {getFromSessionStorage, saveToSessionStorage} from "./utils/useSessionStorage.ts";

function AppLoader() {
    const {accessToken, authChecked} = useAuthContext();
    const {
        recommendedCourses,
        setRecommendedCourses,
        enrollments,
        setEnrollments
    } = useCourseStorage();

    useEffect(() => {
        if (!authChecked) return;

        const load = async () => {
            // Recommended Courses
            try {
                const recommendedStored = getFromSessionStorage<typeof recommendedCourses>("recommendedCourses");
                if (recommendedStored) {
                    setRecommendedCourses(recommendedStored);
                } else {
                    const recommended = await fetchRecommendedCoursesApi(accessToken || "");
                    setRecommendedCourses(recommended);
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
                console.error("Failed to fetch recommended courses:", err);
            }
        };


        load();
    }, [authChecked, accessToken]);

    useEffect(() => {
        saveToSessionStorage("recommendedCourses", recommendedCourses);
    }, [recommendedCourses]);

    useEffect(() => {
        saveToSessionStorage("enrollments", enrollments);
    }, [enrollments]);


    return null
}

export default AppLoader;