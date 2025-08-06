import React, {useEffect, useState} from "react";
import {fetchCoursesApi, fetchEnrolledCoursesApi} from "../api/courseApi.ts";
import type {CoursePreview} from "../types/CoursePreview.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useCourseStorage} from "../context/CourseStorage.ts";
import StarFilled from "../assets/icons/StarFilled.tsx";

function DashboardCourses() {
    const {allCourses: allCoursesStorage, setAllCourses: setAllCoursesStorage} = useCourseStorage();
    const {user, accessToken} = useAuthContext();

    const [selectedTab, setSelectedTab] = useState("all");
    const [enrolledCourses, setEnrolledCourses] = useState<CoursePreview[]>([]);
    const [allCourses, setAllCourses] = useState<CoursePreview[]>(allCoursesStorage || []);

    useEffect(() => {
        // Enrolled courses
        fetchEnrolledCoursesApi(accessToken || "")
            .then(data => {
                setEnrolledCourses(data);
            })
            .catch(error => {
                console.error("Failed to fetch enrolled courses:", error);
            })

        if (allCoursesStorage && allCoursesStorage.length > 0) {
            return;
        }
        const storedCourses = sessionStorage.getItem("allCourses");
        if (storedCourses) {
            setAllCoursesStorage(JSON.parse(storedCourses));
            setAllCourses(JSON.parse(storedCourses));
            return;
        }
        fetchCoursesApi(accessToken || "")
            .then(courses => {
                setAllCoursesStorage(courses);
                setAllCourses(courses);
                sessionStorage.setItem("allCourses", JSON.stringify(courses));
            })
            .catch(err => {
                console.error("Failed to fetch courses:", err);
            })
    }, [accessToken]);


    function renderContent() {
        if (enrolledCourses.length === 0) {
            return (
                <h2 className="text-2xl font-semibold text-black/60">
                    No limits, just potential — enroll in your first course!
                </h2>
            );
        }

        switch (selectedTab) {
            case "all": return (
                    <>
                        {
                            enrolledCourses.map((course, index) => (
                                <CourseList course={course} key={index}/>
                            ))
                        }
                    </>
                );
            case "active": return (
                    <>
                        {
                            enrolledCourses.map((course, index) => (
                                <CourseList course={course} key={index}/>
                            ))
                        }
                    </>
                );
            case "completed": return (
                    <>
                        {
                            enrolledCourses.map((course, index) => (
                                <CourseList course={course} key={index}/>
                            ))
                        }
                    </>
                );
            case "favorites": return (
                    <>
                        {
                            allCourses.filter(course => user?.favoriteCourses.includes(course.id)).map((course, index) => (
                                <CourseList course={course} key={index}/>
                            ))
                        }
                    </>
                );
        }
    }

    return (
        <section className="flex flex-col gap-4 text-left pt-top-nav-sm">
            <h1 className="text-3xl font-semibold">My Courses</h1>
            <ul>
                <ListTab name={"All"} isSelected={selectedTab === "all"}
                         setSelectedTab={() => setSelectedTab("all")}/>
                <ListTab name={"Active"} isSelected={selectedTab === "active"}
                         setSelectedTab={() => setSelectedTab("active")}/>
                <ListTab name={"Completed"} isSelected={selectedTab === "completed"}
                         setSelectedTab={() => setSelectedTab("completed")}/>
                <ListTab name={"Favorites"} isSelected={selectedTab === "favorites"}
                         setSelectedTab={() => setSelectedTab("favorites")}/>
            </ul>
            <div className="flex flex-col gap-4">
                {renderContent()}
            </div>
        </section>
    )
}

function CourseList({course}: { course: CoursePreview }) {

    return (
        <aside
            style={{"--card-color": course.color} as React.CSSProperties}
            className="flex gap-8 items-center p-4 border-1 border-black/40 rounded-xl"
        >
            {/*IMAGE*/}
            <div className="overflow-clip rounded-lg w-1/3">
                <img src={course.imageUrl} alt={course.title}
                     className="aspect-video object-cover"/>
            </div>

            {/*INFO*/}
            <div className="flex flex-col gap-4 w-2/3">
                {/*TITLE AND TOPICS COVERED*/}
                <div className="flex flex-col gap-0">
                    <h3 className="text-xl font-bold">{course.titleShort}</h3>

                    <p className="text-black/60">{
                        course.topicsCovered.map(item =>
                            item
                                .toLowerCase()
                                .replace(/_/g, " ")
                                .replace(/\b\w/g, c => c.toUpperCase())
                        )
                            .join(" • ")
                    }</p>
                </div>

                {/*Info*/}
                <div className="flex flex-wrap gap-2 whitespace-nowrap">
                    <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                        <StarFilled className="w-4 h-4 text-gold"/> {course.rating / course.ratingCount}
                    </div>
                    <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                        {course.ratingCount} reviews
                    </div>
                    <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                        {(course.durationMinutes / 60).toFixed(1)} hours
                    </div>
                    <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                        {course.courseContentCount} modules
                    </div>
                    <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                        {course.difficulty.charAt(0) + course.difficulty.slice(1).toLowerCase()}
                    </div>
                </div>
            </div>
        </aside>
    )
}

function ListTab({name, isSelected, setSelectedTab}: {
    name: string;
    isSelected: boolean;
    setSelectedTab: () => void;
}) {
    return (
        <li className="inline-block text-lg font-medium ">
            <button
                className={`hover:text-black px-4 py-2 cursor-pointer ${isSelected ? "border-b-2 border-shifter" : "text-black/40"}`}
                onClick={setSelectedTab}
            >
                {name}
            </button>
        </li>
    )
}

export default DashboardCourses;