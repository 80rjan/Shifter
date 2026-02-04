import type {CourseDetail} from "../models/javaObjects/course/CourseDetail.tsx";
import React from "react";
import {useCourseStorage} from "../context/CourseStorage.ts";
import {toUrlFormat} from "../utils/toUrlFormat.ts";
import {useTranslation} from "react-i18next";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function HeroCourseDetails({course, enrollUser}: { course: CourseDetail | null, enrollUser: () => Promise<void> }) {
    const [isLoading, setIsLoading] = React.useState<boolean>(false);
    const {enrollments} = useCourseStorage();
    const { t } = useTranslation("courses");

    const tripleInfoArr = t("courseDetails.tripleInfo", { returnObjects: true }) as {
        header: string,
        description: string,
        headerRating: string,
        headerNew: string,
        descriptionRating: string,
        descriptionNew: string,
    }[];
    const tripleInfo = [
        {
            header: `${course?.courseContents && course.courseContents.length} ${tripleInfoArr[0].header}`,
            description: course?.descriptionShort
        },
        {
            header: `${course?.durationMinutes && (course.durationMinutes / 60).toFixed(1)} ${tripleInfoArr[1].header}`,
            description: tripleInfoArr[1].description
        },
        {
            header: course?.averageRating
                ? `${course.averageRating.toFixed(1)} ${tripleInfoArr[2].headerRating}`
                : tripleInfoArr[2].headerNew,
            description: course?.averageRating
                ? tripleInfoArr[2].descriptionRating
                : tripleInfoArr[2].descriptionNew
        }
        ,
    ]

    return (
        <div
            className={`py-4 bg-shifter`}>
            {/*HEADER AND DESCRIPTION*/}
            <section
                className="flex flex-col items-center gap-8 bg-white mx-6 px-horizontal-lg pb-12 pt-40 rounded-xl shadow-lg shadow-black/20 text-black-text">
                <h1 className="text-5xl font-semibold">{course?.title}</h1>
                <p>{course?.description}</p>
                {
                    enrollments && enrollments.some(enrollment => enrollment.courseId === course?.id) ?
                        <div
                            className="flex mt-12 gap-4 items-center bg-gray/60 backdrop-blur-lg border-3 border-black/5 px-2 py-1 w-fit rounded-full">

                            {
                                isLoading ? (
                                    <div className="w-8 loader"></div>
                                ) : (
                                    <LocalizedLink
                                        to={`/learn/${course?.id}/${toUrlFormat(course?.titleShort || "")}`}
                                        className={`
                                            bg-shifter
                                            hover:shadow-lg hover:shadow-deep-green/50 transition-all duration-300 ease-in-out cursor-pointer
                                            shadow-md shadow-deep-green/30 text-white font-medium text-xl border-3 border-white/50 rounded-full px-14 py-2
                                        `}
                                    >{t("courseDetails.buttonEnrolled")}</LocalizedLink>
                                )
                            }
                        </div>
                        :
                        <div
                            className="flex mt-12 gap-4 items-center bg-gray/60 backdrop-blur-lg border-3 border-black/5 px-2 py-1 w-fit rounded-full">
                            <span className="font-semibold text-xl px-8">
                                {course?.price && course.price > 0 ? `$${course?.price}` : t("courseDetails.free")}
                            </span>
                            {
                                isLoading ? (
                                    <div className="w-8 loader"></div>
                                ) : (
                                    <button className={`
                                                bg-shifter
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
                                    >{t("courseDetails.buttonEnroll")}</button>
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