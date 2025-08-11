import type {ContentType} from "../types/ContentType.tsx";
import type {UserCourseProgress} from "./UserCourseProgress.tsx";

export interface CourseLectureFull {
    id: number;
    title: string;
    description: string;
    durationMinutes: number;
    position: number;
    contentText: string;
    contentFileName: string;
    contentType: ContentType;
    userCourseProgress: UserCourseProgress;
}