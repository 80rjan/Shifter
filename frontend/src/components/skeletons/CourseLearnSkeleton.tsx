
function CourseLearnSkeleton() {
    return (
        <main className="flex ">
            <div className="flex flex-col w-full">
                {/* Video player skeleton */}
                <div className="w-full h-96 bg-gray-300 animate-pulse"></div>

                <div className="flex flex-col gap-4 flex-grow py-vertical px-horizontal text-left text-black-text">
                    {/* Title skeleton */}
                    <div className="h-10 bg-gray-300 rounded w-3/4 animate-pulse"></div>

                    {/* Content text skeleton */}
                    <div className="flex flex-col gap-3">
                        <div className="h-6 bg-gray-300 rounded w-full animate-pulse"></div>
                        <div className="h-6 bg-gray-300 rounded w-98/100 animate-pulse"></div>
                        <div className="h-6 bg-gray-300 rounded w-full animate-pulse"></div>
                        <div className="h-6 bg-gray-300 rounded w-95/100 animate-pulse"></div>
                        <div className="h-6 bg-gray-300 rounded w-4/5 animate-pulse"></div>
                    </div>

                    {/* File download section skeleton */}
                    <div className="flex justify-between w-full gap-20 items-center py-12">
                        <div className="h-6 bg-gray-300 rounded w-120 animate-pulse"></div>
                        <div className="h-12 bg-gray-300 rounded-md w-36 animate-pulse"></div>
                    </div>
                </div>
            </div>

            {/* CourseContentSideNav skeleton */}
            <div className="w-28/100 flex flex-col gap-4 p-6 bg-gray-50">
                {/* Side nav header skeleton */}
                <div className="h-8 bg-gray-300 rounded w-3/4 animate-pulse"></div>

                {/* Course content sections skeleton */}
                {[...Array(4)].map((_, sectionIndex) => (
                    <div key={sectionIndex} className="flex flex-col gap-2">
                        {/* Section title skeleton */}
                        <div className="h-6 bg-gray-300 rounded w-2/3 animate-pulse"></div>

                        {/* Lectures skeleton */}
                        {[...Array(4)].map((_, lectureIndex) => (
                            <div key={lectureIndex} className="flex items-center gap-3 p-2">
                                <div className="w-4 h-4 bg-gray-300 rounded-full animate-pulse"></div>
                                <div className="h-5 bg-gray-300 rounded w-full animate-pulse"></div>
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </main>
    );
}

export default CourseLearnSkeleton;