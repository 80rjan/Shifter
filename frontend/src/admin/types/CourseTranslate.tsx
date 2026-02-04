import type {Language} from "../../models/types/Language.tsx";
import type {TagReq} from "./TagReq.tsx";
import type {CourseContentTranslate} from "./CourseContentTranslate.tsx";

export interface CourseTranslate {
    id: number;
    language: Language;
    titleShort: string;
    title: string;
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    whatWillBeLearned: string[];
    skillsGained: TagReq[];
    topicsCovered: TagReq[];
    courseContents: CourseContentTranslate[];
}