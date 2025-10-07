import {useEffect, useState} from "react";
import type {CourseFull} from "../models/javaObjects/CourseFull.tsx";
import type {CourseLectureFull} from "../models/javaObjects/CourseLectureFull.tsx";
import {fetchCourseCertificateApi, fetchCourseFullApi} from "../api/courseApi.ts";
import {fetchPresignedUrlApi} from "../api/s3Api.ts";
import {completeLectureApi, uncompleteLectureApi} from "../api/userCourseProgressApi.ts";

export function useCourseLearn(courseId: number, accessToken: string) {
    const [course, setCourse] = useState<CourseFull | null>(null);
    const [activeLecture, setActiveLecture] = useState<CourseLectureFull | null>(null);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState<string>("");
    const [isDownloading, setIsDownloading] = useState(false);
    const [isLastLectureFinished, setIsLastLectureFinished] = useState(false);
    const [progressPercentage, setProgressPercentage] = useState(0);

    const courseFinishedPunchlines = [
        "🎓 Course Completed - The Future is Yours to Shape!",
        "🔥 Course Completed - Your Hard Work is Paying Off!",
        "🌟 Course Completed - You’re Unlocking Your True Potential!",
        "🚀 Course Completed - Your Journey to Success Continues!",
        "🏆 Course Completed - You’re One Step Closer to Your Dreams!",
        "💪 Course Completed - You’ve Proven Your Dedication!",
        "✨ Course Completed - Your Growth is Inspiring!",
        "🎉 Course Completed - You’re Ready for New Challenges!",
        "💡 Course Completed - Your Knowledge is Expanding!",
        "🎯 Course Completed - You’re Hitting Your Targets!",
        "🌍 Course Completed - You’re Making an Impact!",
        "💥 Course Completed - You’re Breaking Barriers!",
        "🚀 Course Completed - Your Journey is Just Beginning!",
        "💫 Course Completed - You’re Reaching New Heights!",
        "🌟 Course Completed - You’re a Star in the Making!",
        "🎓 Course Completed - Your Knowledge is Your Superpower!",
        "🎉 Course Completed - You’re a Force to be Reckoned With!",
        "💪 Course Completed - Your Strength is Unmatched!",
        "💡 Course Completed - Keep Growing, Keep Shining!",
        "🚀 Course Completed - The Sky is Not the Limit, It’s Just the Beginning!",
        "🌟 Course Completed - Your Potential is Limitless!",
        "🎉 Course Completed - Celebrate Your Success and Keep Moving Forward!",
        "🏆 Course Completed - You’ve Earned Your Place Among the Best!",
        "💪 Course Completed - Your Determination is Unstoppable!",
    ]

    useEffect(() => {
        if (!course?.courseContents) return;
        const completedLectures = course.courseContents.flatMap(content =>
            content.courseLectures.filter(lecture => lecture.userCourseProgress.completed)
        ) || [];

        const totalLectures = course.courseContents.flatMap(content => content.courseLectures) || [];

        setProgressPercentage(
            Math.round((completedLectures.length / (totalLectures.length || 1)) * 100)
        );
    }, [course?.courseContents]);

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

    const downloadCertificate = async () => {
        try {
            const response = await fetchCourseCertificateApi(Number(courseId || -1), accessToken || "");

            const pdfBlob = response.data;

            const disposition = response.headers['content-disposition'] || response.headers['Content-Disposition'];
            let filename = `Shifter_Certificate_${courseId}.pdf`; // Fallback filename

            if (disposition) {
                const filenameMatch = disposition.match(/filename="(.+?)"|filename\*=UTF-8''(.+)/i);
                if (filenameMatch) {
                    // Prioritize the filename from the second capture group (UTF-8 encoding)
                    // otherwise use the first group.
                    filename = filenameMatch[2] || filenameMatch[1];

                    // Clean up the filename if it was URL-encoded (common for UTF-8)
                    if (filename.includes('%')) {
                        filename = decodeURIComponent(filename);
                    }
                }
            }

            const url = window.URL.createObjectURL(pdfBlob);

            const a = document.createElement('a');
            a.href = url;
            a.download = filename;
            document.body.appendChild(a);
            a.click();

            window.URL.revokeObjectURL(url);
            a.remove();

        } catch (error) {
            console.error("Error during certificate download:", error);
            alert('Failed to download certificate. Check authentication or permissions.');
        }
    }

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
        setCourse,
        activeLecture,
        setActiveLecture,
        loading,
        videoUrl,
        isDownloading,
        setIsDownloading,
        updateLecture,
        triggerDownload,
        downloadCertificate,
        getPresignedUrl,
        isLastLectureFinished,
        setIsLastLectureFinished,
        courseFinishedPunchlines,
        progressPercentage
    };
}
