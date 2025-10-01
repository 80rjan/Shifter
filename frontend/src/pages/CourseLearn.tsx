import {Link, useParams} from "react-router-dom";
import {useAuthContext} from "../context/AuthContext.tsx";
import CourseContentSideNav from "../components/learn/CourseContentSideNav.tsx";
import CourseLearnSkeleton from "../components/skeletons/CourseLearnSkeleton.tsx";
import {ArrowLeft, MoveLeft, MoveRight, CheckCheck} from "lucide-react";
import TextWithUnderline from "../components/TextWithUnderline.tsx";
import {useCourseLearn} from "../hooks/useCourseLearn.tsx";
import {type JSX, useEffect, useRef, useState} from "react";
import StarFilled from "../assets/icons/StarFilled.tsx";
import NavbarLearn from "../layout/NavbarLearn.tsx";
import ModalReviewCourse from "../components/ModalReviewCourse.tsx";

function CourseLearn() {
    const {courseId} = useParams<{ courseId: string }>();
    const {accessToken} = useAuthContext();
    const [showSideNav, setShowSideNav] = useState(true);
    const [showReviewModal, setShowReviewModal] = useState(false);
    const {
        activeLecture,
        setActiveLecture,
        course,
        setCourse,
        loading,
        videoUrl,
        isDownloading,
        updateLecture,
        triggerDownload,
        isLastLectureFinished,
        setIsLastLectureFinished,
        courseFinishedPunchlines,
        progressPercentage
    } = useCourseLearn(Number(courseId), accessToken || "");
    const spanRef = useRef<HTMLSpanElement>(null);
    const [spanWidth, setSpanWidth] = useState(0);
    const [isBtnHovered, setIsBtnHovered] = useState(false);

    const firstLectureId = course?.courseContents[0].courseLectures[0].id;
    const allLectures = course?.courseContents.flatMap(content => content.courseLectures);
    const lastLectureId = allLectures ? allLectures[allLectures.length - 1].id : undefined;


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

    const markCourseAsRated = (newRating: number) => {
        if (!course) return;
        setCourse({...course, rating: newRating})
    }

    if (loading) {
        return (
            <>
                <NavbarLearn courseRating={0} markCourseAsRated={markCourseAsRated}/>
                <CourseLearnSkeleton/>
            </>
        );
    }

    return (
        <>
            <NavbarLearn courseRating={course?.rating} markCourseAsRated={markCourseAsRated}/>
            <main className="flex overflow-x-clip">
                {
                    isLastLectureFinished ? (
                        <div className="flex flex-col gap-12 w-full justify-center items-center h-screen ">
                            {
                                progressPercentage !== 100 ? (
                                    <LastLectureFinished
                                        title={"🎉 You've finished the last lesson in this course!"}
                                        onClick={() => {
                                            const firstUncompletedLecture = course?.courseContents
                                                ?.flatMap(content => content.courseLectures)
                                                ?.find(lecture => !lecture.userCourseProgress.completed);
                                            setActiveLecture(firstUncompletedLecture || course?.courseContents[0]?.courseLectures[0] || null);
                                            setIsLastLectureFinished(false);
                                        }}
                                        btnText={"Finish Other Lectures"}
                                        btnShow={true}
                                    />
                                ) : (
                                    <LastLectureFinished
                                        title={courseFinishedPunchlines[Math.floor(Math.random() * courseFinishedPunchlines.length)]}
                                        onClick={() => {
                                            setShowReviewModal(true);
                                        }}
                                        btnText={<>
                                            <StarFilled className="text-gold h-6 "/>
                                            Leave a Review
                                        </>}
                                        btnShow={(course?.rating ?? 0) === 0}
                                    />
                                )
                            }
                        </div>
                    ) : (
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
                                    lastLectureId === activeLecture?.id ? (
                                        <button
                                            onClick={() => {
                                                updateLecture(activeLecture?.userCourseProgress.id || -1, true);
                                                setIsLastLectureFinished(true);
                                            }}
                                            className="ml-auto flex items-center gap-2 text-lg cursor-pointer py-2">
                                            <TextWithUnderline label={"Finish Course"}/>
                                            <CheckCheck size={20} strokeWidth={2}/>
                                        </button>
                                    ) : (
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
                    )
                }
                {
                    activeLecture && course && showSideNav ? (
                        <CourseContentSideNav
                            activeLecture={activeLecture}
                            setActiveLecture={setActiveLecture}
                            courseContents={course.courseContents}
                            updateLecture={updateLecture}
                            closeModal={() => setShowSideNav(false)}
                            progressPercentage={progressPercentage}
                        />
                    ) : (
                        <button
                            onMouseEnter={() => setIsBtnHovered(true)}
                            onMouseLeave={() => setIsBtnHovered(false)}
                            onClick={() => {
                                setShowSideNav(true)
                                setIsBtnHovered(false)
                            }}
                            className="fixed top-24 right-0 flex items-center gap-2
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
                {
                    showReviewModal && course && (
                        <ModalReviewCourse
                            courseId={course.id}
                            closeModal={() => setShowReviewModal(false)}
                            markCourseAsRated={markCourseAsRated}
                            isUpdate={false}
                        />
                    )
                }
            </main>
        </>
    )
}

function LastLectureFinished({title, onClick, btnText, btnShow}: {
    title: string,
    onClick: () => void,
    btnText: string | JSX.Element,
    btnShow: boolean
}) {

    return (
        <>
            <h1 className="text-3xl font-semibold">{title}</h1>
            <div className="flex flex-col gap-4">
                {
                    btnShow && (
                        <button
                            onClick={onClick}
                            className="hover:bg-shifter/10
                            flex gap-2 items-center text-lg  text-shifter font-bold px-8 py-2 rounded-md border-1 border-shifter cursor-pointer">
                            {btnText}
                        </button>
                    )}
                <Link to="/learn"
                      className="text-lg font-bold text-shifter underline hover:text-shifter/60">
                    Exit from Course
                </Link>
            </div>
        </>
    )
}

export default CourseLearn;