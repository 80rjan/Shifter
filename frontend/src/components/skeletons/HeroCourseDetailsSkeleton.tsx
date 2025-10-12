
function HeroCourseDetailsSkeleton() {
    return (
        <div className="bg-shifter py-4">
            {/*HEADER AND DESCRIPTION*/}
            <section className="flex flex-col items-center gap-8 bg-white mx-6 px-horizontal-lg pb-12 pt-40 rounded-xl shadow-lg shadow-black/20">
                {/* Title */}
                <div className="h-12 bg-gray-300 rounded w-120 animate-pulse"></div>

                {/* Description */}
                <div className="space-y-2 w-full max-w-5xl">
                    <div className="h-4 bg-gray-300 rounded w-full animate-pulse"></div>
                    <div className="h-4 bg-gray-300 rounded w-full animate-pulse"></div>
                    <div className="h-4 bg-gray-300 rounded w-4/5 animate-pulse"></div>
                </div>

                {/* Button section */}
                <div className="flex mt-12 gap-12 items-center bg-gray/60 backdrop-blur-lg border-3 border-black/5 px-2 py-1 w-fit rounded-full">
                    <div className="h-6 bg-gray-300 rounded w-8 animate-pulse"></div>
                    <div className="h-12 bg-gray-300 rounded-full w-60 animate-pulse"></div>
                </div>
            </section>

            {/*TRIPLE INFO*/}
            <section className="flex justify-center text-white px-12 py-4 ">
                {[...Array(3)].map((_, index) => (
                    <div key={index} className="flex flex-col gap-4 text-left px-20 py-8 border-r-2 border-white/40 last:border-r-0">
                        <div className="h-8 bg-white/30 rounded w-80 animate-pulse"></div>
                        <div className="space-y-2">
                            <div className="h-4 bg-white/20 rounded w-full animate-pulse"></div>
                            <div className="h-4 bg-white/20 rounded w-full animate-pulse"></div>
                            <div className="h-4 bg-white/20 rounded w-2/3 animate-pulse"></div>
                        </div>
                    </div>
                ))}
            </section>
        </div>
    );
}

export default HeroCourseDetailsSkeleton;