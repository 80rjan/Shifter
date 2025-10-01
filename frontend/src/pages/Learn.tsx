import {useEnrolledCourses} from "../hooks/useEnrolledCourses.tsx";
import { useState} from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
import CourseCardEnrolled from "../components/CourseCardEnrolled.tsx";
import CourseCardEnrolledSkeleton from "../components/skeletons/CourseCardEnrolledSkeleton.tsx";

function Learn() {
    const [selectedTab, setSelectedTab] = useState("all");
    const {user} = useAuthContext();
    const {
        allCourses,
        enrolledCourses,
        setEnrolledCourses,
        loading
    } = useEnrolledCourses();

    function renderContent() {
        if (enrolledCourses.length === 0) {
            return (
                <h2 className="text-2xl font-semibold text-black/60">
                    No limits, just potential — enroll in your first course!
                </h2>
            );
        }

        switch (selectedTab) {
            case "all":
                return (
                    <>
                        {
                            enrolledCourses.map((course, index) => (
                                <CourseCardEnrolled
                                    course={course}
                                    key={index}
                                    markCourseAsRated={(rating) => setEnrolledCourses([
                                        ...enrolledCourses.filter(c => c.id !== course.id),
                                        {...course, rating: rating}
                                    ])}
                                />
                            ))
                        }
                    </>
                );
            case "active":
                return (
                    <>
                        {
                            enrolledCourses.map((course, index) => {
                                if (course.isFinished) return null;
                                return (
                                    <CourseCardEnrolled
                                        course={course}
                                        key={index}
                                        markCourseAsRated={(rating) => setEnrolledCourses([
                                            ...enrolledCourses.filter(c => c.id !== course.id),
                                            {...course, rating: rating}
                                        ])}
                                    />
                                )
                            })
                        }
                    </>
                );
            case "completed":
                return (
                    <>
                        {
                            enrolledCourses.map((course, index) => {
                                if (!course.isFinished) return null;
                                return (
                                    <CourseCardEnrolled
                                        course={course}
                                        key={index}
                                        markCourseAsRated={(rating) => setEnrolledCourses([
                                            ...enrolledCourses.filter(c => c.id !== course.id),
                                            {...course, rating: rating}
                                        ])}
                                    />
                                )
                            })
                        }
                    </>
                );
            case "favorites":
                return (
                    <>
                        {
                            allCourses.filter(course => user?.favoriteCourses.includes(course.id)).map((course, index) => (
                                <CourseCardEnrolled course={course} key={index}/>
                            ))
                        }
                    </>
                );
        }
    }


    return (
        <main className="flex flex-col gap-0">
            <section className="border-b-2 border-white/40
            bg-dark-blue text-white px-horizontal-md w-full flex flex-col gap-4 text-left pt-top-nav-lg">
                <h1 className="text-4xl font-semibold">My Learning</h1>
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
            </section>
            <section className="grid grid-cols-4 gap-x-4 gap-y-8 px-horizontal-md py-vertical-md items-stretch">
                {
                    loading ?
                        new Array(8).fill(0).map((_, index) => (
                            <CourseCardEnrolledSkeleton key={index} />
                        )) : renderContent()
                }
            </section>
        </main>
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
                className={`hover:text-white px-4 py-2 cursor-pointer ${isSelected ? "border-b-2 border-white" : "text-white/40"}`}
                onClick={setSelectedTab}
            >
                {name}
            </button>
        </li>
    )
}

export default Learn;