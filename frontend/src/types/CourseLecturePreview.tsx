
export interface CourseLecturePreview {
    title: string;
    description: string;
    durationMinutes: number;
    position: number;
    contentType: "VIDEO" | "TEXT" | "FILE" | "QUIZ";
}