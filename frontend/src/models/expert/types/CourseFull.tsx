import type {Difficulty} from "../../types/Difficulty.tsx";
import type {CourseContentFull} from "./CourseContentFull.tsx";
import type {Language} from "../../types/Language.tsx";

export interface CourseFull {
    id: number;
    imageUrl: string;
    color: string;
    difficulty: Difficulty;
    durationMinutes: number;
    price: number;
    language: Language;
    titleShort: string;
    title: string;
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    whatWillBeLearned: string[];
    skillsGained: string[];
    topicsCovered: string[];
    courseContents: CourseContentFull[];
}