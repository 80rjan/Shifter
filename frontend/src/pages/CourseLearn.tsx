import {useEffect, useState} from "react";
import type {CourseFull} from "../models/javaObjects/CourseFull.tsx";
import {useParams} from "react-router-dom";
import {fetchCourseFullApi} from "../api/courseApi.ts";
import {useAuthContext} from "../context/AuthContext.tsx";
import CourseContentSideNav from "../components/learn/CourseContentSideNav.tsx";
import type {CourseLectureFull} from "../models/javaObjects/CourseLectureFull.tsx";
import {fetchPresignedUrlApi} from "../api/s3Api.ts";
import CourseLearnSkeleton from "../components/skeletons/CourseLearnSkeleton.tsx";

function CourseLearn() {
    const {courseId} = useParams<{ courseId: string }>();
    const [course, setCourse] = useState<CourseFull | null>(null);
    const [activeLecture, setActiveLecture] = useState<CourseLectureFull>({
        id: -1,
        contentFileName: "",
        contentText: "",
        contentType: "TEXT",
        description: "",
        durationMinutes: 0,
        position: 0,
        title: ""
    });
    const [loading, setLoading] = useState(true);
    const [isDownloading, setIsDownloading] = useState(false);
    const [videoUrl, setVideoUrl] = useState<string>("");
    const {accessToken} = useAuthContext();

    useEffect(() => {
        setLoading(true);
        fetchCourseFullApi(Number(courseId), accessToken || "")
            .then(resCourse => {
                setCourse(resCourse)
                setActiveLecture(resCourse?.courseContents[0]?.courseLectures[0]);
            })
            .catch(err => {
                console.error("Error fetching full course api: ", err)
            })
            .finally(() => {
                setLoading(false);
            });
    }, [courseId, accessToken]);

    useEffect(() => {
        if (activeLecture.contentType === "VIDEO") {
            getPresignedUrl(60 * 60)
                .then(url => {
                    setVideoUrl(url || "")
                })
                .catch(err => {
                    console.error("Error fetching video URL: ", err);
                });
        } else {
            setVideoUrl("");
        }
    }, [activeLecture]);


    const getPresignedUrl = async (expirySeconds: number): Promise<string | undefined> => {
        const encodedFileName = encodeURIComponent(activeLecture.contentFileName);
        try {
            const url = await fetchPresignedUrlApi(accessToken || "", Number(courseId) || -1, Number(activeLecture.id), encodedFileName, expirySeconds);
            console.log("Presigned URL: ", url);
            return url;
        } catch (err) {
            console.error("Error fetching presigned URL: ", err);
        }
    };


    const triggerDownload = (url: string, filename: string) => {
        const a = document.createElement("a");
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };

    if (loading) {
        return (
            <CourseLearnSkeleton />
        );
    }

    return (
        <main className="flex ">
            <div className="flex flex-col">
                {
                    activeLecture.contentType === "VIDEO" && (
                        <video
                            src={videoUrl}
                            controls
                            controlsList="nodownload"
                            preload="metadata"
                        />
                    )
                }
                <div
                    className="flex flex-col gap-4 flex-grow py-vertical-md px-horizontal-sm text-left text-black-text">
                    <h1 className="text-4xl font-semibold">{activeLecture?.title}</h1>
                    <p className="text-lg leading-loose">{activeLecture?.contentText}</p>
                    {
                        (activeLecture?.contentType === "FILE" || activeLecture.contentType === "TOOL") && (
                            <div className="flex justify-between w-full gap-20 items-center py-12">
                                <p className="text-lg font-medium">
                                    {activeLecture.contentFileName}
                                </p>
                                <button
                                    disabled={isDownloading}
                                    onClick={async () => {
                                        setIsDownloading(true);
                                        const url = await getPresignedUrl(60 * 5)
                                        if (url) {
                                            triggerDownload(url, activeLecture.contentFileName);
                                        }
                                        setIsDownloading(false);
                                    }}
                                    className={`disabled:cursor-not-allowed disabled:opacity-40 hover:shadow-lg transition-all duration-300 ease-in-out cursor-pointer
                                    bg-shifter text-white text-lg px-8 py-2 rounded-md shadow-md border-2 border-white/40 shadow-shifter/40`}
                                >
                                    {
                                        isDownloading ? "Downloading..." : "Download File"
                                    }
                                </button>
                            </div>
                        )
                    }
                </div>
            </div>
            <CourseContentSideNav
                setActiveLecture={setActiveLecture}
                courseContents={course?.courseContents}/>
        </main>
    )
}

export default CourseLearn;