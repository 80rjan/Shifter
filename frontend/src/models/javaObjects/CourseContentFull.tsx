import type {CourseLectureFull} from "./CourseLectureFull.tsx";

export interface CourseContentFull {
    title: string;
    position: number;
    courseLectures: CourseLectureFull[];
}