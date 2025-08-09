import type {ContentType} from "../types/ContentType.tsx";

export interface CourseLecturePreview {
    title: string;
    description: string;
    durationMinutes: number;
    position: number;
    contentType: ContentType;
}