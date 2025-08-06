import type {Difficulty} from "../../types/Difficulty.tsx";
import type {CourseContentEntity} from "./CourseContentEntity.tsx";

export interface CourseEntity {
    imageUrl: string;
    color: string;
    titleShort: string;
    title: string;
    difficulty: Difficulty;
    durationMinutes: number;
    price: number;
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    whatWillBeLearned: string[];
    skillsGained: string[];
    topicsCovered: string[];
    courseContents: CourseContentEntity[];
}