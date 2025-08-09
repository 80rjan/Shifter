
export interface Enrollment {
    enrollmentStatus: "PENDING" | "ACTIVE" | "COMPLETED";
    percentCompleted: number;
    date: Date;
    courseId: number;
}