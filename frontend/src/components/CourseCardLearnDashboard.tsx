import type {CoursePreviewEnrolled} from "../models/javaObjects/course/CoursePreviewEnrolled.tsx";
import React from "react";
import StarFilled from "../assets/icons/StarFilled.tsx";
import StarOutline from "../assets/icons/StarOutline.tsx";
import ModalReviewCourse from "./ModalReviewCourse.tsx";
import type {CoursePreview} from "../models/javaObjects/course/CoursePreview.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";
import {fromEnumFormat} from "../utils/toEnumFormat.ts";

function CourseCardLearnDashboard({course, markCourseAsRated}: {
    course: CoursePreviewEnrolled | CoursePreview;
    markCourseAsRated: (rating: number) => void;
}) {
    const [showReviewModal, setShowReviewModal] = React.useState<boolean>(false);

    return (
        <aside className="h-full">
            <LocalizedLink
                style={{"--card-color": course.color} as React.CSSProperties}
                className="hover:shadow-md transition-all duration-300 ease-in-out
                flex flex-col gap-2 items-center col-span-1 p-2 rounded-md h-full"
                to={
                    "rating" in course ?
                        `/learn/${course.id}/${course.urlSlug}` :
                        `/courses/${course.id}/${course.urlSlug}`
                }>

                {/*IMAGE*/}
                <div className="overflow-clip rounded-sm">
                    <img src={course.imageUrl} alt={course.title}
                         className="aspect-video object-cover"/>
                </div>

                {/*INFO*/}
                <div className="flex flex-col gap-2 justify-between flex-1 text-left">
                    <h3 className="text-md font-bold">{course.titleShort}</h3>

                    <p className="text-black/60 text-sm">{(() => {
                        const MAX_CHARS = 80;
                        const fullStr = (course.topicsCovered ?? [])
                            .map(item =>
                                fromEnumFormat(item)
                                    // .replace(/\b\w/g, c => c.toUpperCase())
                            )
                            .join(" • ");
                        if (fullStr.length > MAX_CHARS)
                            return fullStr.slice(0, 80).trim() + "...";
                        return fullStr;
                    })()}</p>
                </div>

                {/*PROGRESS BAR*/}
                {
                    "lecturesFinishedCount" in course && (
                        <div className="flex flex-col gap-1 items-start w-full">
                            <div className="w-full bg-gray-200 rounded-full h-0.5">
                                <div
                                    className="h-full rounded-full"
                                    style={{
                                        width: `${(course.lecturesFinishedCount / course.courseLectureCount) * 100}%`,
                                        backgroundColor: course.color,
                                    }}
                                />
                            </div>
                            <div className="flex justify-between w-full">
                                <p className="text-xs text-black/60">
                                    {Math.round(course.lecturesFinishedCount / course.courseLectureCount * 100)}% completed
                                </p>
                                {
                                    course.isFinished && (
                                        <button
                                            onClick={(e) => {
                                                e.preventDefault();
                                                e.stopPropagation();
                                                setShowReviewModal(true)
                                            }}
                                            className="hover:bg-black/5 px-2 rounded-xs
                                    flex gap-1 cursor-pointer"
                                        >
                                            {[1, 2, 3, 4, 5].map((star) => {
                                                const courseRating = course.rating;
                                                const StarIcon = courseRating && star <= courseRating ? StarFilled : StarOutline;
                                                return <StarIcon key={star} className="w-2 text-yellow-400"/>;
                                            })}
                                        </button>
                                    )
                                }
                            </div>
                        </div>
                    )
                }
            </LocalizedLink>

            {
                "lecturesFinishedCount" in course && showReviewModal && (
                    <ModalReviewCourse
                        courseId={course.id}
                        closeModal={() => setShowReviewModal(false)}
                        markCourseAsRated={markCourseAsRated}
                        isUpdate={course.rating !== null}
                    />
                )
            }
        </aside>
    )
}

export default CourseCardLearnDashboard;