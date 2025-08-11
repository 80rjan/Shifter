import {useParams} from "react-router-dom";
import {useAuthContext} from "../context/AuthContext.tsx";
import CourseContentSideNav from "../components/learn/CourseContentSideNav.tsx";
import CourseLearnSkeleton from "../components/skeletons/CourseLearnSkeleton.tsx";
import {ArrowLeft, MoveLeft, MoveRight} from "lucide-react";
import TextWithUnderline from "../components/TextWithUnderline.tsx";
import {useCourseLearn} from "../hooks/useCourseLearn.tsx";
import {useEffect, useRef, useState} from "react";

function CourseLearn() {
    const {courseId} = useParams<{ courseId: string }>();
    const {accessToken} = useAuthContext();
    const [showSideNav, setShowSideNav] = useState(true);
    const {
        activeLecture,
        setActiveLecture,
        course,
        loading,
        videoUrl,
        isDownloading,
        updateLecture,
        triggerDownload,
    } = useCourseLearn(Number(courseId), accessToken || "");
    const spanRef = useRef<HTMLSpanElement>(null);
    const [spanWidth, setSpanWidth] = useState(0);
    const [isBtnHovered, setIsBtnHovered] = useState(false);

    useEffect(() => {
        if (spanRef.current) {
            setSpanWidth(spanRef.current.offsetWidth);
        }
    }, [showSideNav]);

    useEffect(() => {
        window.scrollTo({
            top: 0,
            behavior: "smooth",
        });
    }, [activeLecture]);


    if (loading) {
        return (
            <CourseLearnSkeleton/>
        );
    }

    const firstLectureId = course?.courseContents[0].courseLectures[0].id;
    const allLectures = course?.courseContents.flatMap(content => content.courseLectures);
    const lastLectureId = allLectures ? allLectures[allLectures.length - 1].id : undefined;

    return (
        <main className="flex relative overflow-x-clip">
            <div className="flex flex-col">
                {
                    activeLecture?.contentType === "VIDEO" && (
                        <video
                            src={videoUrl}
                            controls
                            controlsList="nodownload"
                            preload="metadata"
                        />
                    )
                }
                <div
                    className="flex flex-col gap-4 flex-grow py-vertical-md px-horizontal-sm text-left text-black-text">
                    <h1 className="text-4xl font-semibold">{activeLecture?.title}</h1>
                    <p className="text-lg leading-loose">{activeLecture?.contentText}</p>
                    {
                        (activeLecture?.contentType === "FILE" || activeLecture?.contentType === "TOOL" || activeLecture?.contentType === "QUIZ") && (
                            <div className="flex justify-between w-full gap-20 items-center py-12">
                                <p className="text-lg font-medium">
                                    {activeLecture.contentFileName}
                                </p>
                                <button
                                    disabled={isDownloading}
                                    onClick={() => triggerDownload()}
                                    className={`disabled:cursor-not-allowed disabled:opacity-40 hover:shadow-lg transition-all duration-300 ease-in-out cursor-pointer
                                    bg-shifter text-white text-lg px-8 py-2 rounded-md shadow-md border-2 border-white/40 shadow-shifter/40`}
                                >
                                    {
                                        isDownloading ? "Downloading..." : "Download File"
                                    }
                                </button>
                            </div>
                        )
                    }
                </div>

                <div className="px-horizontal-sm py-vertical-md w-full flex justify-between items-center">
                    {
                        firstLectureId !== activeLecture?.id && (
                            <button
                                onClick={() => {
                                    if (!course || !activeLecture) return;
                                    const allLectures = course.courseContents.flatMap(content => content.courseLectures);
                                    const currentIndex = allLectures.findIndex(lec => lec.id === activeLecture.id);
                                    if (currentIndex > 0) {
                                        setActiveLecture(allLectures[currentIndex - 1]);
                                    }
                                }}
                                className="mr-auto flex items-center gap-2 text-lg cursor-pointer py-2">
                                <MoveLeft size={20} strokeWidth={2}/>
                                <TextWithUnderline label={"Previous Lecture"} fromRightToLeft={true}/>
                            </button>
                        )
                    }
                    {
                        lastLectureId !== activeLecture?.id && (
                            <button
                                onClick={() => {
                                    updateLecture(activeLecture?.userCourseProgress.id || -1, true)
                                    if (!course || !activeLecture) return;
                                    const allLectures = course.courseContents.flatMap(content => content.courseLectures);
                                    const currentIndex = allLectures.findIndex(lec => lec.id === activeLecture.id);
                                    if (currentIndex < allLectures.length - 1) {
                                        setActiveLecture(allLectures[currentIndex + 1]);
                                    }
                                }}
                                className="ml-auto flex items-center gap-2 text-lg cursor-pointer py-2">
                                <TextWithUnderline label={"Next Lecture"}/>
                                <MoveRight size={20} strokeWidth={2}/>
                            </button>
                        )
                    }
                </div>
            </div>
            {
                activeLecture && course && showSideNav ? (
                    <CourseContentSideNav
                        activeLecture={activeLecture}
                        setActiveLecture={setActiveLecture}
                        courseContents={course.courseContents}
                        updateLecture={updateLecture}
                        closeModal={() => setShowSideNav(false)}
                    />
                ) : (
                    <button
                        onMouseEnter={() => setIsBtnHovered(true)}
                        onMouseLeave={() => setIsBtnHovered(false)}
                        onClick={() => {
                            setShowSideNav(true)
                            setIsBtnHovered(false)
                        }}
                        className="absolute top-12 right-0 flex items-center gap-2
                        h-fit bg-shifter px-4 py-2 border border-white/40 rounded-sm cursor-pointer group overflow-hidden hover:translate-x-0"
                        style={{
                            transform: !isBtnHovered ? `translateX(${spanWidth + 16}px)` : "translateX(0)",
                            transition: "transform 0.5s ease",
                        }}
                    >
                        <ArrowLeft
                            size={22}
                            strokeWidth={1.5}
                            color={"var(--color-white)"}
                        />
                        <span
                            ref={spanRef}
                            className="text-white whitespace-nowrap opacity-0 group-hover:opacity-100 transition-opacity duration-700 ease-in-out"
                        >
                            Course Content
                        </span>
                    </button>

                )
            }
        </main>
    )
}

export default CourseLearn;