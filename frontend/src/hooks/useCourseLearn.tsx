import {useEffect, useState} from "react";
import type {CourseFull} from "../models/javaObjects/CourseFull.tsx";
import type {CourseLectureFull} from "../models/javaObjects/CourseLectureFull.tsx";
import {fetchCourseFullApi} from "../api/courseApi.ts";
import {fetchPresignedUrlApi} from "../api/s3Api.ts";
import {completeLectureApi, uncompleteLectureApi} from "../api/userCourseProgressApi.ts";

export function useCourseLearn(courseId: number, accessToken: string) {
    const [course, setCourse] = useState<CourseFull | null>(null);
    const [activeLecture, setActiveLecture] = useState<CourseLectureFull | null>(null);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState<string>("");
    const [isDownloading, setIsDownloading] = useState(false);

    useEffect(() => {
        setLoading(true);
        fetchCourseFullApi(courseId, accessToken)
            .then(resCourse => {
                setCourse(resCourse);
                const firstUncompletedLecture = resCourse?.courseContents
                    ?.flatMap(content => content.courseLectures)
                    ?.find(lecture => !lecture.userCourseProgress.completed);
                setActiveLecture(firstUncompletedLecture || resCourse?.courseContents[0]?.courseLectures[0] || null);
            })
            .catch(err => console.error("Error fetching full course api: ", err))
            .finally(() => setLoading(false));
    }, [courseId, accessToken]);

    useEffect(() => {
        console.log(activeLecture)
    }, [activeLecture])

    useEffect(() => {
        if (activeLecture?.contentType === "VIDEO") {
            getPresignedUrl(60 * 60) // 1 hour
                .then(url => setVideoUrl(url || ""))
                .catch(err => console.error("Error fetching video URL: ", err));
        } else {
            setVideoUrl("");
        }
    }, [activeLecture, accessToken, courseId]);

    const getPresignedUrl = async (expirySeconds: number): Promise<string | undefined> => {
        if (!activeLecture) return undefined;
        const encodedFileName = encodeURIComponent(activeLecture.contentFileName);
        try {
            const url = await fetchPresignedUrlApi(accessToken, courseId, activeLecture.id, encodedFileName, expirySeconds);
            return url;
        } catch (err) {
            console.error("Error fetching presigned URL: ", err);
            return undefined;
        }
    };

    const triggerDownload = async () => {
        if (!activeLecture) return;

        setIsDownloading(true);
        try {
            const url = await getPresignedUrl(60 * 5); // 5 minutes expiry
            if (!url) {
                console.error("No presigned URL available");
                setIsDownloading(false);
                return;
            }

            // Create temporary link element to download the file
            const a = document.createElement("a");
            a.href = url;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);

            updateLecture(activeLecture.userCourseProgress.id, true);
        } catch (error) {
            console.error("Download failed:", error);
        } finally {
            setIsDownloading(false);
        }
    };

    const updateLecture = (progressId: number, isComplete: boolean) => {
        if (!course) return;

        const oldCourse = structuredClone(course);
        const updatedCourse = {
            ...course,
            courseContents: course.courseContents.map(content => ({
                ...content,
                courseLectures: content.courseLectures.map(lecture =>
                    lecture.userCourseProgress.id === progressId
                        ? {
                            ...lecture,
                            userCourseProgress: {
                                ...lecture.userCourseProgress,
                                completed: isComplete,
                                completedAt: isComplete ? new Date() : null,
                            },
                        }
                        : lecture
                ),
            })),
        };

        setCourse(updatedCourse);

        const apiCall = isComplete
            ? completeLectureApi(progressId, accessToken)
            : uncompleteLectureApi(progressId, accessToken);

        apiCall
            .catch(err => {
                console.error("Error updating lecture: ", err);
                setCourse(oldCourse);
            });
    };

    return {
        course,
        activeLecture,
        setActiveLecture,
        loading,
        videoUrl,
        isDownloading,
        setIsDownloading,
        updateLecture,
        triggerDownload,
        getPresignedUrl,
    };
}
