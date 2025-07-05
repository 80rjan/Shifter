import React, {useEffect, useRef} from "react";
import CoursesFilters from "../components/CoursesFilters.tsx";
import CoursesGrid from "../components/CoursesGrid.tsx";
import type {FilterParams} from "../types/FilterParams.tsx";
import { useNavigate, useLocation } from "react-router-dom";
import {
    fetchAllCoursesApi,
    fetchCoursesSkillsApi,
    fetchCoursesTopicsApi,
    fetchFilteredCoursesApi,
} from "../api/courses.ts";
import axios from "axios";
import ShifterRocket from "../assets/shifterImg/Rocket-Blue-Fire.png"
import type {Course} from "../types/Course.tsx";

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

    const sessionCourses = sessionStorage.getItem("allCourses");
    const [courses, setCourses] = React.useState<Course[] | null>(sessionCourses ? JSON.parse(sessionCourses) : null);
    const sessionTopics = sessionStorage.getItem("courseTopics");
    const [topics, setTopics] = React.useState<string[] | null>(sessionTopics ? JSON.parse(sessionTopics) : null);
    const sessionsSkills = sessionStorage.getItem("courseSkills");
    const [skills, setSkills] = React.useState<string[] | null>(sessionsSkills ? JSON.parse(sessionsSkills) : null);

    const [filters, setFilters] = React.useState<FilterParams>(getInitialFiltersFromSearch());
    const [loading, setLoading] = React.useState<boolean>(false);

    const abortControllerRef = useRef<AbortController | null>(null);
    const debounceTimeoutRef = useRef<number | null>(null);

    // INITIAL FETCHES IF NOTHING IN SESSION STORAGE (e.g. user visits /courses directly instead of from Home)
    useEffect(() => {

        if (!topics)
            fetchCoursesTopicsApi()
                .then(data => {
                    setTopics(data);
                    sessionStorage.setItem("courseTopics", JSON.stringify(data));
                })
                .catch(err => {
                    console.error("Error fetching course topics: ", err);
                    throw err;
                })

        if (!skills)
            fetchCoursesSkillsApi()
                .then(data => {
                    setSkills(data);
                    sessionStorage.setItem("courseSkills", JSON.stringify(data));
                })
                .catch(err => {
                    console.error("Error fetching course skills: ", err);
                    throw err;
                })

        if (!courses)
            fetchAllCoursesApi()
                .then(data => {
                    setCourses(data);
                    sessionStorage.setItem("allCourses", JSON.stringify(data));
                })
                .catch(err => {
                    console.error("Error fetching all courses: ", err);
                    throw err;
                })
    }, [])

    const fetchFilteredCourses = () => {
        if (abortControllerRef.current) {
            abortControllerRef.current.abort();
        }

        const controller = new AbortController();
        abortControllerRef.current = controller;

        setLoading(true);

        fetchFilteredCoursesApi(filters, controller.signal)
            .then(data => {
                setCourses(data)
            })
            .catch(err => {
                if (axios.isCancel(err)) {
                    // request canceled, no need to do anything
                    console.log("Request canceled");
                } else if (err.name === "CanceledError") {
                    // axios 1.x abort signals throw CanceledError
                    console.log("Request aborted");
                } else {
                    console.error("Error fetching courses:", err);
                }
            })
            .finally(() => {
                setLoading(false);
            });
    }

    // UPDATE URL WHEN PARAMS CHANGE
    useEffect(() => {
        console.log(filters);

        if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);

        const noFiltersApplied = Object.keys(filters).length === 0;

        if (noFiltersApplied) {
            // No filters → immediately show all courses from sessionStorage (no debounce!)
            const sessionCourses = sessionStorage.getItem("allCourses");
            if (!sessionCourses) {
                setLoading(true)
                fetchAllCoursesApi()
                    .then(data => {
                        setCourses(data);
                        sessionStorage.setItem("allCourses", JSON.stringify(data));
                    })
                    .catch(err => {
                        console.error("Error fetching all courses: ", err);
                        throw err;
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            } else {
                setCourses(JSON.parse(sessionCourses) || []);
            }

            // Update URL without debounce (if needed)
            const paramsToUrl = new URLSearchParams();
            const newSearch = paramsToUrl.toString();
            if (newSearch !== location.search.replace(/^\?/, "")) {
                navigate({ pathname: location.pathname, search: newSearch }, { replace: true });
            }

            return () => {
                if (abortControllerRef.current) abortControllerRef.current.abort();
            };
        }

        // Filters are applied → debounce API call
        debounceTimeoutRef.current = setTimeout(() => {
            const paramsToUrl = new URLSearchParams();

            Object.entries(filters).forEach(([key, value]) => {
                if (
                    value !== undefined &&
                    value !== null &&
                    !(typeof value === "string" && value.trim() === "") &&
                    !(Array.isArray(value) && value.length === 0)
                ) {
                    if (Array.isArray(value) && value.length > 0) {
                        value.forEach(v => paramsToUrl.append(key, v));
                    } else {
                        paramsToUrl.set(key, value.toString());
                    }
                }
            });

            const newSearch = paramsToUrl.toString();
            if (newSearch !== location.search.replace(/^\?/, "")) {
                navigate({ pathname: location.pathname, search: newSearch }, { replace: true });
            }

            fetchFilteredCourses();
        }, 500);

        return () => {
            if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);
            if (abortControllerRef.current) abortControllerRef.current.abort();
        };
    }, [filters]);



    return (
        <main className="font-montserrat bg-white">
            <section className="relative flex flex-col items-center justify-center py-vertical-lg pt-30 gap-4 px-40 shadow-sm">
                <img
                    src={ShifterRocket}
                    alt="Shifter Rocket"
                    className="absolute top-30 left-20 rotate-45 w-16 h-auto"
                />

                <h1 className="text-6xl">
                    Ready to Take the
                    <br />
                    <strong className="text-shifter">Next Step?</strong>
                </h1>
                <p>
                    Explore courses designed not just to teach — but to shift your mindset, sharpen your skills, and
                    accelerate your journey toward real impact. Whether you're scaling a business, leading a team, or
                    growing as a professional, this is where transformation begins.
                </p>
            </section>
            <section className="flex gap-0 w-full">
                <CoursesFilters setFilters={setFilters} filters={filters} topics={topics} skills={skills}/>
                <CoursesGrid setFilters={setFilters} filters={filters} courses={courses} loading={loading}/>
            </section>
        </main>
    )
}

export default Courses;