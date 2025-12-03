import type {CourseContentLearn} from "./CourseContentLearn.tsx";

export interface CourseLearn {
    id: number,
    titleShort: string;
    title: string;
    rating: number;
    isFinished: boolean;
    courseContents: CourseContentLearn[];
}