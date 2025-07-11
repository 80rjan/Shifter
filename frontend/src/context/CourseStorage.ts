import { create } from "zustand";
import type {CoursePreview} from "../types/CoursePreview.tsx";
import type {Enrollment} from "../types/Enrollment.tsx";


interface CourseStorage {
    enrollments: Enrollment[] | null;
    recommendedCourses: CoursePreview[] | null;
    favoriteCourses: number[] | null;
    allCourses: CoursePreview[] | null;
    topics: string[] | null;
    skills: string[] | null;

    setEnrollments: (enrollments: Enrollment[] | null) => void;
    setRecommendedCourses: (courses: CoursePreview[]) => void;
    setFavoriteCourses: (courses: number[] | null) => void;
    setAllCourses: (courses: CoursePreview[]) => void;
    setTopics: (topics: string[]) => void;
    setSkills: (skills: string[]) => void;
}

export const useCourseStorage = create<CourseStorage>((set) => ({
    enrollments: null,
    recommendedCourses: null,
    favoriteCourses: null,
    allCourses: null,
    topics: null,
    skills: null,

    setEnrollments: (enrollments) => set({ enrollments: enrollments }),
    setRecommendedCourses: (courses) => set({ recommendedCourses: courses }),
    setFavoriteCourses: (courses) => set({ favoriteCourses: courses }),
    setAllCourses: (courses) => set({ allCourses: courses }),
    setTopics: (topics) => set({ topics }),
    setSkills: (skills) => set({ skills })
}));
