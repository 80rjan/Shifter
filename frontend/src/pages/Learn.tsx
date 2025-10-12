import {useEnrolledCourses} from "../hooks/useEnrolledCourses.tsx";
import {useState} from "react";
import {useAuthContext} from "../context/AuthContext.tsx";
import CourseCardLearnDashboard from "../components/CourseCardLearnDashboard.tsx";
import CourseCardEnrolledSkeleton from "../components/skeletons/CourseCardEnrolledSkeleton.tsx";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import ShifterArrow from "../../public/Shifter-Arrow-White.png";

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
        const noCoursesMessage = {
            all: "You haven't enrolled in any courses yet!",
            active: "No active courses — time to start learning!",
            completed: "Nothing completed yet — keep going!",
            favorites: "You have no favorites — find courses you love!"
        };

        switch (selectedTab) {
            case "all":
                return (
                    <>
                        {
                            enrolledCourses.length > 0 ?
                                enrolledCourses.map((course, index) => (
                                    <CourseCardLearnDashboard
                                        course={course}
                                        key={index}
                                        markCourseAsRated={(rating) => setEnrolledCourses([
                                            ...enrolledCourses.filter(c => c.id !== course.id),
                                            {...course, rating: rating}
                                        ])}
                                    />
                                )) : (
                                    <h2 className="text-2xl font-semibold text-black/60 text-left whitespace-nowrap">{noCoursesMessage.all}</h2>
                                )
                        }
                    </>
                );
            case "active":
                return (
                    <>
                        {
                            enrolledCourses.length > 0 ?
                                enrolledCourses.map((course, index) => {
                                    if (course.isFinished) return null;
                                    return (
                                        <CourseCardLearnDashboard
                                            course={course}
                                            key={index}
                                            markCourseAsRated={(rating) => setEnrolledCourses([
                                                ...enrolledCourses.filter(c => c.id !== course.id),
                                                {...course, rating: rating}
                                            ])}
                                        />
                                    )
                                }) : (
                                    <h2 className="text-2xl font-semibold text-black/60 text-left whitespace-nowrap">{noCoursesMessage.active}</h2>
                                )
                        }
                    </>
                );
            case "completed":
                return (
                    <>
                        {
                            enrolledCourses.filter(course => course.isFinished).length > 0 ?
                                enrolledCourses.filter(course => course.isFinished).map((course, index) => (
                                        <CourseCardLearnDashboard
                                            course={course}
                                            key={index}
                                            markCourseAsRated={(rating) => setEnrolledCourses([
                                                ...enrolledCourses.filter(c => c.id !== course.id),
                                                {...course, rating: rating}
                                            ])}
                                        />
                                    )
                                ) : (
                                    <h2 className="text-2xl font-semibold text-black/60 text-left whitespace-nowrap">{noCoursesMessage.completed}</h2>
                                )
                        }
                    </>
                );
            case "favorites":
                return (
                    <>
                        {
                            allCourses.filter(course => user?.favoriteCourses.includes(course.id)).length > 0 ?
                                allCourses?.filter(course => user?.favoriteCourses.includes(course.id)).map((course, index) => (
                                    <CourseCardLearnDashboard course={course} key={index}
                                                              markCourseAsRated={(rating) => {
                                                                  console.log(rating)
                                                              }}/>
                                )) : (
                                    <h2 className="text-2xl font-semibold text-black/60 text-left whitespace-nowrap">{noCoursesMessage.favorites}</h2>
                                )
                        }
                    </>
                );
        }
    }


    return (
        <main className="flex flex-col flex-1 gap-0 ">
            <div className="bg-black">
                <section className="relative border-b-2 border-white/40 overflow-clip
            bg-shifter/90 shadow-md text-white px-horizontal-md w-full flex flex-col gap-between-sm text-left pt-top-nav-lg">

                    <img
                        src={ShifterArrow}
                        alt="Shifter Arrow"
                        className="absolute right-0 -bottom-24 rotate-45 opacity-20
                    pointer-events-none w-60 h-auto"
                    />

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
            </div>
            <div className="flex-1 bg-beige">
                <section className="grid grid-cols-4 gap-x-4 gap-y-8 px-horizontal-md py-vertical-md items-stretch ">
                    {
                        loading ?
                            new Array(8).fill(0).map((_, index) => (
                                <CourseCardEnrolledSkeleton key={index}/>
                            )) : renderContent()
                    }
                </section>
            </div>
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