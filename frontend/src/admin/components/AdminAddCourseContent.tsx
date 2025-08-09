import type {Dispatch, SetStateAction} from "react";
import AdminAddCourseInput from "./AdminAddCourseInput.tsx";
import type {ContentType} from "../../models/types/ContentType.tsx";
import AdminAddCourseInputSelect from "./AdminAddCourseInputSelect.tsx";
import AdminAddCourseInputTextArea from "./AdminAddCourseInputTextArea.tsx";
import {X, MoveUp, MoveDown, ChevronUp, ChevronDown} from "lucide-react";
import type {CourseEntity} from "../types/CourseEntity.tsx";

function AdminAddCourseContents({
                                          course,
                                          setCourse,
                                          courseLectureFiles,
                                          setCourseLectureFiles
                                      }: {
    course: CourseEntity;
    setCourse: Dispatch<SetStateAction<CourseEntity>>;
    courseLectureFiles: { file: File, type: ContentType, contentPosition: number, lecturePosition: number }[];
    setCourseLectureFiles: Dispatch<SetStateAction<{
        file: File,
        type: ContentType,
        contentPosition: number,
        lecturePosition: number
    }[]>>;
}) {
    return (
        <div className="flex flex-col gap-4">
            {/*COURSE CONTENT*/}
            {
                course.courseContents.length > 0 && course.courseContents.map((content, index) => {
                    return (
                        <div key={`content-${index}`}
                             className="w-full flex flex-col gap-4"
                        >
                            {/*CONTENT HEADER*/}
                            <div className="flex justify-between items-center">
                                <h3 className="font-semibold text-black/80 text-2xl text-left">Module {index + 1}</h3>

                                {/*Header Buttons*/}
                                <div className="flex gap-2">
                                    {index > 0 && (
                                        <button
                                            onClick={() => {
                                                const updated = [...course.courseContents];
                                                [updated[index - 1], updated[index]] = [updated[index], updated[index - 1]];
                                                updated[index - 1] = {...updated[index - 1], position: index};
                                                updated[index] = {...updated[index], position: index + 1};
                                                setCourse({
                                                    ...course,
                                                    courseContents: updated
                                                });
                                            }}
                                            className="hover:bg-gray/60 transition-all duration-300 ease-out cursor-pointer
                                            rounded-sm"
                                        >
                                            <MoveUp size={28} opacity={0.6}/>
                                        </button>
                                    )}
                                    {index < course.courseContents.length - 1 && (
                                        <button
                                            onClick={() => {
                                                const updated = [...course.courseContents];
                                                [updated[index], updated[index + 1]] = [updated[index + 1], updated[index]];
                                                updated[index] = {...updated[index], position: index + 1};
                                                updated[index + 1] = {...updated[index + 1], position: index + 2};
                                                setCourse({
                                                    ...course,
                                                    courseContents: updated
                                                });
                                            }}
                                            className="hover:bg-gray/60 transition-all duration-300 ease-out cursor-pointer
                                            rounded-sm"
                                        >
                                            <MoveDown size={28} opacity={0.6}/>
                                        </button>
                                    )}
                                    <button
                                        className="hover:bg-gray/60 hover:text-black rounded-sm px-4 py-2 cursor-pointer text-black/80"
                                        onClick={() => setCourse({
                                            ...course,
                                            courseContents: course.courseContents
                                                .filter((_, i) => i !== index)
                                                .map((item, i) => ({...item, position: i + 1}))
                                        })}
                                    >
                                        Delete Module
                                    </button>
                                </div>
                            </div>

                            <AdminAddCourseInput
                                label={"Title"}
                                value={content.title}
                                onChange={e => setCourse({
                                    ...course,
                                    courseContents: course.courseContents.map((item, i) => i === index ? {
                                        ...item,
                                        title: e.target.value
                                    } : item)
                                })}
                                type={"text"}
                                width={"full"}
                            />

                            {/*LECTURES*/}
                            <div
                                className={`relative grid grid-cols-6 grid-rows-[${content.courseLectures.length}] gap-y-12`}>
                                <div
                                    className="col-span-1 flex flex-col gap-12 overflow-clip"
                                    style={{
                                        gridRow: `span ${content.courseLectures.length} / span ${content.courseLectures.length}`
                                    }}
                                >
                                    {
                                        content.courseLectures.length > 0 && content.courseLectures.map((_, lectureIndex) => (
                                            <div key={`content-${index}-lectureDot-${lectureIndex}`}
                                                 className="relative h-full flex justify-center items-start"
                                            >
                                                <div
                                                    className="w-8 aspect-square bg-gray rounded-full z-10"/>
                                                {
                                                    lectureIndex === content.courseLectures.length - 1 ? (
                                                        <div
                                                            className="absolute w-1 h-50/10 bg-white z-1 rounded-full"/>
                                                    ) : (
                                                        <div
                                                            className="absolute w-1 h-50/10 bg-gray rounded-full"/>
                                                    )
                                                }
                                            </div>
                                        ))
                                    }
                                </div>
                                {
                                    content.courseLectures.length > 0 && content.courseLectures.map((lecture, lectureIndex) => {
                                        return (
                                            <div key={`content-${index}-lecture-${lectureIndex}`}
                                                 className="col-span-5 col-start-2 row-span-1 flex flex-col gap-4">
                                                {/*LECTURE HEADER*/}
                                                <div className="flex justify-between items-center">
                                                    <h3 className="font-semibold text-black/80 text-2xl text-left">Lecture {lectureIndex + 1}</h3>

                                                    {/*Header Buttons*/}
                                                    <div className="flex gap-2">
                                                        {lectureIndex > 0 && (
                                                            <button
                                                                onClick={() => {
                                                                    const updatedContent = [...course.courseContents];
                                                                    const lectures = [...updatedContent[index].courseLectures];
                                                                    [lectures[lectureIndex - 1], lectures[lectureIndex]] = [lectures[lectureIndex], lectures[lectureIndex - 1]];
                                                                    lectures[lectureIndex - 1] = {
                                                                        ...lectures[lectureIndex - 1],
                                                                        position: lectureIndex
                                                                    };
                                                                    lectures[lectureIndex] = {
                                                                        ...lectures[lectureIndex],
                                                                        position: lectureIndex + 1
                                                                    };
                                                                    updatedContent[index].courseLectures = lectures;
                                                                    setCourse({
                                                                        ...course,
                                                                        courseContents: updatedContent
                                                                    });
                                                                }}
                                                                className="hover:bg-gray/60 transition-all duration-300 ease-out cursor-pointer
                                                                rounded-sm px-1"
                                                            >
                                                                <ChevronUp size={28} opacity={0.6}/>
                                                            </button>
                                                        )}
                                                        {lectureIndex < content.courseLectures.length - 1 && (
                                                            <button
                                                                onClick={() => {
                                                                    const updatedContent = [...course.courseContents];
                                                                    const lectures = [...updatedContent[index].courseLectures];
                                                                    [lectures[lectureIndex], lectures[lectureIndex + 1]] = [lectures[lectureIndex + 1], lectures[lectureIndex]];
                                                                    lectures[lectureIndex] = {
                                                                        ...lectures[lectureIndex],
                                                                        position: lectureIndex + 1
                                                                    };
                                                                    lectures[lectureIndex + 1] = {
                                                                        ...lectures[lectureIndex + 1],
                                                                        position: lectureIndex + 2
                                                                    };
                                                                    updatedContent[index].courseLectures = lectures;
                                                                    setCourse({
                                                                        ...course,
                                                                        courseContents: updatedContent
                                                                    });
                                                                }}
                                                                className="hover:bg-gray/60 transition-all duration-300 ease-out cursor-pointer
                                                                rounded-sm px-1"
                                                            >
                                                                <ChevronDown size={28} opacity={0.6}/>
                                                            </button>
                                                        )}
                                                        <button
                                                            className="hover:bg-gray/60 rounded-sm px-2 py-1 cursor-pointer"
                                                            onClick={() => setCourse({
                                                                ...course,
                                                                courseContents: course.courseContents.map((item, i) => {
                                                                    if (i === index) {
                                                                        const updatedLectures = item.courseLectures
                                                                            .filter((_, li) => li !== lectureIndex)
                                                                            .map((lecture, li) => ({
                                                                                ...lecture,
                                                                                position: li + 1
                                                                            }));
                                                                        return {
                                                                            ...item,
                                                                            courseLectures: updatedLectures
                                                                        };
                                                                    }
                                                                    return item;
                                                                })
                                                            })}
                                                        ><X size={28} opacity={0.6}/></button>
                                                    </div>
                                                </div>

                                                <div className="flex gap-20 justify-between w-full">
                                                    <AdminAddCourseInput
                                                        label={`Title`}
                                                        value={lecture.title}
                                                        onChange={e => setCourse({
                                                            ...course,
                                                            courseContents: course.courseContents.map((item, i) => {
                                                                if (i === index) {
                                                                    const updatedLectures = item.courseLectures.map((l, li) => li === lectureIndex ? {
                                                                        ...l,
                                                                        title: e.target.value
                                                                    } : l);
                                                                    return {...item, courseLectures: updatedLectures};
                                                                }
                                                                return item;
                                                            })
                                                        })}
                                                        type={"text"}
                                                        width={"full"}
                                                    />
                                                    <AdminAddCourseInput
                                                        label={`Duration (in minutes)`}
                                                        value={lecture.durationMinutes}
                                                        onChange={e => setCourse({
                                                            ...course,
                                                            courseContents: course.courseContents.map((item, i) => {
                                                                if (i === index) {
                                                                    const updatedLectures = item.courseLectures.map((l, li) => li === lectureIndex ? {
                                                                        ...l,
                                                                        durationMinutes: Number(e.target.value)
                                                                    } : l);
                                                                    return {...item, courseLectures: updatedLectures};
                                                                }
                                                                return item;
                                                            })
                                                        })}
                                                        type={"number"}
                                                        width={"fit"}
                                                    />
                                                    <AdminAddCourseInputSelect
                                                        label={"Content Type"}
                                                        onChange={e => setCourse({
                                                            ...course,
                                                            courseContents: course.courseContents.map((item, i) => {
                                                                if (i === index) {
                                                                    const updatedLectures = item.courseLectures.map((l, li) => li === lectureIndex ? {
                                                                        ...l,
                                                                        contentType: e.target.value as ContentType
                                                                    } : l);
                                                                    return {...item, courseLectures: updatedLectures};
                                                                }
                                                                return item;
                                                            })
                                                        })}
                                                        options={[
                                                            {value: "TEXT", name: "Text"},
                                                            {value: "FILE", name: "File"},
                                                            {value: "VIDEO", name: "Video"},
                                                            {value: "QUIZ", name: "Quiz"},
                                                            {value: "TOOL", name: "Tool"}
                                                        ]}
                                                    />
                                                </div>
                                                <AdminAddCourseInputTextArea
                                                    label={`Description (2-3 sentences)`}
                                                    rows={3}
                                                    value={lecture.description}
                                                    name={`description-${index}-${lectureIndex}`}
                                                    placeholder={"Enter the description for the lecture here..."}
                                                    onChange={e => setCourse({
                                                        ...course,
                                                        courseContents: course.courseContents.map((item, i) => {
                                                            if (i === index) {
                                                                const updatedLectures = item.courseLectures.map((l, li) => li === lectureIndex ? {
                                                                    ...l,
                                                                    description: e.target.value
                                                                } : l);
                                                                return {...item, courseLectures: updatedLectures};
                                                            }
                                                            return item;
                                                        })
                                                    })}/>
                                                <AdminAddCourseInputTextArea
                                                    label={`Content Text`}
                                                    rows={6}
                                                    value={lecture.contentText}
                                                    name={`contentText-${index}-${lectureIndex}`}
                                                    placeholder={"Enter the content text here..."}
                                                    onChange={e => setCourse({
                                                        ...course,
                                                        courseContents: course.courseContents.map((item, i) => {
                                                            if (i === index) {
                                                                const updatedLectures = item.courseLectures.map((l, li) => li === lectureIndex ? {
                                                                    ...l,
                                                                    contentText: e.target.value
                                                                } : l);
                                                                return {...item, courseLectures: updatedLectures};
                                                            }
                                                            return item;
                                                        })
                                                    })}/>
                                                {
                                                    lecture.contentType !== "TEXT" && (
                                                        <AdminAddCourseInput
                                                            label={`Content ${lecture.contentType.charAt(0) + lecture.contentType.slice(1).toLowerCase()}`}
                                                            onChange={e => {
                                                                const file = e.target.files && e.target.files[0];
                                                                if (!file) return;

                                                                setCourseLectureFiles([
                                                                    ...courseLectureFiles.filter(item => item.lecturePosition !== lecture.position || item.contentPosition !== content.position),
                                                                    {
                                                                        file,
                                                                        type: lecture.contentType,
                                                                        contentPosition: content.position,
                                                                        lecturePosition: lecture.position
                                                                    }
                                                                ]);
                                                            }}
                                                            type={"file"}
                                                            width={"full"}
                                                        />
                                                    )
                                                }
                                            </div>
                                        )
                                    })
                                }
                            </div>

                            <button
                                onClick={() =>
                                    setCourse({
                                        ...course,
                                        courseContents: course.courseContents.map((item, i) => i === index ? {
                                            ...item,
                                            courseLectures: [
                                                ...item.courseLectures,
                                                {
                                                    title: "",
                                                    position: item.courseLectures.length + 1,
                                                    contentText: "",
                                                    contentFileName: "",
                                                    contentType: "TEXT",
                                                    description: "",
                                                    durationMinutes: 0
                                                }
                                            ]
                                        } : item)
                                    })}
                                className="hover:bg-shifter/20 transition-all ease-out duration-300
                                px-12 py-2 rounded-sm text-shifter text-xl font-bold underline w-full cursor-pointer"
                            >
                                Add New Course Lecture
                            </button>

                            {
                                index !== course.courseContents.length - 1 && (
                                    <hr className="border-t-4 w-full border-black/20 rounded-full my-4"/>
                                )
                            }
                        </div>
                    )
                })
            }

            <button
                onClick={() =>
                    setCourse({
                        ...course,
                        courseContents: [
                            ...course.courseContents,
                            {
                                title: "",
                                position: course.courseContents.length + 1,
                                courseLectures: []
                            }
                        ]
                    })
                }
                className="hover:bg-shifter/20 transition-all ease-out duration-300
                px-12 py-2 rounded-sm text-shifter text-xl font-bold underline w-full cursor-pointer "
            >
                Add New Course Content
            </button>
        </div>
    );
}

export default AdminAddCourseContents;