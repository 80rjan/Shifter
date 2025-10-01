function CourseCardEnrolledSkeleton() {
    return (
        <aside className="flex flex-col gap-2 items-center col-span-1 p-2 rounded-md">
            {/* IMAGE skeleton */}
            <div className="overflow-clip rounded-sm w-full">
                <div className="aspect-video bg-gray-300 animate-pulse"></div>
            </div>

            {/* INFO skeleton */}
            <div className="flex flex-col gap-2 text-left w-full">
                {/* Title skeleton */}
                <div className="h-5 bg-gray-300 rounded w-3/4 animate-pulse"></div>

                {/* Topics skeleton */}
                <div className="h-4 bg-gray-300 rounded w-full animate-pulse"></div>
            </div>

            {/* PROGRESS BAR skeleton */}
            <div className="flex flex-col gap-1 items-start w-full">
                <div className="w-full bg-gray-200 rounded-full h-0.5">
                    <div className="h-full bg-gray-300 rounded-full w-1/3 animate-pulse"></div>
                </div>
                <div className="h-3 bg-gray-300 rounded w-20 animate-pulse"></div>
            </div>
        </aside>
    );
}

export default CourseCardEnrolledSkeleton;