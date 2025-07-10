import type {CourseLecturePreview} from "./CourseLecturePreview.tsx";

export interface CourseContentPreview {
    title: string;
    position: number;
    courseLectures: CourseLecturePreview[];
}