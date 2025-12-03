import {useEffect, useRef, useState} from "react";
import type {CourseLearn} from "../models/javaObjects/CourseLearn.tsx";
import type {CourseLectureLearn} from "../models/javaObjects/CourseLectureLearn.tsx";
import {fetchCourseCertificateApi, fetchEnrolledCourseLearnApi} from "../api/courseApi.ts";
import {fetchPresignedUrlApi} from "../api/s3Api.ts";
import {completeLectureApi, uncompleteLectureApi} from "../api/userCourseProgressApi.ts";
import type {Language} from "../models/types/Language.tsx";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";

export function useCourseLearn(courseId: number, accessToken: string) {
    const [course, setCourse] = useState<CourseLearn | null>(null);
    const [activeLecture, setActiveLecture] = useState<CourseLectureLearn | null>(null);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState<string>("");
    const [isDownloading, setIsDownloading] = useState(false);
    const [isLastLectureFinished, setIsLastLectureFinished] = useState(false);
    const [progressPercentage, setProgressPercentage] = useState(0);
    const spanRef = useRef<HTMLSpanElement>(null);
    const [spanWidth, setSpanWidth] = useState(0);
    const [isBtnHovered, setIsBtnHovered] = useState(false);
    const [showSideNav, setShowSideNav] = useState(true);
    const [showReviewModal, setShowReviewModal] = useState(false);
    const { loadUserProfile } = useUserContext();
    const { i18n } = useTranslation();

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
        fetchEnrolledCourseLearnApi(courseId, accessToken, i18n.language as Language)
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

        try {
            const url = await fetchPresignedUrlApi(accessToken, activeLecture.id, i18n.language as Language, expirySeconds);
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
            isFinished: course.isFinished,
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

        let hasFinishedCourse = course.isFinished;
        if (!course.isFinished) {
            hasFinishedCourse = updatedCourse.courseContents
                .flatMap(content => content.courseLectures)
                .flatMap(lecture => lecture.userCourseProgress)
                .filter(progress => !progress.completed)
                .length === 0;
        }

        setCourse({
            ...updatedCourse,
            isFinished: hasFinishedCourse
        });

        if (hasFinishedCourse) {
            loadUserProfile(accessToken)
                .catch(err => {
                    console.error("Error updating user skills after course completion: ", err);
                });
        }

        const apiCall = isComplete
            ? completeLectureApi(progressId, accessToken)
            : uncompleteLectureApi(progressId, accessToken);

        apiCall
            .catch(err => {
                console.error("Error updating lecture: ", err);
                setCourse(oldCourse);
            });
    };

    useEffect(() => {
        if (spanRef.current) {
            setSpanWidth(spanRef.current.offsetWidth);
        }
    }, [showSideNav]);

    useEffect(() => {
        window.scrollTo({
            top: 0,
            behavior: "smooth",
        });
    }, [activeLecture]);

    const markCourseAsRated = (newRating: number) => {
        if (!course) return;
        setCourse({...course, rating: newRating})
    }

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
        progressPercentage,
        markCourseAsRated,
        showReviewModal,
        setShowReviewModal,
        showSideNav,
        setShowSideNav,
        isBtnHovered,
        setIsBtnHovered,
        spanWidth,
        spanRef
    };
}
