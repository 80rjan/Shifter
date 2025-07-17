
function CoursesFiltersSkeleton() {
    return (
        <aside className="flex flex-col gap-8 pl-8 pt-12 text-left sticky top-0 h-screen border-r-2 border-black/10">
            {/* Header */}
            <div className="h-8 bg-gray-300 rounded w-32 animate-pulse shrink-0 "></div>

            <div className="relative flex flex-col gap-12 pl-4 pr-2 pb-20 overflow-y-scroll scrollable">
                {/* Filter Sections - matching the 6 sections in CoursesFilters */}
                <FilterSelectSkeleton hasSearch={false} /> {/* Favorite Courses */}
                <FilterSelectSkeleton hasSearch={false} /> {/* Level */}
                <FilterSelectSkeleton hasSearch={true} />  {/* Topics */}
                <FilterSelectSkeleton hasSearch={true} />  {/* Skills */}
                <FilterSelectSkeleton hasSearch={false} /> {/* Duration */}
                <FilterSelectSkeleton hasSearch={false} /> {/* Price */}
            </div>

            {/* Gradient overlay */}
            <div className="pointer-events-none absolute bottom-0 left-0 w-full h-24 bg-gradient-to-t from-white to-transparent"/>
        </aside>
    );
}

function FilterSelectSkeleton({hasSearch}: {hasSearch: boolean}) {
    return (
        <section className="flex flex-col gap-1 text-md">
            {/* Header */}
            <div className="h-6 bg-gray-300 rounded w-24 mb-4 animate-pulse"></div>

            <form className="flex flex-col gap-2">
                {/* Search input skeleton (only for Topics and Skills) */}
                {hasSearch && (
                    <div className="h-10 bg-gray-300 rounded-sm mb-2 animate-pulse"></div>
                )}

                {/* Filter options - 4 items */}
                {[...Array(4)].map((_, index) => (
                    <label key={index} className="flex items-center text-black whitespace-nowrap cursor-pointer w-fit">
                        <div className="h-7 w-7 bg-gray-300 rounded mr-2 animate-pulse"></div>
                        <div className="h-4 bg-gray-300 rounded w-40 animate-pulse"></div>
                    </label>
                ))}
            </form>

            {/* Show more button skeleton (only for sections with search) */}
            {hasSearch && (
                <div className="h-4 bg-gray-300 rounded w-16 animate-pulse"></div>
            )}
        </section>
    );
}

export default CoursesFiltersSkeleton;