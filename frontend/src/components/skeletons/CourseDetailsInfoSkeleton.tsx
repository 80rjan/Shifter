import ShifterArrow from "../../../public/Shifter-Arrow.png";

function CourseDetailsInfoSkeleton() {
    return (
        <>
            {/*WHAT WILL BE LEARNED*/}
            <section className="relative flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md overflow-clip">
                <img src={ShifterArrow} alt="Shifter Arrow"
                     className="absolute opacity-10 h-150 w-120 -rotate-130 -top-40 right-0"/>
                <img src={ShifterArrow} alt="Shifter Arrow"
                     className="absolute opacity-10 h-150 w-120 rotate-50 -bottom-40 left-0"/>

                <h2 className="text-5xl">What you'll learn</h2>
                <div className="grid grid-cols-2 gap-y-8 gap-x-20">
                    {[...Array(10)].map((_, index) => (
                        <div className="flex items-center gap-4" key={index}>
                            <div className="h-10 w-10 bg-gray-300 rounded animate-pulse"></div>
                            <div className="h-6 bg-gray-300 rounded flex-1 animate-pulse"></div>
                        </div>
                    ))}
                </div>
            </section>

            {/*COURSE CONTENT*/}
            <section className="relative flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md overflow-clip">
                <h2 className="text-5xl">Course content</h2>
                <div>
                    {[...Array(5)].map((_, index) => (
                        <div key={index} className={`border-1 border-black/20 ${index !== 4 ? "border-b-0" : ""}`}>
                            <div className="overflow-clip flex justify-between items-center px-4 py-4 bg-black/5">
                                <div className="flex gap-4 items-center">
                                    <div className="h-8 w-8 bg-gray-300 rounded animate-pulse"></div>
                                    <div className="h-6 bg-gray-300 rounded w-48 animate-pulse"></div>
                                </div>
                                <div className="flex gap-2 items-center text-black/40">
                                    <div className="h-4 bg-gray-300 rounded w-16 animate-pulse"></div>
                                    <span >•</span>
                                    <div className="h-4 bg-gray-300 rounded w-12 animate-pulse"></div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </section>

            {/*DESCRIPTION*/}
            <section className="flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md">
                <h2 className="text-5xl">Course description</h2>
                <div>
                    <div className="relative overflow-hidden">
                        <div className="space-y-4">
                            {[...Array(7)].map((_, index) => (
                                <div key={index} className="h-4 bg-gray-300 rounded w-full animate-pulse"></div>
                            ))}
                            <div className="h-4 bg-gray-300 rounded w-2/3 animate-pulse"></div>
                        </div>
                        <div
                            className="pointer-events-none absolute bottom-0 left-0 w-full h-24 bg-gradient-to-t from-white to-transparent"/>
                    </div>
                    <div className="mt-4 h-10 bg-gray-300 rounded w-24 animate-pulse"></div>
                </div>
            </section>
        </>
    );
}

export default CourseDetailsInfoSkeleton;