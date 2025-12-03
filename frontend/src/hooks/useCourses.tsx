import type {Language} from "../models/types/Language.tsx";
import {useTranslation} from "react-i18next";
import {useLocation, useNavigate} from "react-router-dom";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useUserContext} from "../context/UserContext.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";
import {useEffect, useMemo, useState} from "react";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";
import type {FilterParams} from "../models/FilterParams.tsx";
import {fetchCoursesApi} from "../api/courseApi.ts";
import {getFromSessionStorage, saveToSessionStorage} from "../utils/useSessionStorage.ts";
import {indexToDifficultyMapper} from "../utils/mapper.ts";


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

export function useCourses() {
    const { i18n } = useTranslation();

    const navigate = useNavigate();
    const location = useLocation();
    const {accessToken, loading: authLoading} = useAuthContext();
    const { user } = useUserContext();
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

    const fetchCourses = async () => {
        setLoading(true);
        fetchCoursesApi(accessToken || "", i18n.language as Language)
            .then(resCourses => setAllCourses(resCourses))
            .catch(err => console.error("Failed to fetch courses:", err))
            .finally(() => setLoading(false));
    }
    useEffect(() => {
        const load = async() => {
            const storedCourses = getFromSessionStorage<typeof allCourses>("allCourses");
            if (storedCourses) {
                setAllCourses(storedCourses);
                return;
            }

            await fetchCourses()
        }

        load()
    }, [authLoading]);

    useEffect(() => {
        fetchCourses()
    }, [i18n.language, accessToken]);

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
            const courseDifficulties = [
                "BEGINNER",
                "INTERMEDIATE",
                "ADVANCED",
                "EXPERT"
            ]
            filteredCourses = filteredCourses.filter(course =>
                filters.difficulty!.map(indexStr => indexToDifficultyMapper(Number(indexStr), courseDifficulties)).includes(course.difficulty)
            );
        }

        if (filters.price?.length) {
            filteredCourses = filteredCourses.filter(course =>
                filters.price!.some(priceRange => {
                    const price = course.price;
                    switch (priceRange) {
                        case "free": return price === 0;
                        case "low": return price < 20;
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

    return {
        authLoading,
        loading,
        filters,
        setFilters,
        topics,
        skills,
        courses
    }
}