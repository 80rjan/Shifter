import React, {useEffect, useRef} from "react";
import CoursesFilters from "../components/CoursesFilters.tsx";
import CoursesGrid from "../components/CoursesGrid.tsx";
import { type Course} from "../types/Course.tsx";
import type {FilterParams} from "../types/FilterParams.tsx";
import { useNavigate, useLocation } from "react-router-dom";
import {fetchCoursesApi, fetchCoursesSkillsApi, fetchCoursesTopicsApi} from "../api/courses.ts";
import axios from "axios";

function getInitialParamsFromSearch(): FilterParams {
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

    const [loading, setLoading] = React.useState<boolean>(false);
    const [courses, setCourses] = React.useState<Course[]>([]);
    const [params, setParams] = React.useState<FilterParams>(getInitialParamsFromSearch());
    const [topics, setTopics] = React.useState<string[]>([]);
    const [skills, setSkills] = React.useState<string[]>([]);

    const abortControllerRef = useRef<AbortController | null>(null);
    const debounceTimeoutRef = useRef<number | null>(null);

    const fetchFilterValues = () => {
        fetchCoursesTopicsApi()
            .then(data => {
                setTopics([...new Set(data as string[])]);
            })
            .catch(err => {
                console.error("Error fetching topics:", err);
            })

        fetchCoursesSkillsApi()
            .then(data => {
                setSkills([...new Set(data as string[])]);
            })
            .catch(err => {
                console.error("Error fetching skills:", err);
            })
    }

    const fetchCourses = () => {

        if (abortControllerRef.current) {
            abortControllerRef.current.abort();
        }

        const controller = new AbortController();
        abortControllerRef.current = controller;

        setLoading(true);

        fetchCoursesApi(params, controller.signal)
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

    // FETCH FILTER TOPICS AND SKILLS. GET THE PARAMS FROM THE QUERY - ONLY ON MOUNT
    useEffect(() => {
        fetchFilterValues();
    }, []);

    // UPDATE URL WHEN PARAMS CHANGE
    useEffect(() => {
        if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);

        debounceTimeoutRef.current = setTimeout(() => {
            // Update URL params
            const paramsToUrl = new URLSearchParams();

            Object.entries(params).forEach(([key, value]) => {
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

            fetchCourses();
        }, 500);

        return () => {
            if (debounceTimeoutRef.current) clearTimeout(debounceTimeoutRef.current);
            if (abortControllerRef.current) abortControllerRef.current.abort();
        };
    }, [params]);


    return (
        <main className="font-montserrat bg-white">
            <section className="bg-deep-green/10 flex flex-col items-center justify-center py-vertical-lg pt-40 gap-4 px-40">
                <h1 className="text-6xl">
                    Ready to Take the
                    <br />
                    <strong className="text-shifter font-semibold">Next Step?</strong>
                </h1>
                <p>
                    Explore courses designed not just to teach — but to shift your mindset, sharpen your skills, and
                    accelerate your journey toward real impact. Whether you're scaling a business, leading a team, or
                    growing as a professional, this is where transformation begins.
                </p>
            </section>
            <div className="flex gap-0 w-full">
                <CoursesFilters setParams={setParams} params={params} topics={topics} skills={skills}/>
                <CoursesGrid setParams={setParams} params={params} courses={courses} loading={loading}/>
            </div>
        </main>
    )
}

export default Courses;