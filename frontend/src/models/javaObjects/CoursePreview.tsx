import type {Difficulty} from "../types/Difficulty.tsx";

export interface CoursePreview {
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
    skillsGained: string[];
    topicsCovered: string[];
    courseContentCount: number;
}

