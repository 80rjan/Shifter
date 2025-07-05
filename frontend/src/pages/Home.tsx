import Hero from "../components/Hero.tsx";
import CollaborationSteps from "../components/CollaborationSteps.tsx";
import RoadmapAI from "../components/RoadmapAI.tsx";
import ShifterValues from "../components/ShifterValues.tsx";
import CoursesCarousel from "../components/CoursesCarousel.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";
import {useEffect} from "react";
import {
    fetchAllCoursesApi,
    fetchCoursesSkillsApi,
    fetchCoursesTopicsApi,
    fetchRecommendedCoursesApi
} from "../api/courses.ts";
import {useGlobalContext} from "../context/GlobalContext.tsx";

function Home() {
    const {accessToken} = useGlobalContext();
    const {
        recommendedCourses,
        setRecommendedCourses,
        allCourses,
        setAllCourses,
        topics,
        setTopics,
        skills,
        setSkills
    } = useCourseStorage();

    const fetchRecommendedCourses = async () => {
        const stored = sessionStorage.getItem("recommendedCourses");
        if (stored) {
            setRecommendedCourses(JSON.parse(stored));
            return;
        }

        fetchRecommendedCoursesApi(accessToken || "")
            .then(data => {
                setRecommendedCourses(data);
                sessionStorage.setItem("recommendedCourses", JSON.stringify(data));
            })
            .catch(err => {
                console.error("Error fetching recommended courses:", err);
                throw err;
            });
    }

    const fetchAllCourses = async () => {
        const stored = sessionStorage.getItem("allCourses");
        if (stored) {
            setAllCourses(JSON.parse(stored));
            return;
        }

        fetchAllCoursesApi()
            .then(data => {
                setAllCourses(data);
                sessionStorage.setItem("allCourses", JSON.stringify(data));
            })
            .catch(err => {
                console.error("Error fetching all courses:", err);
                throw err;
            });
    }

    const fetchCoursesTopics = async () => {
        const stored = sessionStorage.getItem("courseTopics");
        if (stored) {
            setTopics(JSON.parse(stored));
            return;
        }

        fetchCoursesTopicsApi()
            .then(data => {
                setTopics(data);
                sessionStorage.setItem("courseTopics", JSON.stringify(data));
            })
            .catch(err => {
                console.error("Error fetching course topics:", err);
                throw err;
            });
    }

    const fetchCoursesSkills = async () => {
        const stored = sessionStorage.getItem("courseSkills");
        if (stored) {
            setSkills(JSON.parse(stored));
            return;
        }

        fetchCoursesSkillsApi()
            .then(data => {
                setSkills(data);
                sessionStorage.setItem("courseSkills", JSON.stringify(data));
            })
            .catch(err => {
                console.error("Error fetching course skills:", err);
                throw err;
            });
    }

    useEffect(() => {
        if (!recommendedCourses)
            fetchRecommendedCourses()

        if (!allCourses)
            fetchAllCourses()

        if (!topics)
            fetchCoursesTopics()

        if (!skills)
            fetchCoursesSkills()
    }, []);

    return (
        <main className="bg-white">
            <Hero/>
            <CollaborationSteps/>
            <RoadmapAI/>
            <CoursesCarousel/>
            <ShifterValues/>
        </main>
    )
}

export default Home