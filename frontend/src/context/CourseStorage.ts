import { create } from "zustand";
import type {Course} from "../types/Course.tsx";


interface CourseStorage {
    recommendedCourses: Course[] | null;
    allCourses: Course[] | null;
    topics: string[] | null;
    skills: string[] | null;

    setRecommendedCourses: (courses: Course[]) => void;
    setAllCourses: (courses: Course[]) => void;
    setTopics: (topics: string[]) => void;
    setSkills: (skills: string[]) => void;
}

export const useCourseStorage = create<CourseStorage>((set) => ({
    recommendedCourses: null,
    allCourses: null,
    topics: null,
    skills: null,

    setRecommendedCourses: (courses) => set({ recommendedCourses: courses }),
    setAllCourses: (courses) => set({ allCourses: courses }),
    setTopics: (topics) => set({ topics }),
    setSkills: (skills) => set({ skills })
}));
