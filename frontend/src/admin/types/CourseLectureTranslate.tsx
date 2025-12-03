import type {ContentType} from "../../models/types/ContentType.tsx";

export interface CourseLectureTranslate {
    id: number;
    title: string;
    description: string;
    durationMinutes: number;
    contentText: string;
    contentFileName: string;
    contentType: ContentType;
}