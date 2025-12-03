import type {CourseLectureTranslate} from "./CourseLectureTranslate.tsx";

export interface CourseContentTranslate {
    id: number;
    title: string;
    courseLectures: CourseLectureTranslate[];
}