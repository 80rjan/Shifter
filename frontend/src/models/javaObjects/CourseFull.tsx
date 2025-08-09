import type {Difficulty} from "../types/Difficulty.tsx";
import type {CourseContentFull} from "./CourseContentFull.tsx";

export interface CourseFull {
    id: number,
    imageUrl: string;
    color: string;
    titleShort: string;
    title: string;
    difficulty: Difficulty;
    durationMinutes: number;
    price: number;
    rating: number;
    ratingCount: number;
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    whatWillBeLearned: string[];
    skillsGained: string[];
    topicsCovered: string[];
    courseContents: CourseContentFull[];
}