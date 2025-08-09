import type {CourseContentFull} from "../../models/javaObjects/CourseContentFull.tsx";
import {useState} from "react";
import {ChevronDown, ClipboardList, File, ListChecks, Text, TvMinimalPlay} from "lucide-react";
import type {CourseLectureFull} from "../../models/javaObjects/CourseLectureFull.tsx";

function CourseContentSideNav({ setActiveLecture, courseContents }: {
    setActiveLecture: (lecture: CourseLectureFull) => void,
    courseContents: CourseContentFull[]
}) {
    const [openIndices, setOpenIndices] = useState<number[]>([]);

    const toggleAccordion = (index: number) => {
        if (openIndices.includes(index)) {
            setOpenIndices(openIndices.filter(i => i !== index));
        } else {
            setOpenIndices([...openIndices, index]);
        }
    }

    return (
        <aside className="sticky top-0 right-0 min-w-28/100 h-screen overflow-y-auto border-l-1 border-black/20">
            {
                courseContents && courseContents.map((content, index) => {
                    const isOpen = openIndices.includes(index);

                    return (
                    <div
                        key={index}
                        className="flex flex-col border-t-2 border-black/20">
                        {/*Content*/}
                        <button
                            onClick={() => toggleAccordion(index)}
                            className="py-4 px-4 bg-black/5 flex flex-col items-start gap-2 cursor-pointer">
                            <div className="flex items-start gap-2 justify-between w-full">
                                <h3 className="text-left font-bold">Module {index+1}: {content.title}</h3>
                                <ChevronDown height={20}
                                className={`${isOpen && "-rotate-180"} transition-all duration-500 ease-out`}/>
                            </div>

                            <span className="text-xs text-black text-light">
                                0 / {content.courseLectures.length} | {content.courseLectures.reduce((acc, lecture) => acc + lecture.durationMinutes, 0)}min
                            </span>
                        </button>

                        {/*Lecture*/}
                        {isOpen && content.courseLectures.map((lecture, lectureIndex) => {
                            return (
                                <button
                                    key={lectureIndex}
                                    onClick={() => setActiveLecture(lecture)}
                                    className="hover:bg-shifter/5 cursor-pointer
                                    py-4 px-4 flex gap-2 items-start">
                                    <input
                                        type={"checkbox"}
                                        className="w-4 aspect-square"
                                    />

                                    <div className="flex flex-col gap-2 text-left">
                                        <h4 className="leading-tight text-sm">{lecture.title}</h4>
                                        <span className="flex items-center gap-2 text-xs opacity-80">
                                            {lecture.contentType === "VIDEO" && <TvMinimalPlay size={16} strokeWidth={1.5} />}
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