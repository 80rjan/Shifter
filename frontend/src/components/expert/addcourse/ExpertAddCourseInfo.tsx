import type {Difficulty} from "../../../models/types/Difficulty.tsx";
import type {CourseFull} from "../../../models/expert/types/CourseFull.tsx";
import AdminAddCourseInputSelect from "./input/AdminAddCourseInputSelect.tsx";
import type {JSX} from "react";
import AdminAddCourseInput from "./input/AdminAddCourseInput.tsx";
import AdminAddCourseInputTextArea from "./input/AdminAddCourseInputTextArea.tsx";
import AdminAddCourseInputWithPills from "./input/AdminAddCourseInputWithPills.tsx";
import AdminTranslateCourseDisabledInput from "../translatecourse/input/AdminTranslateCourseDisabledInput.tsx";

function ExpertAddCourseInfo({course, setCourse, courseCard, setCourseImage}: {
    course: CourseFull;
    setCourse: (course: CourseFull) => void;
    courseCard: JSX.Element;
    setCourseImage: (image: File | null) => void;
}) {

    return (
        <section className="flex flex-col gap-2">
            <div className="grid grid-cols-3 gap-x-12">
                {/*Course Card*/}
                <div className="col-span-1 h-full">
                    <AdminTranslateCourseDisabledInput
                        label={"Language"}
                        name={"language"}
                        value={course.language}
                        width={"fit"}
                    />

                    <div className="h-full flex items-center">
                        {courseCard}
                    </div>
                </div>

                {/*Course Info*/}
                <div className="flex flex-col gap-4 justify-center col-span-2 w-full">

                    <AdminAddCourseInput
                        label={"Course Image"}
                        onChange={e => {
                            // const file = e.target.files?.[0];
                            // if (!file) return;
                            const file = e.target.files ? e.target.files[0] : null;
                            setCourseImage(file);
                        }}
                        type={"file"}
                        width={"full"}
                    />

                    <AdminAddCourseInput
                        label={"Short Title"}
                        value={course.titleShort}
                        onChange={e => setCourse({
                            ...course,
                            titleShort: e.target.value
                        })}
                        type={"text"}
                        width={"full"}
                    />

                    <AdminAddCourseInput
                        label={"Long Title"}
                        value={course.title}
                        onChange={e => setCourse({
                            ...course,
                            title: e.target.value
                        })}
                        type={"text"}
                        width={"full"}
                    />

                    <AdminAddCourseInputWithPills
                        label="Topics Covered"
                        name="topicsCovered"
                        options={course.topicsCovered}
                        addPill={option => setCourse({
                            ...course,
                            topicsCovered: course.topicsCovered.includes(option) ? course.topicsCovered : [...course.topicsCovered, option]
                        })}
                        removePill={index => setCourse({
                            ...course,
                            topicsCovered: course.topicsCovered.filter((_, i) => i !== index)
                        })}
                        width={"full"}
                    />

                    <div className="flex justify-between w-full">
                        <div className="flex gap-4 w-fit items-end">
                            <AdminAddCourseInput
                                label={"Color (in hex)"}
                                value={course.color}
                                onChange={e => setCourse({
                                    ...course,
                                    color: e.target.value
                                })}
                                type={"text"}
                                width={"fit"}
                            />
                            <div
                                style={{backgroundColor: course.color}}
                                className="ml-auto h-2/3 aspect-square rounded-sm"
                            />
                        </div>
                        <AdminAddCourseInput
                            label={"Price (in euros)"}
                            value={course.price}
                            onChange={e => setCourse({
                                ...course,
                                price: Number(e.target.value)
                            })}
                            type={"number"}
                            width={"fit"}
                        />
                        <AdminAddCourseInputSelect
                            label={"Difficulty"}
                            onChange={e => setCourse({
                                ...course,
                                difficulty: e.target.value as Difficulty
                            })}
                            options={[
                                {value: "BEGINNER", name: "Beginner"},
                                {value: "INTERMEDIATE", name: "Intermediate"},
                                {value: "ADVANCED", name: "Advanced"},
                                {value: "EXPERT", name: "Expert"}
                            ]}
                        />
                    </div>

                    <AdminAddCourseInputWithPills
                        label="Skills Gained"
                        name="skillsGained"
                        options={course.skillsGained}
                        addPill={option => setCourse({
                            ...course,
                            skillsGained: course.skillsGained.includes(option) ? course.skillsGained : [...course.skillsGained, option]
                        })}
                        removePill={index => setCourse({
                            ...course,
                            skillsGained: course.skillsGained.filter((_, i) => i !== index)
                        })}
                        width={"full"}
                    />
                </div>
            </div>

            <div className="flex flex-col gap-6">
                <AdminAddCourseInputWithPills
                    label="What will be learned (1-2 sentences)"
                    name="whatWillBeLearned"
                    options={course.whatWillBeLearned}
                    addPill={option => setCourse({
                        ...course,
                        whatWillBeLearned: course.whatWillBeLearned.includes(option) ? course.whatWillBeLearned : [...course.whatWillBeLearned, option]
                    })}
                    removePill={index => setCourse({
                        ...course,
                        whatWillBeLearned: course.whatWillBeLearned.filter((_, i) => i !== index)
                    })}
                    width={"full"}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Short (1-2 sentences)"}
                    name={"descriptionShort"}
                    value={course.descriptionShort}
                    placeholder={"Enter a short description of the course"}
                    rows={1}
                    onChange={e => setCourse({
                        ...course,
                        descriptionShort: e.target.value
                    })}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Medium (2-3 sentences)"}
                    name={"description"}
                    value={course.description}
                    placeholder={"Enter a description of the course"}
                    rows={3}
                    onChange={e => setCourse({
                        ...course,
                        description: e.target.value
                    })}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Long"}
                    name={"descriptionLong"}
                    value={course.descriptionLong}
                    placeholder={"Enter a detailed description of the course"}
                    rows={6}
                    onChange={e => setCourse({
                        ...course,
                        descriptionLong: e.target.value
                    })}
                />
            </div>
        </section>
    )
}

export default ExpertAddCourseInfo;