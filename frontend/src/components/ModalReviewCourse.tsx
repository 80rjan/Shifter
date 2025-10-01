import ReactDOM from "react-dom";
import {X} from "lucide-react";
import {useEffect, useState} from "react";
import StarFilled from "../assets/icons/StarFilled";
import StarOutline from "../assets/icons/StarOutline";
import {getReviewApi, updateReviewApi, writeReviewApi} from "../api/reviewApi.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import ModalReviewCourseSkeleton from "./skeletons/ModalReviewCourseSkeleton.tsx";

function ModalReviewCourse({
                               courseId,
                               closeModal,
                               markCourseAsRated,
                               isUpdate
                           }: {
    courseId: number;
    closeModal: () => void;
    markCourseAsRated: (rating: number) => void;
    isUpdate: boolean;
}) {
    const {accessToken} = useAuthContext();
    const [review, setReview] = useState({
        rating: 0,
        comment: "",
    });
    const [hovered, setHovered] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [fetchingReview, setFetchingReview] = useState<boolean>(false);
    const [error, setError] = useState<string>("");

    useEffect(() => {
        if (!isUpdate)
            return;

        setFetchingReview(true);
        getReviewApi(accessToken || "", courseId)
            .then(review => {
                setReview({rating: review.rating, comment: review.comment});
            })
            .catch((error) => {
                console.error("Error fetching review:", error);
                setError("Failed to load your review. Please try again later.");
            })
            .finally(() => {
                setFetchingReview(false);
            });
    }, []);

    const starTexts = [
        "Select Rating",                    // default placeholder
        "Poor – did not meet expectations", // 1 star
        "Fair – could be better",           // 2 stars
        "Average – okay, but room to improve", // 3 stars
        "Good – I learned valuable things",    // 4 stars
        "Excellent – exceeded expectations!"   // 5 stars
    ];

    const writeReview = () => {
        if (review.rating === 0) {
            setError("Please select a rating before submitting.");
            return;
        }

        setLoading(true);
        const apiCall = isUpdate ? updateReviewApi : writeReviewApi;
        apiCall(
            accessToken || "",
            courseId,
            review.rating,
            review.comment
        )
            .then(() => {
                markCourseAsRated(review.rating);
                closeModal();
            })
            .catch((error) => {
                console.error("Error writing review:", error);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    if (fetchingReview)
        return <ModalReviewCourseSkeleton closeModal={closeModal} />

    return ReactDOM.createPortal(
        <>
            {/* Overlay */}
            <div className="fixed inset-0 bg-gray/60 backdrop-blur-sm z-[1000]"/>

            {/* Modal */}
            <section
                className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2
                   bg-white shadow-lg py-4 px-6 z-[1000] max-w-1/2 min-w-1/3
                   max-h-5/6 rounded-lg flex flex-col items-center gap-6
                   text-black-text"
            >
                {/* Close button */}
                <button
                    onClick={closeModal}
                    className="ml-auto flex justify-end cursor-pointer p-2 hover:bg-shifter/20 rounded-sm"
                >
                    <X size={24} strokeWidth={1.5}/>
                </button>

                <h2 className="text-2xl font-bold ">
                    How Was Your Learning Experience?
                </h2>

                {/* STARS */}
                <div className="flex flex-col gap-2 items-center">
                    <div className="flex gap-4"
                         onMouseLeave={() => setHovered(0)}
                    >
                        {[1, 2, 3, 4, 5].map((star) => {
                            const filled = hovered ? star <= hovered : star <= review.rating;
                            const StarIcon = filled ? StarFilled : StarOutline;

                            return (
                                <div
                                    key={star}
                                    onMouseEnter={() => setHovered(star)}
                                    onClick={() =>
                                        setReview({...review, rating: star})
                                    }
                                    className={`cursor-pointer transition-colors duration-150`}
                                >
                                    <StarIcon className="w-8 text-gold"/>
                                </div>
                            );
                        })}
                    </div>

                    <span className="text-md">
                        {hovered ? starTexts[hovered] : starTexts[review.rating]}
                    </span>
                </div>

                <div className="w-full flex flex-col gap-1">
                    <textarea
                        className="focus:outline-none focus:border-shifter resize-none
                        w-full border border-black/40 rounded-sm px-4 py-2"
                        onChange={(e) =>
                            setReview({...review, comment: e.target.value})
                        }
                        value={review.comment}
                        placeholder="Share your thoughts about this course..."
                        rows={4}
                    />
                    <span className="font-normal text-black/60 text-xs"><sup>*</sup>optional</span>
                </div>

                <div className="flex gap-6 mr-auto">
                    <button
                        onClick={writeReview}
                        type="button"
                        className="hover:shadow-md hover:shadow-shifter/40 shadow-sm shadow-shifter/20 transition-all duration-300 ease-in-out
                    border-2 border-white/40 px-12 py-2 bg-shifter text-white cursor-pointer rounded-sm w-fit"
                    >
                        {loading ? "Submitting..." : "Submit Review"}
                    </button>
                    {loading && <div className="loader h-full"/>}
                </div>

                {error && (
                    <p className="text-md text-red">{error}</p>
                )}
            </section>
        </>,
        document.getElementById("portal")!
    );
}

export default ModalReviewCourse;