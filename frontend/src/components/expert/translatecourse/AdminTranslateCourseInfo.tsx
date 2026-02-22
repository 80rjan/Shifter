import AdminTranslateCourseInput from "./input/AdminTranslateCourseInput.tsx";
import AdminAddCourseInputTextArea from "./input/AdminTranslateCourseInputTextArea.tsx";
import type {CourseFull} from "../../../models/expert/types/CourseFull.tsx";
import type {JSX, SetStateAction} from "react";
import type {CourseTranslate} from "../../../models/expert/types/CourseTranslate.tsx";
import AdminTranslateCourseDisabledInput from "./input/AdminTranslateCourseDisabledInput.tsx";
import {capitalize} from "../../../utils/capitalize.ts";
import {parseStringToTagReq} from "../../../utils/parseStringToTagReq.ts";
import AdminTranslateCourseInputPills from "./input/AdminTranslateCourseInputPills.tsx";

function AdminTranslateCourseInfo({initCourse, course, setCourse, courseCard}: {
    initCourse: CourseFull;
    course: CourseTranslate;
    setCourse: (action: SetStateAction<CourseTranslate>) => void;
    courseCard: JSX.Element;
}) {

    return (
        <section className="flex flex-col gap-12">
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

                    <AdminTranslateCourseInput
                        label={"Short Title"}
                        value={course.titleShort}
                        onChange={e => setCourse(prev => ({
                            ...prev,
                            titleShort: e.target.value
                        }))}
                        type={"text"}
                        width={"full"}
                        translateFrom={initCourse.titleShort}
                    />

                    <AdminTranslateCourseInput
                        label={"Long Title"}
                        value={course.title}
                        onChange={e => setCourse(prev => ({
                            ...prev,
                            title: e.target.value
                        }))}
                        type={"text"}
                        width={"full"}
                        translateFrom={initCourse.title}
                    />

                    <AdminTranslateCourseInputPills
                        label={"Topics Covered"}
                        translateFromList={initCourse.topicsCovered}
                        onChange={(e, index) => {
                            const newTopicId = parseStringToTagReq(initCourse.topicsCovered[index]).id; // ✅ Extract real tag ID
                            const newValue = e.target.value;

                            setCourse((prev: CourseTranslate) => {
                                if (newValue === "") {
                                    return { ...prev, topicsCovered: prev.topicsCovered.filter(topic => topic.id !== newTopicId) };
                                }

                                const existingIndex = prev.topicsCovered.findIndex(topic => topic.id === newTopicId);

                                if (existingIndex >= 0) {
                                    const updatedTopics = [...prev.topicsCovered];
                                    updatedTopics[existingIndex] = { ...updatedTopics[existingIndex], value: newValue };
                                    return { ...prev, topicsCovered: updatedTopics };
                                }

                                return {
                                    ...prev,
                                    topicsCovered: [...prev.topicsCovered, { id: newTopicId, value: newValue }]
                                };
                            })
                        }}
                        isTagEncoded={true} // ✅ Change from false to true
                        display={"grid"}
                    />

                    <div className="flex justify-between w-full">
                        <div className="flex gap-4 w-fit items-end">
                            <AdminTranslateCourseDisabledInput
                                label={"Color (in hex)"}
                                value={initCourse.color}
                                width={"fit"}
                            />
                            <div
                                style={{backgroundColor: initCourse.color}}
                                className="ml-auto h-2/3 aspect-square rounded-sm"
                            />
                        </div>
                        <AdminTranslateCourseDisabledInput
                            label={"Price (in euros)"}
                            value={initCourse.price}
                            width={"fit"}
                        />
                        <AdminTranslateCourseDisabledInput
                            label={"Difficulty"}
                            value={capitalize(initCourse.difficulty)}
                            width={"fit"}
                        />
                    </div>

                    <AdminTranslateCourseInputPills
                        label={"Skills Gained"}
                        translateFromList={initCourse.skillsGained}
                        onChange={(e, index) => {
                            const newSkillId = parseStringToTagReq(initCourse.skillsGained[index]).id;
                            const newValue = e.target.value;

                            setCourse((prev: CourseTranslate) => {
                                if (newValue === "") {
                                    return { ...prev, skillsGained: prev.skillsGained.filter(skill => skill.id !== newSkillId) };
                                }

                                const existingIndex = prev.skillsGained.findIndex(skill => skill.id === newSkillId);

                                if (existingIndex >= 0) {
                                    const updatedSkills = [...prev.skillsGained];
                                    updatedSkills[existingIndex] = { ...updatedSkills[existingIndex], value: newValue };
                                    return { ...prev, skillsGained: updatedSkills };
                                }

                                return {
                                    ...prev,
                                    skillsGained: [...prev.skillsGained, { id: newSkillId, value: newValue }]
                                };
                            })
                        }}
                        isTagEncoded={true}
                        display={"grid"}
                    />

                </div>
            </div>

            <div className="flex flex-col gap-6">
                <AdminTranslateCourseInputPills
                    label={"What will be learned (1-2 sentences)"}
                    translateFromList={initCourse.whatWillBeLearned}
                    onChange={(e, index) => setCourse(prev => {
                        const newValue = e.target.value;

                        // Create a copy with enough slots
                        const newLearnedArray = [...prev.whatWillBeLearned];

                        // Ensure array is large enough
                        while (newLearnedArray.length <= index) {
                            newLearnedArray.push("");
                        }

                        if (newValue === "") {
                            newLearnedArray[index] = "";
                        } else {
                            newLearnedArray[index] = newValue;
                        }

                        // Filter out empty strings for the final result
                        return {
                            ...prev,
                            whatWillBeLearned: newLearnedArray
                        };
                    })}
                    isTagEncoded={false}
                    display={"flex"}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Short (1-2 sentences)"}
                    name={"descriptionShort"}
                    value={course.descriptionShort}
                    placeholder={"Enter a short description of the course"}
                    rows={1}
                    onChange={e => setCourse(prev => ({
                        ...prev,
                        descriptionShort: e.target.value
                    }))}
                    translateFrom={initCourse.descriptionShort}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Medium (2-3 sentences)"}
                    name={"description"}
                    value={course.description}
                    placeholder={"Enter a description of the course"}
                    rows={3}
                    onChange={e => setCourse(prev => ({
                        ...prev,
                        description: e.target.value
                    }))}
                    translateFrom={initCourse.description}
                />
                <AdminAddCourseInputTextArea
                    label={"Description Long"}
                    name={"descriptionLong"}
                    value={course.descriptionLong}
                    placeholder={"Enter a detailed description of the course"}
                    rows={6}
                    onChange={e => setCourse(prev => ({
                        ...prev,
                        descriptionLong: e.target.value
                    }))}
                    translateFrom={initCourse.descriptionLong}
                />
            </div>
        </section>
    )
}

export default AdminTranslateCourseInfo;