import type {CourseLectureEntity} from "./CourseLectureEntity.tsx";

export interface CourseContentEntity {
    title: string;
    position: number;
    courseLectures: CourseLectureEntity[];
}