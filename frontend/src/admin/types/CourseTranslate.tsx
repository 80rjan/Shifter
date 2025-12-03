import type {Language} from "../../models/types/Language.tsx";
import type {AttributeReq} from "./AttributeReq.tsx";
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
    skillsGained: AttributeReq[];
    topicsCovered: AttributeReq[];
    courseContents: CourseContentTranslate[];
}