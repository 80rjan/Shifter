
function PersonalizationSliderSkeleton() {
    return (
        <div className="w-full flex flex-col justify-center gap-4 px-6 py-1 items-start">
            <div className="flex justify-between items-end w-full flex-wrap gap-2">
                {/* Label skeleton */}
                <div className="h-8 bg-gray-300 rounded-md w-40 animate-pulse"></div>

                {/* Search input skeleton */}
                <div className="h-6 bg-gray-300 rounded w-32 animate-pulse"></div>
            </div>

            {/* Options container skeleton */}
            <div className="relative flex gap-2 flex-wrap w-full max-h-[30vh] items-center overflow-y-auto">
                {/* Generate skeleton option buttons */}
                {Array.from({ length: 8 }).map((_, index) => (
                    <div
                        key={index}
                        className="h-8 bg-gray-300 rounded-md animate-pulse"
                        style={{ width: `${Math.random() * 60 + 80}px` }} // Random widths for variety
                    ></div>
                ))}

                {/* Gradient overlay skeleton */}
                <div className="pointer-events-none sticky bottom-0 left-0 w-full h-4 bg-gradient-to-t from-white to-transparent"></div>
            </div>
        </div>
    );
}

export default PersonalizationSliderSkeleton;