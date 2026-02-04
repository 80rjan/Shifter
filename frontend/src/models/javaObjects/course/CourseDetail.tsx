import type {CoursePreview} from "./CoursePreview.tsx";
import type {CourseContentPreview} from "../CourseContentPreview.tsx";

export interface CourseDetail extends CoursePreview {
    descriptionShort: string;
    description: string;
    descriptionLong: string;
    whatWillBeLearned: string[];
    courseContents: CourseContentPreview[];
}