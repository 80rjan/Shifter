import {useAuthContext} from "./context/AuthContext.tsx";
import {useCourseStorage} from "./context/CourseStorage.ts";
import {
    fetchRecommendedCoursesApi
} from "./api/courseApi.ts";
import {useEffect} from "react";

function AppLoader() {
    const {accessToken, authChecked} = useAuthContext();
    const {
        recommendedCourses,
        setRecommendedCourses,
    } = useCourseStorage();

    useEffect(() => {
        if (!authChecked) return;

        const load = async () => {
            // Recommended Courses
            try {
                const recommendedStored = JSON.parse(sessionStorage.getItem("recommendedCourses") || "null");
                if (recommendedStored) {
                    setRecommendedCourses(JSON.parse(recommendedStored));
                } else {
                    const recommended = await fetchRecommendedCoursesApi(accessToken || "");
                    setRecommendedCourses(recommended);
                }
            } catch (err) {
                console.error("Failed to fetch recommended courses:", err);
            }
        };


        load();
    }, [authChecked, accessToken]);

    useEffect(() => {
        sessionStorage.setItem("recommendedCourses", JSON.stringify(recommendedCourses));
    }, [recommendedCourses]);


    return null
}

export default AppLoader;