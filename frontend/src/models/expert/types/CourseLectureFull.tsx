import type {ContentType} from "../../types/ContentType.tsx";

export interface CourseLectureFull {
    id: number;
    title: string;
    description: string;
    durationMinutes: number;
    position: number;
    contentText: string;
    contentFileName: string;
    contentType: ContentType;
}