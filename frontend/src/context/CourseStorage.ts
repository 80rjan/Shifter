import { create } from "zustand";
import type {CoursePreview} from "../models/javaObjects/course/CoursePreview.tsx";
import type {Enrollment} from "../models/javaObjects/Enrollment.tsx";


interface CourseStorage {
    enrollments: Enrollment[] | null;
    recommendedCourses: CoursePreview[] | null;
    allCourses: CoursePreview[] | null;

    setEnrollments: (enrollments: Enrollment[] | null) => void;
    setRecommendedCourses: (courses: CoursePreview[]) => void;
    setAllCourses: (courses: CoursePreview[]) => void;
}

export const useCourseStorage = create<CourseStorage>((set) => ({
    enrollments: null,
    recommendedCourses: null,
    allCourses: null,

    setEnrollments: (enrollments) => set({ enrollments: enrollments }),
    setRecommendedCourses: (courses) => set({ recommendedCourses: courses }),
    setAllCourses: (courses) => set({ allCourses: courses }),
}));
