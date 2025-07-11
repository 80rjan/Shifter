import React, {useEffect, useRef} from "react";
import CoursesFilters from "../components/CoursesFilters.tsx";
import CoursesGrid from "../components/CoursesGrid.tsx";
import type {FilterParams} from "../types/FilterParams.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import {fetchCoursesApi} from "../api/courseApi.ts";
import ShifterRocket from "../../public/Rocket-Blue-Fire.png"
import {useCourseStorage} from "../context/CourseStorage.ts";
import type {CoursePreview} from "../types/CoursePreview.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";

function getInitialFiltersFromSearch(): FilterParams {
    const searchParams = new URLSearchParams(location.search);
    const initialParams: FilterParams = {};

    const search = searchParams.get("search");
    if (search) initialParams.search = search;

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
    const navigate = useNavigate();
    const location = useLocation();
    const { accessToken } = useGlobalContext();
    const {allCourses, topics, skills} = useCourseStorage();

    const [showOnlyFavorites, setShowOnlyFavorites] = React.useState<boolean>(false);
    const [filteredCourses, setFilteredCourses] = React.useState<CoursePreview[] | null>(null);

    const [filters, setFilters] = React.useState<FilterParams>(getInitialFiltersFromSearch());
    const [loading, setLoading] = React.useState<boolean>(false);

    const abortControllerRef = useRef<AbortController | null>(null);
    const debounceTimeoutRef = useRef<number | null>(null);

    // Effect to fetch filtered courses on filters change
    useEffect(() => {
        if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);

        const noFiltersApplied = Object.keys(filters).length === 0;

        if (noFiltersApplied) {

            if (abortControllerRef.current) {
                abortControllerRef.current.abort();
                abortControllerRef.current = null;
            }

            setFilteredCourses(null);

            // Clear URL query params
            navigate({pathname: location.pathname, search: ""}, {replace: true});
            setLoading(false);
            return;
        }

        debounceTimeoutRef.current = window.setTimeout(() => {
            if (abortControllerRef.current) {
                abortControllerRef.current.abort();
            }

            abortControllerRef.current = new AbortController();

            setLoading(true);

            fetchCoursesApi(accessToken || "", filters, abortControllerRef.current?.signal)
                .then(data => {
                    setFilteredCourses(data);
                })
                .catch(error => {
                    if (error.name === "CanceledError" || error.message === "canceled") {
                        // request was aborted, do nothing
                        console.log("Previous request aborted");
                    } else {
                        console.error(error);
                    }
                })
                .finally(() => {
                    setLoading(false);
                    abortControllerRef.current = null;
                });

            // Update URL with filters
            const paramsToUrl = new URLSearchParams();
            Object.entries(filters).forEach(([key, value]) => {
                if (Array.isArray(value)) {
                    value.forEach(v => paramsToUrl.append(key, v));
                } else if (value) {
                    paramsToUrl.set(key, value.toString());
                }
            });
            navigate({pathname: location.pathname, search: paramsToUrl.toString()}, {replace: true});
        }, 500);

        return () => {
            if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);
            if (abortControllerRef.current) {
                abortControllerRef.current.abort();
                abortControllerRef.current = null;
            }
        };
    }, [filters]);

    return (
        <main className="font-montserrat bg-white">
            <section
                className="relative flex flex-col items-center justify-center py-vertical-lg pt-30 gap-4 px-horizontal-lg shadow-sm">
                <img
                    src={ShifterRocket}
                    alt="Shifter Rocket"
                    className="absolute top-30 left-20 rotate-45 w-16 h-auto"
                />
                <h1 className="text-6xl">
                    Ready to Take the
                    <br/>
                    <strong className="text-shifter">Next Step?</strong>
                </h1>
                <p>
                    Explore courses designed not just to teach — but to shift your mindset, sharpen your skills, and
                    accelerate your journey toward real impact. Whether you're scaling a business, leading a team, or
                    growing as a professional, this is where transformation begins.
                </p>
            </section>
            <section className="flex gap-0 w-full">
                <CoursesFilters
                    setFilters={setFilters}
                    filters={filters}
                    topics={topics}
                    skills={skills}
                    showOnlyFavoriteCourses={showOnlyFavorites}
                    setShowOnlyFavorites={setShowOnlyFavorites}/>
                <CoursesGrid
                    setFilters={setFilters}
                    filters={filters}
                    courses={filteredCourses ?? allCourses}
                    loading={loading}
                    showOnlyFavoriteCourses={showOnlyFavorites}
                />
            </section>
        </main>
    );
}

export default Courses;