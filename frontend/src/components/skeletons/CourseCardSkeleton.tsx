
function CourseCardSkeleton() {
    return (
        <article className="relative border-1 border-black/10 shadow-md shadow-black/10
            flex flex-col w-full rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF] animate-pulse">

            {/*IMAGE SKELETON*/}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <div className="w-full h-[180px] bg-gray-300 rounded-md"></div>
            </div>

            {/*TITLE AND TOPICS SKELETON*/}
            <div className="flex flex-col gap-2 items-start text-left h-full">
                {/*Title skeleton*/}
                <div className="h-6 bg-gray-300 rounded w-3/4"></div>

                {/*Topics skeleton*/}
                <div className="h-16 bg-gray-200 rounded w-full"></div>
            </div>

            {/*INFO SKELETON*/}
            <div className="flex flex-wrap gap-2 whitespace-nowrap">
                <div className="h-6 bg-gray-200 rounded w-12"></div>
                <div className="h-6 bg-gray-200 rounded w-20"></div>
                <div className="h-6 bg-gray-200 rounded w-20"></div>
                <div className="h-6 bg-gray-200 rounded w-24"></div>
                <div className="h-6 bg-gray-200 rounded w-16"></div>
            </div>

            {/*BUTTON AND PRICE SKELETON*/}
            <div className="flex justify-between items-center mt-0">
                <div className="h-6 bg-gray-300 rounded w-12"></div>

                <div className="flex items-center gap-2">
                    <div className="w-6 h-6 bg-gray-200 rounded"></div>
                    <div className="h-8 bg-gray-300 rounded w-20"></div>
                </div>
            </div>
        </article>
    );
}

export default CourseCardSkeleton;