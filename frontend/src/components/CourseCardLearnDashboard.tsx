import type {CoursePreviewEnrolled} from "../models/javaObjects/CoursePreviewEnrolled.tsx";
import {Link} from "react-router-dom";
import React from "react";
import {toUrlFormat} from "../utils/toUrlFormat.ts";
import StarFilled from "../assets/icons/StarFilled.tsx";
import StarOutline from "../assets/icons/StarOutline.tsx";
import ModalReviewCourse from "./ModalReviewCourse.tsx";
import type {CoursePreview} from "../models/javaObjects/CoursePreview.tsx";

function CourseCardLearnDashboard({course, markCourseAsRated}: {
    course: CoursePreviewEnrolled | CoursePreview;
    markCourseAsRated: (rating: number) => void;
}) {
    const [showReviewModal, setShowReviewModal] = React.useState<boolean>(false);

    return (
        <aside className="h-full">
            <Link
                style={{"--card-color": course.color} as React.CSSProperties}
                className="hover:shadow-md transition-all duration-300 ease-in-out
                flex flex-col gap-2 items-center col-span-1 p-2 rounded-md h-full"
                to={
                    "rating" in course ?
                        `/learn/${course.id}/${toUrlFormat(course.titleShort)}` :
                        `/courses/${course.id}//${toUrlFormat(course.titleShort)}`
                }>

                {/*IMAGE*/}
                <div className="overflow-clip rounded-sm">
                    <img src={course.imageUrl} alt={course.title}
                         className="aspect-video object-cover"/>
                </div>

                {/*INFO*/}
                <div className="flex flex-col gap-2 justify-between flex-1 text-left">
                    <h3 className="text-md font-bold">{course.titleShort}</h3>

                    <p className="text-black/60 text-sm">{
                        course.topicsCovered.map(item =>
                            item
                                .toLowerCase()
                                .replace(/_/g, " ")
                                .replace(/\b\w/g, c => c.toUpperCase())
                        )
                            .join(" • ")
                    }</p>
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
                                    {course.lecturesFinishedCount / course.courseLectureCount * 100}% completed
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
                                                const StarIcon = courseRating > 0 && star <= courseRating ? StarFilled : StarOutline;
                                                return <StarIcon key={star} className="w-2 text-yellow-400"/>;
                                            })}
                                        </button>
                                    )
                                }
                            </div>
                        </div>
                    )
                }
            </Link>

            {
                "lecturesFinishedCount" in course && showReviewModal && (
                    <ModalReviewCourse
                        courseId={course.id}
                        closeModal={() => setShowReviewModal(false)}
                        markCourseAsRated={markCourseAsRated}
                        isUpdate={true}
                    />
                )
            }
        </aside>
    )
}

export default CourseCardLearnDashboard;