import type {Dispatch, SetStateAction} from "react";
import type {ContentType} from "../../../models/types/ContentType.tsx";
import AdminTranslateCourseInputTextArea from "./input/AdminTranslateCourseInputTextArea.tsx";
import type {CourseFull} from "../../types/CourseFull.tsx";
import type {CourseTranslate} from "../../types/CourseTranslate.tsx";
import AdminTranslateCourseInput from "./input/AdminTranslateCourseInput.tsx";
import AdminTranslateCourseDisabledInput from "./input/AdminTranslateCourseDisabledInput.tsx";

function AdminTranslateCourseContents({initCourse, course, setCourse, courseLectureFiles, setCourseLectureFiles}: {
    initCourse: CourseFull;
    course: CourseTranslate;
    setCourse: (course: CourseTranslate) => void;
    courseLectureFiles: { file: File, type: ContentType, contentPosition: number, lecturePosition: number }[];
    setCourseLectureFiles: Dispatch<SetStateAction<{
        file: File,
        type: ContentType,
        contentPosition: number,
        lecturePosition: number
    }[]>>;
}) {

    const combined = course.courseContents.map((content, i) => (
        { content: content, initContent: initCourse.courseContents[i]}
    ))

    return (
        <div className="flex flex-col gap-4">
            {/*COURSE CONTENT*/}
            {
                combined.length > 0 && combined.map(({ content, initContent }, index) => {
                    return (
                        <div key={`content-${index}`}
                             className="w-full flex flex-col gap-4"
                        >
                            {/*CONTENT HEADER*/}
                            <div className="flex justify-between items-center">
                                <h3 className="font-semibold text-black/80 text-2xl text-left">Module {index + 1}</h3>
                            </div>

                            <AdminTranslateCourseInput
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
                                translateFrom={initContent.title}
                            />

                            {/*LECTURES*/}
                            <div
                                className={`relative grid grid-cols-6 grid-rows-[${content.courseLectures.length}]`}>
                                {/* DOTS COLUMN */}
                                {content.courseLectures.map((_, lectureIndex) => (
                                    <div
                                        key={`dot-${lectureIndex}`}
                                        className="col-span-1 flex flex-col items-center justify-start"
                                        style={{ gridRow: `${lectureIndex + 1} / ${lectureIndex + 2}` }} // one row per lecture
                                    >
                                        <div className="w-8 aspect-square bg-gray rounded-full z-10" />
                                        {lectureIndex !== content.courseLectures.length - 1 && (
                                            <div className="w-1 h-full bg-gray rounded-full" />
                                        )}
                                    </div>
                                ))}

                                {/* LECTURE CONTENTS */}
                                {
                                    content.courseLectures.length > 0 &&  content.courseLectures.map((lecture, i) => (
                                        { lecture: lecture, initLecture: initContent.courseLectures[i]}
                                    )).map(({ lecture, initLecture }, lectureIndex) => {
                                        return (
                                            <div key={`content-${index}-lecture-${lectureIndex}`}
                                                 className="col-span-5 col-start-2 row-span-1 flex flex-col gap-4 pb-12">
                                                {/*LECTURE HEADER*/}
                                                <div className="flex justify-between items-center">
                                                    <h3 className="font-semibold text-black/80 text-2xl text-left">Lecture {lectureIndex + 1}</h3>
                                                </div>

                                                <div className="flex gap-20 justify-between w-full">
                                                    <AdminTranslateCourseInput
                                                        label={"Title"}
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
                                                        translateFrom={initLecture.title}
                                                    />
                                                    <AdminTranslateCourseDisabledInput
                                                        label={`Duration (in minutes)`}
                                                        value={initLecture.durationMinutes}
                                                        width={"fit"}
                                                    />
                                                    <AdminTranslateCourseDisabledInput
                                                        label={"Content Type"}
                                                        value={initLecture.contentType}
                                                        width={"fit"}
                                                    />
                                                </div>
                                                <AdminTranslateCourseInputTextArea
                                                    label={"Description Short (1-2 sentences)"}
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
                                                    })}
                                                    translateFrom={initLecture.description}
                                                />
                                                <AdminTranslateCourseInputTextArea
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
                                                    })}
                                                    translateFrom={initLecture.contentText}
                                                />
                                                {
                                                    lecture.contentType !== "TEXT" && (
                                                        <AdminTranslateCourseInput
                                                            label={`Content ${lecture.contentType.charAt(0) + lecture.contentType.slice(1).toLowerCase()}`}
                                                            onChange={e => {
                                                                const file = e.target.files && e.target.files[0];
                                                                if (!file) return;

                                                                setCourseLectureFiles([
                                                                    ...courseLectureFiles.filter(item => item.lecturePosition !== initLecture.position || item.contentPosition !== initContent.position),
                                                                    {
                                                                        file,
                                                                        type: lecture.contentType,
                                                                        contentPosition: initContent.position,
                                                                        lecturePosition: initLecture.position
                                                                    }
                                                                ]);
                                                            }}
                                                            type={"file"}
                                                            width={"full"}
                                                            translateFrom={""}
                                                        />
                                                    )
                                                }
                                            </div>
                                        )
                                    })
                                }
                            </div>

                            {
                                index !== course.courseContents.length - 1 && (
                                    <hr className="border-t-4 w-full border-black/20 rounded-full my-4"/>
                                )
                            }
                        </div>
                    )
                })
            }
        </div>
    );
}

export default AdminTranslateCourseContents;