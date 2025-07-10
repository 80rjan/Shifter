import {Check, ChevronDown, ChevronUp, ClipboardList, File, Text, TvMinimalPlay} from "lucide-react"
import ShifterArrow from "../../public/Shifter-Arrow.png";
import {useState} from "react";
import type {CourseDetail} from "../types/CourseDetail.tsx";

function CourseDetailsInfo({ course }: { course: CourseDetail | null}) {
    const [showMore, setShowMore] = useState(false);
    const [openIndices, setOpenIndices] = useState<number[]>([]);

    const snippetLength = 800;
    const description = course?.descriptionLong || "";
    const shortDescription = description.length > snippetLength
        ? description.slice(0, snippetLength) + "..."
        : description;

    const toggleAccordion = (index: number) => {
        if (openIndices.includes(index)) {
            setOpenIndices(openIndices.filter(i => i !== index));
        } else {
            setOpenIndices([...openIndices, index]);
        }
    }

    return (
        <>
            {/*WHAT WILL BE LEARNED*/}
            <section className="relative flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md overflow-clip ">
                <img src={ShifterArrow} alt="Shifter Arrow"
                     className="absolute opacity-10 h-150 w-120 -rotate-130 -top-40 right-0"/>
                <img src={ShifterArrow} alt="Shifter Arrow"
                     className="absolute opacity-10 h-150 w-120 rotate-50 -bottom-40 left-0"/>

                <h2 className="text-5xl">What you'll learn</h2>
                <div className="grid grid-cols-2 gap-y-4 gap-x-20">
                    {
                        course?.whatWillBeLearned.map((learning, index) => (
                            <div className="flex items-center gap-2" key={index}>
                                <Check size={40} strokeWidth={1} color={"var(--color-shifter)"}/>
                                <p className="text-xl font-normal">{learning}</p>
                            </div>
                        ))
                    }
                </div>
            </section>

            {/*COURSE CONTENT*/}
            <section
                className="relative flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md overflow-clip ">
                <h2 className="text-5xl">Course content</h2>
                <div>
                    {
                        course?.courseContents.map((content, index) => {
                            const isOpen = openIndices.includes(index);

                            return (
                                <div className={`border-1 border-black/20 ${index !== course?.courseContents.length - 1 ? "border-b-0" : ""}`}>
                                    <div
                                        key={index}
                                        className="overflow-clip flex justify-between items-center px-4 py-4 bg-black/5 cursor-pointer"
                                        onClick={() => toggleAccordion(index)}
                                    >
                                        <div className="flex gap-4 items-center">
                                            <ChevronDown size={32} strokeWidth={1}
                                                         className={`text-black ${isOpen ? "rotate-180" : "rotate-0"} transition-all duration-500 ease-in-out `}
                                            />
                                            <h3 className="text-2xl font-semibold">{content.title}</h3>
                                        </div>

                                        <div className="flex gap-2 items-start text-black/80">
                                            <span>{content.courseLectures.length} lectures</span>
                                            <span>•</span>
                                            <span>{Math.round(content.courseLectures.reduce((sum, lecture) => sum + lecture.durationMinutes, 0))}min</span>
                                        </div>
                                    </div>

                                    { isOpen &&
                                        (
                                            <div className="border-t-1 border-black/20 py-4 text-black/80">
                                                {content.courseLectures.map((lecture, lectureIndex) => {
                                                    return (
                                                        <div
                                                            key={lectureIndex}
                                                            className="flex justify-between px-6 py-2">
                                                            <div className="flex gap-4">
                                                                {lecture.contentType === "VIDEO" && <TvMinimalPlay size={20} strokeWidth={1.5} />}
                                                                {lecture.contentType === "TEXT" && <Text size={20} strokeWidth={1.5} />}
                                                                {lecture.contentType === "FILE" && <File size={20} strokeWidth={1.5} />}
                                                                {lecture.contentType === "QUIZ" && <ClipboardList size={20} strokeWidth={1.5} />}
                                                                <h4>{lecture.title}</h4>
                                                            </div>
                                                            <span>{lecture.durationMinutes}min</span>
                                                        </div>
                                                    )
                                                })}
                                            </div>
                                        )
                                    }
                                </div>
                            )
                        })
                    }
                </div>
            </section>

            {/*DESCRIPTION*/}
            <section className="flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md">
                <h2 className="text-5xl">Course description</h2>

                <div>
                    <div className="relative overflow-hidden">
                        <p className="text-lg leading-loose whitespace-pre-line">
                            {showMore ? description : shortDescription}
                        </p>

                        {/* Show the fade overlay only when text is truncated */}
                        {!showMore && (
                            <div
                                className="pointer-events-none absolute bottom-0 left-0 w-full h-24
                   bg-gradient-to-t from-white to-transparent"
                            />
                        )}
                    </div>
                    {description.length > snippetLength && (
                        <button
                            onClick={() => setShowMore(!showMore)}
                            className="mt-4 underline decoration-current cursor-pointer font-bold text-shifter flex items-center
                 gap-2 px-4 py-2 rounded-sm hover:bg-shifter/20 self-start"
                            aria-label={showMore ? "Show less description" : "Show more description"}
                        >
                            {showMore ? (
                                <>
                                    Show less
                                    <ChevronDown/>
                                </>
                            ) : (
                                <>
                                    Show more
                                    <ChevronUp/>
                                </>
                            )}
                        </button>
                    )}
                </div>

            </section>

        </>
    )
}

export default CourseDetailsInfo;