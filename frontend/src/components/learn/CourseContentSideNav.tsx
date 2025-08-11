import type {CourseContentFull} from "../../models/javaObjects/CourseContentFull.tsx";
import {useEffect, useState} from "react";
import {ChevronDown, ClipboardList, File, ListChecks, Text, TvMinimalPlay} from "lucide-react";
import type {CourseLectureFull} from "../../models/javaObjects/CourseLectureFull.tsx";
import {X} from "lucide-react";

function CourseContentSideNav({ activeLecture, setActiveLecture, courseContents, updateLecture, closeModal }: {
    activeLecture: CourseLectureFull,
    setActiveLecture: (lecture: CourseLectureFull) => void,
    courseContents: CourseContentFull[] | undefined,
    updateLecture: (progressId: number, isComplete: boolean) => void,
    closeModal: () => void
}) {
    const [openContentIds, setOpenContentIds] = useState<number[]>([]);

    const toggleAccordion = (contentId: number) => {
        if (openContentIds.includes(contentId)) {
            setOpenContentIds(openContentIds.filter(i => i !== contentId));
        } else {
            setOpenContentIds([...openContentIds, contentId]);
        }
    }

    useEffect(() => {
        if (!courseContents || !activeLecture) return;

        const contentWithActiveLecture = courseContents.find(content =>
            content.courseLectures.some(lec => lec.id === activeLecture.id)
        );

        if (contentWithActiveLecture) {
            setOpenContentIds([...openContentIds, contentWithActiveLecture.id]);
        }
    }, [activeLecture, courseContents]);

    return (
        <aside className="sticky top-0 right-0 min-w-28/100 h-screen overflow-y-auto border-l-1 border-black/20">
            <div className="py-4 px-4 flex items-center justify-between">
                <h2 className="text-left font-bold">Course content</h2>
                <button
                    onClick={closeModal}
                    className="hover:bg-black/10 rounded-sm cursor-pointer p-1.5">
                    <X size={20} strokeWidth={1.5}/>
                </button>
            </div>
            {
                courseContents && courseContents.map((content, index) => {
                    const isOpen = openContentIds.includes(content.id);

                    {/*CONTENT*/}
                    return (
                    <div
                        key={index}
                        className="flex flex-col border-t-2 border-black/20">
                        <button
                            onClick={() => toggleAccordion(content.id)}
                            className="py-4 px-4 bg-black/5 flex flex-col items-start gap-2 cursor-pointer">
                            <div className="flex items-start gap-2 justify-between w-full">
                                <h3 className="text-left font-bold">Module {index+1}: {content.title}</h3>
                                <ChevronDown height={20}
                                className={`${isOpen && "-rotate-180"} transition-all duration-500 ease-out`}/>
                            </div>

                            <span className="text-xs text-black text-light">
                                {content.courseLectures.filter(lecture => lecture.userCourseProgress.completed).length} / {content.courseLectures.length} | {content.courseLectures.reduce((acc, lecture) => acc + lecture.durationMinutes, 0)}min
                            </span>
                        </button>

                        {/*LECTURES*/}
                        {isOpen && content.courseLectures.map((lecture, lectureIndex) => {
                            const isActive = activeLecture.id === lecture.id;
                            return (
                                <button
                                    key={lectureIndex}
                                    onClick={() => setActiveLecture(lecture)}
                                    className={`${isActive && "bg-dark-blue/20"}
                                    ${!isActive && "hover:bg-dark-blue/10"} cursor-pointer
                                    py-4 px-4 flex gap-2 items-start`}>
                                    <input
                                        onClick={e => e.stopPropagation()}
                                        onChange={() => updateLecture(lecture.userCourseProgress.id, !lecture.userCourseProgress.completed)}
                                        checked={lecture.userCourseProgress.completed}
                                        type={"checkbox"}
                                        className="w-4 aspect-square"
                                    />

                                    <div className="flex flex-col gap-2 text-left">
                                        <h4 className="leading-tight text-sm">{lecture.title}</h4>
                                        <span className={`flex items-center gap-2 text-xs ${isActive ? "opacity-100" : "opacity-60"}`}>
                                            {lecture.contentType === "VIDEO" && <TvMinimalPlay  size={16} strokeWidth={1.5} />}
                                            {lecture.contentType === "TEXT" && <Text size={16} strokeWidth={1.5} />}
                                            {lecture.contentType === "FILE" && <File size={16} strokeWidth={1.5} />}
                                            {lecture.contentType === "QUIZ" && <ListChecks size={16} strokeWidth={1.5} />}
                                            {lecture.contentType === "TOOL" && <ClipboardList size={16} strokeWidth={1.5} />}
                                            {lecture.durationMinutes}min
                                        </span>
                                    </div>
                                </button>
                            )
                        })}
                    </div>
                )})
            }
        </aside>
    )
}

export default CourseContentSideNav;