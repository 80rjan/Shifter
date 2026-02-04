import type {CoursePreview} from "./CoursePreview.tsx";

export interface CoursePreviewEnrolled extends CoursePreview {
    lecturesFinishedCount: number;
    rating: number;
    isFinished: boolean;
}

