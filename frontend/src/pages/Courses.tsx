import {useEffect, useMemo, useState} from "react";
import { useTranslation } from "react-i18next";
import CoursesFilters from "../components/CoursesFilters.tsx";
import CoursesGrid from "../components/CoursesGrid.tsx";
import type {FilterParams} from "../models/FilterParams.tsx";
import {fetchCoursesApi} from "../api/courseApi.ts";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterRocket from "../../public/Rocket-Blue-Fire.png";
import {useCourseStorage} from "../context/CourseStorage.ts";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import CoursesFiltersSkeleton from "../components/skeletons/CoursesFiltersSkeleton.tsx";
import CoursesGridSkeleton from "../components/skeletons/CoursesGridSkeleton.tsx";
import {getFromSessionStorage, saveToSessionStorage} from "../utils/useSessionStorage.ts";

function getInitialFiltersFromSearch(locationSearch: string): FilterParams {
    const searchParams = new URLSearchParams(locationSearch);
    const initialParams: FilterParams = {};

    const search = searchParams.getAll("search");
    if (search.length > 0) initialParams.search = search;

    const skill = searchParams.getAll("skill");
    if (skill.length > 0) initialParams.skill = skill;

    const topic = searchParams.getAll("topic");
    if (topic.length > 0) initialParams.topic = topic;

    const difficulty = searchParams.getAll("difficulty");
    if (difficulty.length > 0) initialParams.difficulty = difficulty;

    const price = searchParams.getAll("price");
    if (price.length > 0) initialParams.price = price;

    const duration = searchParams.getAll("duration");
    if (duration.length > 0) initialParams.duration = duration;

    return initialParams;
}

function Courses() {
    const { t } = useTranslation("courses");
    const navigate = useNavigate();
    const location = useLocation();
    const {accessToken, user, loading: authLoading} = useAuthContext();
    const {allCourses, setAllCourses} = useCourseStorage();

    const [courses, setCourses] = useState<CoursePreview[] | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [filters, setFilters] = useState<FilterParams>(getInitialFiltersFromSearch(location.search));

    const topics = useMemo(() =>
            allCourses?.map(course => course.topicsCovered).flat() || [],
        [allCourses]
    );
    const skills = useMemo(() =>
            allCourses?.map(course => course.skillsGained).flat() || [],
        [allCourses]
    );

    useEffect(() => {
        const storedCourses = getFromSessionStorage<typeof allCourses>("allCourses");
        if (storedCourses) {
            setAllCourses(storedCourses);
            return;
        }

        setLoading(true);
        fetchCoursesApi(accessToken || "")
            .then(resCourses => setAllCourses(resCourses))
            .catch(err => console.error("Failed to fetch courses:", err))
            .finally(() => setLoading(false));
    }, [authLoading]);

    useEffect(() => {
        saveToSessionStorage("allCourses", allCourses);
    }, [allCourses]);

    useEffect(() => {
        if (!allCourses) return;

        let filteredCourses = [...allCourses];

        if (filters.search) {
            filteredCourses = filteredCourses.filter(course =>
                filters.search!.some(search =>
                    course.difficulty.toLowerCase().includes(search.toLowerCase()) ||
                    course.title.toLowerCase().includes(search.toLowerCase()) ||
                    course.topicsCovered.some(topic => topic.toLowerCase().includes(search.toLowerCase())) ||
                    course.skillsGained.some(skill => skill.toLowerCase().includes(search.toLowerCase()))
                )
            );
        }

        if (filters.skill?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.skill!.some(skill => course.skillsGained.includes(skill))
            );
        }

        if (filters.topic?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.topic!.some(topic => course.topicsCovered.includes(topic))
            );
        }

        if (filters.difficulty?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.difficulty!.includes(course.difficulty)
            );
        }

        if (filters.price?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.price!.some(priceRange => {
                    const price = course.price;
                    switch (priceRange) {
                        case "free": return price === 0;
                        case "low": return price > 0 && price < 20;
                        case "medium": return price >= 20 && price <= 50;
                        case "high": return price > 50;
                        default: return false;
                    }
                })
            );
        }

        if (filters.duration?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.duration!.some(durationRange => {
                    const minutes = course.durationMinutes;
                    switch (durationRange) {
                        case "extraShort": return minutes < 60 * 3;
                        case "short": return minutes >= 60 * 3 && minutes < 60 * 6;
                        case "medium": return minutes >= 60 * 6 && minutes < 60 * 10;
                        case "long": return minutes >= 60 * 10;
                        default: return false;
                    }
                })
            );
        }

        if (filters.showOnlyFavoriteCourses) {
            filteredCourses = filteredCourses.filter(course =>
                user?.favoriteCourses.includes(course.id)
            );
        }

        setCourses(filteredCourses);
    }, [allCourses, filters, user?.favoriteCourses]);

    useEffect(() => {
        const noFiltersApplied = Object.keys(filters).length === 0;
        if (noFiltersApplied) {
            navigate({pathname: location.pathname, search: ""}, {replace: true});
            return;
        }

        const paramsToUrl = new URLSearchParams();
        Object.entries(filters).forEach(([key, value]) => {
            if (Array.isArray(value)) {
                value.forEach(v => paramsToUrl.append(key, v));
            } else if (value) {
                paramsToUrl.set(key, value.toString());
            }
        });
        navigate({pathname: location.pathname, search: paramsToUrl.toString()}, {replace: true});
    }, [filters]);

    return (
        <main className="font-montserrat bg-white">
            <section className="relative flex flex-col items-center justify-center py-vertical-lg pt-top-nav-md gap-4 px-horizontal-lg shadow-sm">
                <img
                    src={ShifterRocket}
                    alt={t("rocketAlt")}
                    className="absolute top-top-nav-sm left-20 rotate-45 w-16 h-auto"
                />
                <h1 className="text-6xl">
                    {t("title")}
                    <br/>
                    <strong className="text-shifter">{t("highlight")}</strong>
                </h1>
                <p>{t("description")}</p>
            </section>

            {(authLoading || loading) ? (
                <section className="flex gap-0 w-full">
                    <CoursesFiltersSkeleton />
                    <CoursesGridSkeleton />
                </section>
            ) : (
                <section className="flex gap-0 w-full">
                    <CoursesFilters
                        filters={filters}
                        setFilters={setFilters}
                        topics={topics}
                        skills={skills}
                    />
                    <CoursesGrid
                        setFilters={setFilters}
                        filters={filters}
                        courses={courses}
                    />
                </section>
            )}
        </main>
    );
}

export default Courses;
