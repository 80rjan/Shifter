import type {CourseLectureLearn} from "./CourseLectureLearn.tsx";

export interface CourseContentLearn {
    id: number;
    title: string;
    position: number;
    courseLectures: CourseLectureLearn[];
}