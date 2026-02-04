import type {Difficulty} from "../../types/Difficulty.tsx";
import type {Language} from "../../types/Language.tsx";

export interface CoursePreview {
    id: number,
    imageUrl: string;
    color: string;
    titleShort: string;
    title: string;
    difficulty: Difficulty;
    durationMinutes: number;
    price: number;
    skillsGained: string[];
    topicsCovered: string[];
    courseContentCount: number;
    courseLectureCount: number;
    averageRating: number;
    urlSlug: string;
    translatedLanguages: Language[];
}

