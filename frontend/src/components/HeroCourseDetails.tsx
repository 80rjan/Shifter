import type {CourseDetail} from "../types/CourseDetail.tsx";
import React from "react";
import {useCourseStorage} from "../context/CourseStorage.ts";

function HeroCourseDetails({course, enrollUser}: { course: CourseDetail | null, enrollUser: () => Promise<void> }) {
    const [isLoading, setIsLoading] = React.useState<boolean>(false);
    const {enrollments} = useCourseStorage();
    const tripleInfo = [
        {
            header: `${course?.courseContents && course.courseContents.length} Modules Total`,
            description: course?.descriptionShort
        },
        {
            header: `${course?.durationMinutes && (course.durationMinutes / 60).toFixed(1)} Hours Duration`,
            description: 'Learn at your own pace with flexible timing, plus optional exercises and helpful templates.'
        },
        {
            header: course?.ratingCount && course.ratingCount > 10
                ? `${(course.rating || 0) / course.ratingCount} Rating`
                : `New & Trending`,
            description: course?.ratingCount && course.ratingCount > 10
                ? 'Rated highly by learners for its practical insights and actionable strategies.'
                : 'New to the platform, this course is growing quickly. Be among the first learners to benefit from its insights.'
        }
        ,
    ]

    const bgColor = "bg-[var(--card-color)]";


    return (
        <div
            style={{"--card-color": course?.color} as React.CSSProperties}
            className="bg-dark-blue py-4">
            {/*HEADER AND DESCRIPTION*/}
            <section
                className="flex flex-col items-center gap-8 bg-white mx-6 px-horizontal-lg pb-12 pt-40 rounded-xl shadow-lg shadow-black/20">
                <h1 className="text-5xl">{course?.title}</h1>
                <p>{course?.description}</p>
                {
                    enrollments && enrollments.some(enrollment => enrollment.courseId === course?.id) ?
                        <div
                            className="flex mt-12 gap-4 items-center bg-gray/60 backdrop-blur-lg border-3 border-black/5 px-2 py-1 w-fit rounded-full">

                            {
                                isLoading ? (
                                    <div className="w-8 loader"></div>
                                ) : (
                                    <button className={`
                                ${bgColor}
                                hover:shadow-lg hover:shadow-deep-green/50 transition-all duration-300 ease-in-out cursor-pointer
                                shadow-md shadow-deep-green/30 text-white font-medium text-xl border-3 border-white/50 rounded-full px-14 py-2
                            `}
                                    >Start Learning Now</button>
                                )
                            }
                        </div>
                        :
                        <div className="flex mt-12 gap-4 items-center bg-gray/60 backdrop-blur-lg border-3 border-black/5 px-2 py-1 w-fit rounded-full">
                    <span className="font-semibold text-xl px-8">{
                        course?.price && course.price > 0 ? `$${course?.price}` : 'Free'
                    }</span>
                            {
                                isLoading ? (
                                    <div className="w-8 loader"></div>
                                ) : (
                                    <button className={`
                                ${bgColor}
                                hover:shadow-lg hover:shadow-deep-green/50 transition-all duration-300 ease-in-out cursor-pointer
                                shadow-md shadow-deep-green/30 text-white font-medium text-xl border-3 border-white/50 rounded-full px-14 py-2
                            `}
                                            onClick={() => {
                                                setIsLoading(true)
                                                enrollUser()
                                                    .catch((error) => {
                                                        console.error("Error enrolling user in course:", error);
                                                    })
                                                    .finally(() => {
                                                        setIsLoading(false)
                                                    })
                                            }}
                                    >Enroll Now</button>
                                )
                            }
                        </div>
                }
            </section>

            {/*TRIPLE INFO*/}
            <section className="flex text-white px-12 py-4">
                {
                    tripleInfo.map((info, index) => (
                        <div
                            key={index}
                            className="w-1/3 flex flex-col gap-4 text-left px-20 py-8 border-r-2 border-white/40 last:border-r-0"
                        >
                            <h2 className="text-3xl font-bold">{info.header}</h2>
                            <p className="text-md font-light opacity-90">{info.description}</p>
                        </div>
                    ))
                }
            </section>
        </div>
    )
}

export default HeroCourseDetails;