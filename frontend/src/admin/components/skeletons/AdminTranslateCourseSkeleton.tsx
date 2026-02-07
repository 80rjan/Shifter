function AdminTranslateCourseSkeleton() {
    return (
        <main className="flex flex-col gap-12 px-horizontal py-vertical">
            {/* AdminTranslateCourseInfo skeleton */}
            <section className="flex flex-col gap-8">
                {/* Course card and form fields side by side */}
                <div className="flex gap-8">
                    {/* Course card skeleton - left side */}
                    <div className="flex-shrink-0 w-1/3">
                        <div className="bg-gray-300 animate-pulse rounded-md aspect-video w-full"></div>
                        <div className="mt-4 space-y-2">
                            <div className="h-5 bg-gray-300 rounded w-3/4 animate-pulse"></div>
                            <div className="h-4 bg-gray-300 rounded w-full animate-pulse"></div>
                            <div className="h-4 bg-gray-300 rounded w-2/3 animate-pulse"></div>
                        </div>
                    </div>

                    {/* Form fields skeleton - right side */}
                    <div className="flex-1 flex flex-col gap-4">
                        {/* Title field */}
                        <div className="flex flex-col gap-2">
                            <div className="h-5 bg-gray-300 rounded w-16 animate-pulse"></div>
                            <div className="h-10 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                        </div>

                        {/* Short description field */}
                        <div className="flex flex-col gap-2">
                            <div className="h-5 bg-gray-300 rounded w-32 animate-pulse"></div>
                            <div className="h-10 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                        </div>

                        {/* Additional fields in grid */}
                        <div className="grid grid-cols-2 gap-4">
                            {
                                Array.from({length: 6}).map((_, i) => (
                                    <div key={i}
                                        className="flex flex-col gap-2">
                                        <div className="h-5 bg-gray-300 rounded w-24 animate-pulse"></div>
                                        <div className="h-10 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                </div>

                {/* Full width description field below */}
                <div className="flex flex-col gap-2">
                    <div className="h-5 bg-gray-300 rounded w-20 animate-pulse"></div>
                    <div className="h-24 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                </div>
            </section>

            {/* AdminTranslateCourseContent skeleton */}
            <section className="flex flex-col gap-6">
                {/* Section header */}
                <div className="h-6 bg-gray-300 rounded w-48 animate-pulse"></div>

                {/* Course content items */}
                {[...Array(5)].map((_, index) => (
                    <div key={index} className="border border-gray-200 rounded-md p-4 flex flex-col gap-4">
                        {/* Content header */}
                        <div className="flex justify-between items-center">
                            <div className="h-5 bg-gray-300 rounded w-32 animate-pulse"></div>
                            <div className="h-8 bg-gray-300 rounded w-20 animate-pulse"></div>
                        </div>

                        {/* Content fields */}
                        <div className="flex flex-col gap-3">
                            <div className="h-10 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                            <div className="h-20 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                        </div>

                        {/* File upload area */}
                        <div className="h-16 bg-gray-300 rounded-sm w-full animate-pulse"></div>
                    </div>
                ))}
            </section>

            {/* Submit button skeleton */}
            <div className="w-full h-12 bg-gray-300 rounded-sm animate-pulse"></div>
        </main>
    );
}

export default AdminTranslateCourseSkeleton;