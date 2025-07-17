import CourseCardSkeleton from "./CourseCardSkeleton.tsx";

function CoursesGridSkeleton() {
    return (
        <main className="flex-1 p-6">
            {/* Active Filters Pills Skeleton */}
            <div className="flex justify-between mb-6">
                <div className="flex flex-wrap gap-2">
                    {[...Array(4)].map((_, index) => (
                        <div key={index} className="h-8 bg-gray-300 rounded-full w-24 animate-pulse"></div>
                    ))}
                </div>
                <div className="h-8 bg-gray-300 rounded w-60 animate-pulse"></div>
            </div>


            {/* Course Grid Skeleton */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {[...Array(9)].map((_, index) => (
                    <CourseCardSkeleton key={index} />
                ))}
            </div>
        </main>
    );
}

export default CoursesGridSkeleton;