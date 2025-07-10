import { create } from "zustand";
import type {CoursePreview} from "../types/CoursePreview.tsx";


interface CourseStorage {
    recommendedCourses: CoursePreview[] | null;
    favoriteCourses: number[] | null;
    allCourses: CoursePreview[] | null;
    topics: string[] | null;
    skills: string[] | null;

    setRecommendedCourses: (courses: CoursePreview[]) => void;
    setFavoriteCourses: (courses: number[] | null) => void;
    setAllCourses: (courses: CoursePreview[]) => void;
    setTopics: (topics: string[]) => void;
    setSkills: (skills: string[]) => void;
}

export const useCourseStorage = create<CourseStorage>((set) => ({
    recommendedCourses: null,
    favoriteCourses: null,
    allCourses: null,
    topics: null,
    skills: null,

    setRecommendedCourses: (courses) => set({ recommendedCourses: courses }),
    setFavoriteCourses: (courses) => set({ favoriteCourses: courses }),
    setAllCourses: (courses) => set({ allCourses: courses }),
    setTopics: (topics) => set({ topics }),
    setSkills: (skills) => set({ skills })
}));
