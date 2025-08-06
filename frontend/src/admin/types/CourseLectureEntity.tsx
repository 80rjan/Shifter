import type {ContentType} from "../../types/ContentType.tsx";

export interface CourseLectureEntity {
    title: string;
    description: string;
    durationMinutes: number;
    position: number;
    contentText: string;
    contentStoragePath: string;
    contentType: ContentType;
}