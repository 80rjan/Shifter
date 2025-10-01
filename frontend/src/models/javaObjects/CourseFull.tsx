import type {CourseContentFull} from "./CourseContentFull.tsx";

export interface CourseFull {
    id: number,
    titleShort: string;
    title: string;
    rating: number;
    isFinished: boolean;
    courseContents: CourseContentFull[];
}