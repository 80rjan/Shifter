import ReactDom from "react-dom";
import {X} from "lucide-react";
import {useEffect} from "react";

function ProfileModalAddSkillsInterestsSkeleton({closeModal}: {
    closeModal: () => void;
}) {
    useEffect(() => {
        // Disable scroll
        document.body.classList.add("overflow-hidden");

        // Cleanup: re-enable scroll when modal unmounts
        return () => {
            document.body.classList.remove("overflow-hidden");
        };
    }, []);

    return ReactDom.createPortal(
        <>
            {/*OVERFLOW*/}
            <div className="fixed top-0 bottom-0 left-0 right-0 bg-black/70 z-1000"/>

            <section className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 bg-white shadow-lg py-4 px-6 z-1000
                     max-w-2/3 max-h-5/6 rounded-lg flex flex-col gap-4">
                <button
                    onClick={closeModal}
                    className="hover:bg-black/10 transition-all duration-300 ease-out ml-auto px-2 w-fit rounded-sm cursor-pointer">
                    <X size={32} />
                </button>

                <div className="flex justify-between w-full flex-wrap gap-2">
                    {/* Label skeleton */}
                    <div className="h-7 bg-gray-300 rounded w-32 animate-pulse"></div>

                    {/* Search input skeleton */}
                    <div className="h-8 bg-gray-300 rounded-md w-48 animate-pulse"></div>
                </div>

                <div className="relative custom-scrollbar flex gap-2 flex-wrap w-full items-center overflow-y-auto">
                    {/* Option buttons skeleton */}
                    {[...Array(28)].map((_, index) => (
                        <div
                            key={index}
                            className="h-8 bg-gray-300 rounded-md animate-pulse"
                            style={{
                                width: `${Math.floor(Math.random() * 60) + 80}px`
                            }}
                        >
                        </div>
                    ))}
                </div>
            </section>
        </>,
        document.getElementById("portal")!
    )
}

export default ProfileModalAddSkillsInterestsSkeleton;