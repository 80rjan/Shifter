import ReactDOM from "react-dom";
import {X} from "lucide-react";
import StarOutline from "../../assets/icons/StarOutline";

function ModalReviewCourseSkeleton({closeModal}: {
    closeModal: () => void;
}) {
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

                {/* Title skeleton */}
                <div className="h-8 bg-gray-300 rounded w-80 animate-pulse"></div>

                {/* Stars skeleton */}
                <div className="flex flex-col gap-2 items-center">
                    <div className="flex gap-4">
                        {[1, 2, 3, 4, 5].map((star) => (
                            <StarOutline
                                key={star}
                                className="w-8 h-8 text-gray-300 rounded animate-pulse"
                            />
                        ))}
                    </div>
                    {/* Star text skeleton */}
                    <div className="h-6 bg-gray-300 rounded w-48 animate-pulse"></div>
                </div>

                {/* Textarea skeleton */}
                <div className="w-full flex flex-col gap-1">
                    <div className="w-full h-24 bg-gray-300 rounded-sm animate-pulse"></div>
                    <div className="h-3 bg-gray-300 rounded w-16 animate-pulse"></div>
                </div>

                {/* Submit button skeleton */}
                <div className="flex gap-6 mr-auto">
                    <div className="h-10 bg-gray-300 rounded-sm w-32 animate-pulse"></div>
                </div>
            </section>
        </>,
        document.getElementById("portal")!
    );
}

export default ModalReviewCourseSkeleton;