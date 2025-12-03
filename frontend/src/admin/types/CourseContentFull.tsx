import type {CourseLectureFull} from "./CourseLectureFull.tsx";

export interface CourseContentFull {
    id: number;
    title: string;
    position: number;
    courseLectures: CourseLectureFull[];
}